package com.mockrun.app.core.designsystem

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CompositionLocal providing global Liquid Glass effect toggle state.
 *
 * Note: the lite build keeps the API for source compatibility but renders a
 * solid Material-style surface instead of a GPU blur (no Haze dependency).
 */
val LocalLiquidGlassEnabled = compositionLocalOf { true }

/**
 * No-op replacement for the former HazeState.
 *
 * Kept so call sites that create/remember a haze state still compile without
 * depending on the Haze library. The value carries no rendering state.
 */
class HazeState

val LocalHazeState = compositionLocalOf<HazeState?> { null }

/** Bottom bar state placeholder (kept for API compatibility). */
val LocalBottomBarHazeState = compositionLocalOf<HazeState?> { null }

object LiquidGlassDefaults {
    const val PREFS_NAME = "fake_gps_ui_prefs"
    const val KEY_LIQUID_GLASS_ENABLED = "is_liquid_glass_enabled"

    /** Retained for API compatibility; no blur is applied in the lite build. */
    val BlurRadius = 36.dp

    const val NoiseFactorDark = 0.035f
    const val NoiseFactorLight = 0.025f

    fun isEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_LIQUID_GLASS_ENABLED, true)
    }

    fun setEnabled(context: Context, enabled: Boolean) {
        val sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sp.edit().putBoolean(KEY_LIQUID_GLASS_ENABLED, enabled).apply()
    }
}

/** No-op source marker. Kept so call sites compile without Haze. */
fun Modifier.hazeSource(state: HazeState?): Modifier = this

/** Solid inset card surface. */
fun Modifier.iosCardSurface(
    shape: Shape = RoundedCornerShape(24.dp)
): Modifier = composed {
    this
        .clip(shape)
        .background(LocalIosColors.current.secondaryGroupedBackground, shape)
        .border(1.dp, LocalIosColors.current.line, shape)
}

/**
 * Material-style solid surface with subtle elevation and optical border.
 *
 * The [isLiquidGlass] flag and all other parameters are retained for source
 * compatibility. In the lite build the blur is replaced with an opaque tint,
 * which removes the Haze dependency and its native blur cost.
 */
fun Modifier.liquidGlass(
    isLiquidGlass: Boolean,
    shape: Shape = RoundedCornerShape(24.dp),
    elevation: Dp = 12.dp,
    containerColor: Color? = null,
    borderWidth: Dp = 1.0.dp,
    hazeState: HazeState? = null,
    blurRadius: Dp = LiquidGlassDefaults.BlurRadius,
    noiseFactor: Float = 0f
): Modifier = composed {
    val palette = LocalIosColors.current
    val isDark = palette.isDark

    val fill = containerColor ?: if (isDark) {
        DesignTokens.GlassBgOpaque
    } else {
        DesignTokens.LGlassBgOpaque
    }

    val borderColor = if (isDark) {
        Color.White.copy(alpha = 0.10f)
    } else {
        Color(0x240F172A)
    }

    val shadowElevation = elevation.coerceIn(0.dp, 26.dp)
    val spotColor = if (isDark) {
        Color.Black.copy(alpha = 0.52f)
    } else {
        Color(0xFF0F172A).copy(alpha = 0.14f)
    }

    this
        .shadow(
            elevation = shadowElevation,
            shape = shape,
            spotColor = spotColor,
            ambientColor = Color.Transparent
        )
        .clip(shape)
        .background(fill, shape)
        .border(borderWidth, borderColor, shape)
        .drawBehind {
            val stroke = (if (isDark) 1.dp else 1.25.dp).toPx()
            drawLine(
                color = palette.glassHighlight,
                start = Offset(stroke * 6f, stroke / 2f),
                end = Offset(size.width - stroke * 6f, stroke / 2f),
                strokeWidth = stroke
            )
        }
}

/**
 * Ambient background for scrolling pages.
 *
 * Retained for source compatibility. In the lite build this is a plain
 * gradient background with no blur source attached.
 */
@Composable
fun FrostedAmbientBackground(
    hazeState: HazeState? = null,
    modifier: Modifier = Modifier
) {
    val palette = LocalIosColors.current
    val bgColors: List<Color> = listOf(palette.systemGroupedBackground, palette.secondaryGroupedBackground)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = bgColors))
    )
}
