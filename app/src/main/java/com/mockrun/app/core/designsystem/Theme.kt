package com.mockrun.app.core.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// =============================================================================
// Material 3 baseline tonal colorScheme
//   primary #6750A4 · secondary #625B71 · tertiary #7D5260
//   surface #FFFBFE / #1C1B1F
// =============================================================================
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332B40),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF633B48),
    tertiaryContainer = Color(0xFF7D5260),
    onTertiaryContainer = Color(0xFFEADDFF),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceTint = Color(0xFFD0BCFF),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601C18),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFFFD8E4),
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFF6750A4)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF625B71),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFF7D5260),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF31111D),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    surfaceTint = Color(0xFF6750A4),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = Color(0xFFD0BCFF)
)

/**
 * Material 3 Shape scale
 *   extraSmall 4 · small 8 · medium 12 · large 16 · extraLarge 28
 */
val IosShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

/**
 * 语义化圆角 token（常量名保留，数值对齐 M3 shape scale）。
 */
object AppShapes {
    val phone = RoundedCornerShape(66.dp)
    val sheet = RoundedCornerShape(28.dp)      // extraLarge
    val card = RoundedCornerShape(16.dp)       // large
    val panel = RoundedCornerShape(12.dp)      // medium
    val bar = RoundedCornerShape(28.dp)        // 底部栏
    val button = RoundedCornerShape(20.dp)     // M3 filled button 半高圆角
    val icon = RoundedCornerShape(12.dp)       // medium
    val iconLg = RoundedCornerShape(16.dp)     // large
    val chip = RoundedCornerShape(8.dp)        // small
    val pill = RoundedCornerShape(999.dp)
    val pill24 = RoundedCornerShape(24.dp)
}

/**
 * 间距 token —— 8pt 网格（M3 同样采用 8dp baseline grid）
 */
object AppSpacing {
    val s1 = 4.dp
    val s2 = 8.dp
    val s3 = 12.dp
    val s4 = 16.dp
    val s5 = 20.dp
    val s6 = 24.dp
    val s8 = 32.dp

    /** 页面左右安全边距 */
    val pageH = 16.dp

    /** 地图类屏幕底部预留（Tab 栏 72 + 底距 22 + 余量） */
    val mapBottomInset = 102.dp

    /** 滚动页底部预留（Tab 栏 + 呼吸） */
    val pageBottomInset = 122.dp

    val mapBottomInsetComputed: Dp
        @Composable
        get() = ScreenInsets.bottomInset(extra = 8.dp)

    val pageBottomInsetComputed: Dp
        @Composable
        get() = ScreenInsets.bottomInset(extra = 24.dp)
}

/**
 * MaterialTheme.typography 的语义映射。
 *   IosTypography 常量已全部对齐 M3 Type Scale，此处一一映射，
 *   保证 IosTypography.* 与 MaterialTheme.typography.* 两种写法视觉一致。
 */
val IosM3Typography = Typography(
    displayLarge = IosTypography.LargeTitle,
    displayMedium = IosTypography.Title1,
    displaySmall = IosTypography.HeroTitle,
    headlineLarge = IosTypography.Display,
    headlineMedium = IosTypography.Title2,
    headlineSmall = IosTypography.Title3,
    titleLarge = IosTypography.Headline,
    titleMedium = IosTypography.CardTitle,
    titleSmall = IosTypography.RowTitle,
    bodyLarge = IosTypography.Body,
    bodyMedium = IosTypography.Subheadline,
    bodySmall = IosTypography.Footnote,
    labelLarge = IosTypography.ButtonText,
    labelMedium = IosTypography.ButtonSmall,
    labelSmall = IosTypography.Caption2
)

@Composable
fun MockRunTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val iosPalette = if (darkTheme) DarkIosColorPalette else LightIosColorPalette

    androidx.compose.runtime.CompositionLocalProvider(
        LocalIosColors provides iosPalette
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = IosShapes,
            typography = IosM3Typography,
            content = content
        )
    }
}
