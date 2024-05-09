import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import compose.ChatsScreen
import core.di.LocalPlatform
import core.di.Platform
import platform.UIKit.UIViewController
import ui.theme.AppTheme

@Suppress("unused", "functionName")
fun MainViewController(): UIViewController = ComposeUIViewController {

    AppTheme {
        CompositionLocalProvider(
            LocalPlatform provides Platform.iOS
        ) {
            ChatsScreen()
        }
    }
}
