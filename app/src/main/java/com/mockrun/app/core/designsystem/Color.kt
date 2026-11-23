package com.mockrun.app.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// =============================================================================
// Design Token · Material 3 Baseline
//   全部公开常量名 / 属性名 / getter 名保持不变，仅将色值替换为 M3 baseline
//   tonal palette。业务层的 IosColors.* 引用无需改动即可获得 M3 配色。
// 三层结构：
//   ① 原始 token（DesignTokens）—— M3 baseline 精确数值，不随明暗切换
//   ② 语义 palette（IosColorPalette）—— 按 isDark 提供两组值
//   ③ 访问器（IosColors）—— 向后兼容的公开 API
// =============================================================================

// -----------------------------------------------------------------------------
// ① 原始 Token · Material 3 baseline
// -----------------------------------------------------------------------------
object DesignTokens {
    // 背景层级（dark）
    val Bg0 = Color(0xFF1C1B1F)          // M3 background / surface dark
    val Bg1 = Color(0xFF211F26)          // surfaceContainer
    val Surface = Color(0xFF2B2930)      // surfaceContainerHigh
    val Surface2 = Color(0xFF36343B)     // surfaceContainerHighest
    val Surface3 = Color(0xFF413F47)     // 高一级容器
    val Line = Color(0x1FE6E1E5)         // outlineVariant dark ≈ 12% onSurface
    val LineSoft = Color(0x14E6E1E5)     // 更弱分隔线

    // 语义色（M3 tonal）
    val Brand = Color(0xFFD0BCFF)        // primary dark
    val BrandPress = Color(0xFFA391E8)   // primary pressed dark
    val BrandSoft = Color(0xFFD0BCFF)    // primary on container
    val Ok = Color(0xFF81C784)           // success dark
    val Warn = Color(0xFFFFB74D)         // warning dark
    val Danger = Color(0xFFF2B8B5)       // error dark
    val Violet = Color(0xFFEFB8C8)       // tertiary dark

    // 文字
    val T1 = Color(0xFFE6E1E5)           // onSurface dark
    val T2 = Color(0xFFCAC4D0)           // onSurfaceVariant dark
    val T3 = Color(0xFF938F99)           // outline dark

    // 容器表面（原毛玻璃位，现为不透明 surface container）
    val GlassBg = Color(0xF236343B)      // surfaceContainerHighest dark 95%
    val GlassBgBar = Color(0xF82B2930)   // surfaceContainerHigh dark 97%
    val GlassLine = Color(0x29E6E1E5)    // outline on container
    val GlassHighlight = Color(0x3DE6E1E5)

    val GlassBgOpaque = Color(0xFF2B2930)
    val GlassBgBarOpaque = Color(0xFF211F26)

    // 明色档
    val LBg0 = Color(0xFFFFFBFE)         // M3 background light
    val LBg1 = Color(0xFFF7F2FA)         // surfaceContainerLow
    val LSurface = Color(0xFFFFFFFF)
    val LSurface2 = Color(0xFFF3EDF7)    // surfaceContainer
    val LSurface3 = Color(0xFFE6E0E9)    // surfaceContainerHighest
    val LLine = Color(0x1F1C1B1F)
    val LLineSoft = Color(0x141C1B1F)
    val LGlassBg = Color(0xF2FFFFFF)
    val LGlassBgBar = Color(0xFAFFFBFE)
    val LGlassLine = Color(0x331C1B1F)
    val LGlassHighlight = Color(0xE6FFFFFF)
    val LGlassBgOpaque = Color(0xFFFFFFFF)
    val LGlassBgBarOpaque = Color(0xFFFFFBFE)

    // 地图图钉上方提示条底板
    val LPinTipBg = Color(0xE6FFFFFF)
    val DPinTipBg = Color(0xF2211F26)
}

// -----------------------------------------------------------------------------
// ② 语义 Palette
// -----------------------------------------------------------------------------
data class IosColorPalette(
    val isDark: Boolean,

    // Dynamic Text Hierarchy (Labels)
    val label: Color,
    val secondaryLabel: Color,
    val tertiaryLabel: Color,
    val quaternaryLabel: Color,

    // Grouped System Backgrounds
    val systemGroupedBackground: Color,
    val secondaryGroupedBackground: Color,
    val tertiaryGroupedBackground: Color,

    // System Fills
    val systemFill: Color,
    val secondarySystemFill: Color,
    val tertiarySystemFill: Color,
    val quaternarySystemFill: Color,

    // Separators
    val separator: Color,
    val opaqueSeparator: Color,

    // Materials & Container Tints
    val ultraThinMaterial: Color,
    val thinMaterial: Color,
    val frostedCapsule: Color,
    val hairlineBorder: Color,
    val glassTrack: Color,

    // ---- 层级 / 描边 / 容器 ----
    val bg1: Color,
    val surface2: Color,
    val surface3: Color,
    val line: Color,
    val lineSoft: Color,
    val brandPress: Color,
    val brandSoft: Color,
    val glassBg: Color,
    val glassLine: Color,
    val glassHighlight: Color,
    val glassBgBar: Color,
    val pinTipBg: Color,
)

val LightIosColorPalette = IosColorPalette(
    isDark = false,
    label = Color(0xFF1C1B1F),
    secondaryLabel = Color(0xFF49454F),
    tertiaryLabel = Color(0xFF79747E),
    quaternaryLabel = Color(0x8079747E),
    systemGroupedBackground = DesignTokens.LBg0,
    secondaryGroupedBackground = DesignTokens.LSurface,
    tertiaryGroupedBackground = DesignTokens.LSurface2,
    systemFill = Color(0x3349454F),
    secondarySystemFill = Color(0x2949454F),
    tertiarySystemFill = Color(0x1F49454F),
    quaternarySystemFill = Color(0x1449454F),
    separator = DesignTokens.LLine,
    opaqueSeparator = Color(0xFFCAC4D0),
    ultraThinMaterial = Color(0xE6FFFBFE),
    thinMaterial = Color(0xF2FFFFFF),
    frostedCapsule = DesignTokens.LGlassBg,
    hairlineBorder = DesignTokens.LGlassLine,
    glassTrack = Color(0x1F1C1B1F),
    bg1 = DesignTokens.LBg1,
    surface2 = DesignTokens.LSurface2,
    surface3 = DesignTokens.LSurface3,
    line = DesignTokens.LLine,
    lineSoft = DesignTokens.LLineSoft,
    brandPress = Color(0xFF514578),
    brandSoft = Color(0xFF6750A4),
    glassBg = DesignTokens.LGlassBg,
    glassLine = DesignTokens.LGlassLine,
    glassHighlight = DesignTokens.LGlassHighlight,
    glassBgBar = DesignTokens.LGlassBgBar,
    pinTipBg = DesignTokens.LPinTipBg,
)

val DarkIosColorPalette = IosColorPalette(
    isDark = true,
    label = DesignTokens.T1,
    secondaryLabel = DesignTokens.T2,
    tertiaryLabel = DesignTokens.T3,
    quaternaryLabel = Color(0x66938F99),
    systemGroupedBackground = DesignTokens.Bg0,
    secondaryGroupedBackground = DesignTokens.Surface,
    tertiaryGroupedBackground = DesignTokens.Surface2,
    systemFill = Color(0x5C49454F),
    secondarySystemFill = Color(0x5249454F),
    tertiarySystemFill = Color(0x3D49454F),
    quaternarySystemFill = Color(0x2E49454F),
    separator = DesignTokens.Line,
    opaqueSeparator = DesignTokens.Surface3,
    ultraThinMaterial = Color(0xD92B2930),
    thinMaterial = Color(0xF22B2930),
    frostedCapsule = DesignTokens.GlassBg,
    hairlineBorder = DesignTokens.GlassLine,
    glassTrack = Color(0x29CAC4D0),
    bg1 = DesignTokens.Bg1,
    surface2 = DesignTokens.Surface2,
    surface3 = DesignTokens.Surface3,
    line = DesignTokens.Line,
    lineSoft = DesignTokens.LineSoft,
    brandPress = DesignTokens.BrandPress,
    brandSoft = DesignTokens.BrandSoft,
    glassBg = DesignTokens.GlassBg,
    glassLine = DesignTokens.GlassLine,
    glassHighlight = DesignTokens.GlassHighlight,
    glassBgBar = DesignTokens.GlassBgBar,
    pinTipBg = DesignTokens.DPinTipBg,
)

val LocalIosColors = staticCompositionLocalOf { LightIosColorPalette }

// -----------------------------------------------------------------------------
// ③ 访问器
// -----------------------------------------------------------------------------
object IosColors {
    // 1. System Accent Colors —— M3 tonal 基准
    val SystemBlue = DesignTokens.Brand
    val SystemGreen = DesignTokens.Ok
    val SystemRed = DesignTokens.Danger
    val SystemOrange = DesignTokens.Warn
    val SystemYellow = Color(0xFFF9AB00)
    val SystemPurple = DesignTokens.Violet
    val SystemTeal = Color(0xFF5EB3B3)
    val SystemIndigo = Color(0xFF9C94E0)
    val SystemPink = Color(0xFFF48FB1)
    val SystemMint = Color(0xFFA8DADC)
    val SystemCyan = Color(0xFF84C7E8)

    // 1b. 品牌扩展
    val BrandPress = DesignTokens.BrandPress
    val BrandSoft = DesignTokens.BrandSoft
    val Violet = DesignTokens.Violet

    // 2. Grays（Material neutral）
    val SystemGray = Color(0xFF9E9E9E)
    val SystemGray2 = Color(0xFFE0E0E0)
    val SystemGray3 = Color(0xFFC7C7CC)
    val SystemGray4 = Color(0xFFD1D1D6)
    val SystemGray5 = Color(0xFFE5E5EA)
    val SystemGray6 = Color(0xFFF2F2F7)

    // 3. Dynamic Text Hierarchy (Labels)
    val Label: Color
        @Composable
        get() = LocalIosColors.current.label

    val SecondaryLabel: Color
        @Composable
        get() = LocalIosColors.current.secondaryLabel

    val TertiaryLabel: Color
        @Composable
        get() = LocalIosColors.current.tertiaryLabel

    val QuaternaryLabel: Color
        @Composable
        get() = LocalIosColors.current.quaternaryLabel

    // 4. Grouped System Backgrounds
    val SystemGroupedBackground: Color
        @Composable
        get() = LocalIosColors.current.systemGroupedBackground

    val SecondaryGroupedBackground: Color
        @Composable
        get() = LocalIosColors.current.secondaryGroupedBackground

    val TertiaryGroupedBackground: Color
        @Composable
        get() = LocalIosColors.current.tertiaryGroupedBackground

    // 5. System Fills
    val SystemFill: Color
        @Composable
        get() = LocalIosColors.current.systemFill

    val SecondarySystemFill: Color
        @Composable
        get() = LocalIosColors.current.secondarySystemFill

    val TertiarySystemFill: Color
        @Composable
        get() = LocalIosColors.current.tertiarySystemFill

    val QuaternarySystemFill: Color
        @Composable
        get() = LocalIosColors.current.quaternarySystemFill

    // 6. Separators
    val Separator: Color
        @Composable
        get() = LocalIosColors.current.separator

    val OpaqueSeparator: Color
        @Composable
        get() = LocalIosColors.current.opaqueSeparator

    // 7. Materials & Container Tints
    val UltraThinMaterial: Color
        @Composable
        get() = LocalIosColors.current.ultraThinMaterial

    val ThinMaterial: Color
        @Composable
        get() = LocalIosColors.current.thinMaterial

    // 8. 层级 / 描边 / 容器
    val Bg1: Color
        @Composable
        get() = LocalIosColors.current.bg1

    val Surface2: Color
        @Composable
        get() = LocalIosColors.current.surface2

    val Surface3: Color
        @Composable
        get() = LocalIosColors.current.surface3

    val Line: Color
        @Composable
        get() = LocalIosColors.current.line

    val LineSoft: Color
        @Composable
        get() = LocalIosColors.current.lineSoft

    val GlassBg: Color
        @Composable
        get() = LocalIosColors.current.glassBg

    val GlassLine: Color
        @Composable
        get() = LocalIosColors.current.glassLine

    val GlassHighlight: Color
        @Composable
        get() = LocalIosColors.current.glassHighlight

    val GlassBgBar: Color
        @Composable
        get() = LocalIosColors.current.glassBgBar

    val PinTipBg: Color
        @Composable
        get() = LocalIosColors.current.pinTipBg

    /**
     * 容器面上的强调色（选中态）。
     * 暗色用 primary dark，明色用 primary #6750A4。
     */
    val BrandOnGlass: Color
        @Composable
        get() = LocalIosColors.current.brandSoft
}

// =============================================================================
// Top-Level Backward-Compatible Aliases
// =============================================================================
val IosBlue = IosColors.SystemBlue
val IosGreen = IosColors.SystemGreen
val IosRed = IosColors.SystemRed
val IosOrange = IosColors.SystemOrange
val IosYellow = IosColors.SystemYellow
val IosPurple = IosColors.SystemPurple
val IosTeal = IosColors.SystemTeal
val IosIndigo = IosColors.SystemIndigo
val IosGray = IosColors.SystemGray
val IosGray2 = IosColors.SystemGray2
val IosGray3 = IosColors.SystemGray3
val IosGray4 = IosColors.SystemGray4
val IosGray5 = IosColors.SystemGray5
val IosGray6 = IosColors.SystemGray6

val IosSystemBackground: Color
    @Composable
    get() = LocalIosColors.current.systemGroupedBackground

val IosCardBackground: Color
    @Composable
    get() = LocalIosColors.current.secondaryGroupedBackground

val IosCardBackgroundDark = DesignTokens.Surface

val IosFrostedLight = Color(0xEBFFFFFF)
val IosFrostedDark = Color(0xEB1C1B1F)

val IosFrostedCapsule: Color
    @Composable
    get() = LocalIosColors.current.frostedCapsule

val IosFrostedDarkCapsule = DesignTokens.GlassBg
val IosFrostedPill = Color(0xE0F2F2F7)

val IosHairlineBorder: Color
    @Composable
    get() = LocalIosColors.current.hairlineBorder

val IosHairlineBorderDark = DesignTokens.GlassLine

val IosGlassTrack: Color
    @Composable
    get() = LocalIosColors.current.glassTrack

val IosGlassTrackLight = Color(0x331C1B1F)

val IosSeparator: Color
    @Composable
    get() = LocalIosColors.current.separator

val IosSubtleHighlight = Color(0x40E6E1E5)

val IosGlassBgDark = DesignTokens.GlassBg
val IosGlassLineDark = DesignTokens.GlassLine
val IosGlassHighlightDark = DesignTokens.GlassHighlight
val IosLineDark = DesignTokens.Line
val IosLineSoftDark = DesignTokens.LineSoft
val IosSurfaceDark = DesignTokens.Surface
val IosSurface2Dark = DesignTokens.Surface2
val IosSurface3Dark = DesignTokens.Surface3
val IosBg0Dark = DesignTokens.Bg0
val IosBg1Dark = DesignTokens.Bg1
val IosText1Dark = DesignTokens.T1
val IosText2Dark = DesignTokens.T2
val IosText3Dark = DesignTokens.T3

// =============================================================================
// 语义色 · 状态（M3 error/warning/success/info）
// =============================================================================
object IosSemanticColors {
    /** 危险 / 错误 —— 暗色档 M3 error dark #F2B8B5 */
    val Danger = Color(0xFFF2B8B5)

    /** 危险 / 错误 —— 明色档 M3 error #B3261E */
    val DangerLight = Color(0xFFB3261E)

    /** 危险底容器 —— 暗色档 */
    val DangerContainer = Color(0x268C1D18)

    /** 危险底容器 —— 明色档 */
    val DangerContainerLight = Color(0x26B3261E)

    /** 警告 —— 暗色档 */
    val Warning = Color(0xFFFFB74D)

    /** 警告 —— 明色档 */
    val WarningLight = Color(0xFFB25000)

    /** 警告底容器 —— 暗色档 */
    val WarningContainer = Color(0x26FFB74D)

    /** 警告底容器 —— 明色档 */
    val WarningContainerLight = Color(0x26B25000)

    /** 成功 —— 暗色档 */
    val Success = Color(0xFF81C784)

    /** 成功 —— 明色档 */
    val SuccessLight = Color(0xFF2E7D32)

    /** 成功底容器 —— 暗色档 */
    val SuccessContainer = Color(0x2681C784)

    /** 成功底容器 —— 明色档 */
    val SuccessContainerLight = Color(0x262E7D32)

    /** 提示 / 信息 —— 暗色档 primary dark */
    val Info = Color(0xFFD0BCFF)

    /** 提示 / 信息 —— 明色档 primary */
    val InfoLight = Color(0xFF6750A4)

    /** 提示底容器 —— 暗色档 */
    val InfoContainer = Color(0x264F378B)

    /** 提示底容器 —— 明色档 */
    val InfoContainerLight = Color(0x26EADDFF)
}

data class IosSemanticPalette(
    val danger: Color,
    val dangerContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val success: Color,
    val successContainer: Color,
    val info: Color,
    val infoContainer: Color
)

val DarkSemanticPalette = IosSemanticPalette(
    danger = IosSemanticColors.Danger,
    dangerContainer = IosSemanticColors.DangerContainer,
    warning = IosSemanticColors.Warning,
    warningContainer = IosSemanticColors.WarningContainer,
    success = IosSemanticColors.Success,
    successContainer = IosSemanticColors.SuccessContainer,
    info = IosSemanticColors.Info,
    infoContainer = IosSemanticColors.InfoContainer
)

val LightSemanticPalette = IosSemanticPalette(
    danger = IosSemanticColors.DangerLight,
    dangerContainer = IosSemanticColors.DangerContainerLight,
    warning = IosSemanticColors.WarningLight,
    warningContainer = IosSemanticColors.WarningContainerLight,
    success = IosSemanticColors.SuccessLight,
    successContainer = IosSemanticColors.SuccessContainerLight,
    info = IosSemanticColors.InfoLight,
    infoContainer = IosSemanticColors.InfoContainerLight
)

fun IosSemanticColors.forDark(isDark: Boolean): IosSemanticPalette =
    if (isDark) DarkSemanticPalette else LightSemanticPalette

val IosDanger = IosSemanticColors.Danger
val IosWarning = IosSemanticColors.Warning
val IosSuccess = IosSemanticColors.Success
val IosInfo = IosSemanticColors.Info

// Legacy Fallbacks
val PrimaryGreen = IosGreen
val PrimaryGreenDark = Color(0xFF2E7D32)
val AccentOrange = IosOrange
val SurfaceDark = IosCardBackgroundDark
val BackgroundDark = DesignTokens.Bg0
val Purple80 = IosPurple
val PurpleGrey80 = IosGray2
val Pink80 = IosColors.SystemPink
