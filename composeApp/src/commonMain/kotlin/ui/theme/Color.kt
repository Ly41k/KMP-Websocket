package ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import ui.models.AppColors

internal val lightColorScheme = AppColors(
    primaryBackground = Color(0xFFF4F9FC),
    secondaryBackground = Color(0xFFD6DFE8),
    primaryText = Color(0xFF10100F),
    secondaryText = Color(0xFF626461),
    primaryAction = Color(0xFF88C2CE),
)

internal val darkColorScheme = AppColors(
    primaryBackground = Color(0xFF10100e),
    secondaryBackground = Color(0xFF9eadb5),
    primaryText = Color(0xFFe9e9ec),
    secondaryText = Color(0xFFc4b3a3),
    primaryAction = Color(0xFF03798a),
)

internal val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No colors provided")
}

object ColorRes {
    val errorColor = Color(0xFFD50032)
}
