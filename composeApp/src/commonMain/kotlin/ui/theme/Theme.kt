package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ui.models.AppColors
import ui.models.AppTypographies

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) darkColorScheme else lightColorScheme

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypographies provides typography,
        content = content
    )
}

object AppTheme {
    val colors: AppColors
        @Composable get() = LocalAppColors.current

    val typography: AppTypographies
        @Composable get() = LocalAppTypographies.current
}
