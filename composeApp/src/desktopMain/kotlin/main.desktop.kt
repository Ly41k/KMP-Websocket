import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import compose.ChatsScreen
import core.di.LocalPlatform
import core.di.Platform
import ui.theme.AppTheme

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "App name",
        state = WindowState(size = DpSize(800.dp, 800.dp))
    ) {
        MainView()
    }
}

@Composable
fun MainView() {
    AppTheme {
        CompositionLocalProvider(
            LocalPlatform provides Platform.Desktop
        ) {
            ChatsScreen()
        }
    }
}
