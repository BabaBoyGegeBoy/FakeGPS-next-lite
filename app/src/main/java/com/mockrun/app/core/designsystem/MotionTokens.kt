package com.mockrun.app.core.designsystem

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Material 3 motion tokens
 *   emphasized / standard / standardDecelerate cubic-bezier
 *   保留原有常量名，数值替换为 M3 标准 motion 曲线。
 */
object MotionTokens {
    // M3 emphasized / standard / decelerate
    val EaseOutStrong = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f)
    val EaseInOutStrong = CubicBezierEasing(0.3f, 0.0f, 0.0f, 1.0f)
    val EaseDrawer = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)

    // M3 spring: 无过冲、快速收敛
    val BouncySpringSpec = spring<Float>(
        dampingRatio = 0.8f,
        stiffness = 380f
    )

    val SnappySpringSpec = spring<Float>(
        dampingRatio = 1.0f,
        stiffness = 500f
    )

    val FluidSpringSpec = spring<Float>(
        dampingRatio = 0.9f,
        stiffness = 300f
    )
}

/**
 * M3 交互反馈：点击使用主题默认 ripple indication（不再传 null 禁用）。
 * [pressedScale] 参数保留以兼容现有调用点，默认不再做缩放形变。
 */
fun Modifier.bouncyClickable(
    enabled: Boolean = true,
    @Suppress("UNUSED_PARAMETER") pressedScale: Float = 0.96f,
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        enabled = enabled,
        onClick = onClick
    )
}

/**
 * 绑定外部 pressed/dragged 状态的缩放修饰（滑块/手柄手势用）。
 * 保留函数签名，内部 spring 已切换为 M3 参数。
 */
fun Modifier.bouncyScale(
    isPressed: Boolean,
    pressedScale: Float = 0.96f
): Modifier = composed {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1.0f,
        animationSpec = MotionTokens.BouncySpringSpec,
        label = "bouncyScale"
    )
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}
