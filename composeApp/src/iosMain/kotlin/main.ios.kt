import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import core.di.LocalPlatform
import core.di.Platform
import core.di.PlatformConfiguration
import di.appModule
import moe.tlaster.precompose.PreComposeApp
import navigation.AppNavGraph
import org.koin.compose.KoinApplication
import platform.UIKit.UIViewController
import ui.theme.AppTheme

@Suppress("unused", "functionName")
fun MainViewController(): UIViewController = ComposeUIViewController {
    KoinApplication(application = {
        modules(appModule(configuration = PlatformConfiguration()))
    }) {
        PreComposeApp {
            AppTheme {
                CompositionLocalProvider(
                    LocalPlatform provides Platform.iOS
                ) {
                    AppNavGraph()
                }
            }
        }
    }
}
