import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import core.di.LocalPlatform
import core.di.Platform
import core.di.PlatformConfiguration
import di.appModule
import moe.tlaster.precompose.PreComposeApp
import navigation.AppNavGraph
import org.koin.compose.KoinApplication
import ui.theme.AppTheme

@Composable
fun MainView(activity: ComponentActivity) {
    KoinApplication(application = {
        modules(appModule(configuration = PlatformConfiguration(activity)))
    }) {
        PreComposeApp {
            AppTheme {
                CompositionLocalProvider(
                    LocalPlatform provides Platform.Android
                ) {
                    AppNavGraph()
                }
            }
        }
    }
}
