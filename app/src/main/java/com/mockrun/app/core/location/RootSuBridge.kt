package com.mockrun.app.core.location

import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.mockrun.app.util.Diag
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/** 单个配置项的执行结果，用于一键配置后的逐项回显。 */
data class SetupStepResult(
    val id: String,
    val label: String,
    val success: Boolean,
    val detail: String = ""
)

/** 一键配置整套的汇总结果。 */
data class SetupReport(val steps: List<SetupStepResult>) {
    val successCount: Int get() = steps.count { it.success }
    val failureCount: Int get() = steps.count { !it.success }
}

@Singleton
class RootSuBridge @Inject constructor() {

    private companion object {
        const val TAG = "RootSuBridge"

        /** su commands can be very long (HookStateBridge builds multi-line ones); keep logs readable. */
        const val MAX_CMD_IN_LOG = 120
    }

    private var isRootedCache: Boolean? = null

    fun isRootConfirmed(): Boolean = isRootedCache == true

    suspend fun isRootAvailable(): Boolean = withContext(Dispatchers.IO) {
        // 只缓存"确认有 root"的结论；未确认时每次重探（用户可能稍后才在 Root 管理器里授权）。
        // 旧实现把"未找到 su 二进制"直接缓存为 false 并短路，导致 KernelSU 等 su 不在固定路径
        // 或路径不可 stat 的机型被误判为无 root，Root 模式于是从不自动授权。
        if (isRootedCache == true) return@withContext true

        // 权威判据：直接尝试 su -c id，成功即认为有 root（不依赖固定路径）
        Diag.d(TAG, "probing root with 'su -c id'")
        val executed = executeCommand("id")
        if (executed) {
            isRootedCache = true
            return@withContext true
        }

        // 回退判断：区分"su 存在但被拒"与"根本没有 su"，仅用于日志排障
        val paths = arrayOf(
            "/system/bin/su", "/system/xbin/su", "/sbin/su",
            "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/xbin/su",
            "/data/local/bin/su", "/data/local/su"
        )
        if (paths.any { File(it).exists() }) {
            Diag.w(TAG, "su binary exists but 'su -c id' did not succeed — root likely denied")
        } else {
            Diag.i(TAG, "no su binary on known paths and 'su -c id' failed — running unrooted")
        }
        false
    }

    suspend fun executeCommand(cmd: String): Boolean = withContext(Dispatchers.IO) {
        var process: Process? = null
        val shortCmd = cmd.take(MAX_CMD_IN_LOG)
        try {
            process = Runtime.getRuntime().exec(arrayOf("su", "-c", cmd))
            // Close output stream immediately since we aren't writing stdin
            process.outputStream.close()
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                // Previously silent: a command that ran and failed was indistinguishable
                // from su never being available at all.
                Diag.w(TAG, "su command exited with code $exitCode :: $shortCmd")
            } else {
                Diag.d(TAG, "su command ok :: $shortCmd")
            }
            exitCode == 0
        } catch (e: Exception) {
            // Previously swallowed entirely (`catch (_: Exception) { false }`).
            Diag.w(TAG, "su unavailable or denied :: $shortCmd", e)
            false
        } finally {
            // NOTE: intentionally left un-logged. These are best-effort resource cleanups;
            // a failure here is harmless and logging it would only add noise on hot paths.
            runCatching { process?.inputStream?.close() }
            runCatching { process?.errorStream?.close() }
            runCatching { process?.destroy() }
        }
    }

    /**
     * 授予本应用「模拟位置信息应用」权限（android:mock_location app-op），并顺带开启全局
     * 「开发者选项」开关（部分 OEM 如 ColorOS / MIUI 在 addTestProvider 时除校验 app-op 外
     * 还要求 DEVELOPMENT_SETTINGS_ENABLED = 1）。
     *
     * 关键：两条命令分开执行、互不短路。避免因 ROM 拒绝写 development_settings_enabled
     * 导致 appops set 无法执行。appops 依次尝试多种写法，任一成功即可；
     * settings put 尽力而为，其结果不影响返回值。
     */
    suspend fun grantMockLocation(packageName: String): Boolean {
        val candidates = arrayOf(
            "appops set $packageName android:mock_location allow",
            "cmd appops set $packageName android:mock_location allow",
            "appops set --uid $packageName android:mock_location allow"
        )
        var granted = false
        for (c in candidates) {
            if (executeCommand(c)) {
                granted = true
                break
            }
        }
        // 尽力而为：失败也不影响上面的 app-op 结果
        executeCommand("settings put global development_settings_enabled 1")
        return granted
    }

    suspend fun restoreScanningHardware(): Boolean {
        return executeCommand("settings put secure location_mode 3 && settings put global wifi_scan_always_enabled 1 && settings put global ble_scan_always_enabled 1 && settings put global assisted_gps_enabled 1")
    }

    /**
     * Root 模式下的一整套静默配置。
     *
     * 只做 Root 能稳定静默完成、且不会破坏正常联网的项目：
     * 定位运行时权限、mock_location app-op、开发者选项开关、高精度定位与扫描、
     * 通知权限、悬浮窗权限、电池优化白名单。
     *
     * 刻意不包含：LSPosed 启用 / scope / 重启、OEM 自启动、多任务卡加锁、
     * 关闭 Wi-Fi/BLE 射频（disableWifiBluetoothScan 会影响正常联网）。
     * 这些项由 UI 统一列为「需手动」或单独引导。
     */
    suspend fun applyFullSetup(packageName: String): SetupReport {
        val steps = mutableListOf<SetupStepResult>()

        suspend fun runStep(id: String, label: String, block: suspend () -> Boolean) {
            val ok = runCatching { block() }.getOrDefault(false)
            steps += SetupStepResult(
                id = id,
                label = label,
                success = ok,
                detail = if (ok) "已配置" else "配置失败，请检查 Root 授权或 ROM 限制"
            )
        }

        runStep("mock_location", "模拟位置授权与开发者选项") {
            grantMockLocation(packageName)
        }
        runStep("location_mode", "高精度定位与辅助扫描") {
            restoreScanningHardware()
        }
        runStep("location_permission", "精确定位权限") {
            val fine = executeCommand("pm grant $packageName android.permission.ACCESS_FINE_LOCATION")
            val coarse = executeCommand("pm grant $packageName android.permission.ACCESS_COARSE_LOCATION")
            fine || coarse
        }
        runStep("activity_recognition", "运动识别权限") {
            executeCommand("pm grant $packageName android.permission.ACTIVITY_RECOGNITION")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            runStep("notification", "通知权限") {
                executeCommand("pm grant $packageName android.permission.POST_NOTIFICATIONS")
            }
        }
        runStep("overlay", "悬浮窗权限") {
            executeCommand("appops set $packageName SYSTEM_ALERT_WINDOW allow")
        }
        runStep("battery", "电池优化白名单") {
            executeCommand("cmd deviceidle whitelist +$packageName")
        }

        return SetupReport(steps)
    }

    /**
     * 一键关闭蓝牙 / Wi‑Fi 与背景扫描（Root only）。
     * 用于阻止微信等内嵌腾讯定位 SDK 的应用通过周边路由器 MAC / 蓝牙信标反查真实经纬度。
     * 关闭：WLAN 始终扫描、BLE 始终扫描、Wi‑Fi 唤醒，并直接关闭 Wi‑Fi 与蓝牙射频本身。
     */
    suspend fun disableWifiBluetoothScan(): Boolean {
        return executeCommand(
            "settings put global wifi_scan_always_enabled 0 && " +
            "settings put global ble_scan_always_enabled 0 && " +
            "settings put global wifi_wakeup_enabled 0 && " +
            "svc wifi disable && " +
            "svc bluetooth disable"
        )
    }
}