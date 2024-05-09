import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import compose.ChatsScreen
import core.di.LocalPlatform
import core.di.Platform
import ui.theme.AppTheme

@Composable
fun MainView(activity: ComponentActivity) {
    AppTheme {
        CompositionLocalProvider(
            LocalPlatform provides Platform.Android
        ) {
            ChatsScreen()
        }
    }
}
