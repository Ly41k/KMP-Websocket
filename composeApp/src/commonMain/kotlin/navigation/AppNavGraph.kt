package navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import compose.chat.ChatScreen
import compose.chats.ChatsScreen
import core.utils.Constants.NAV_ARGS
import core.utils.navigateToChatWithArgs
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import presentation.MainViewModel
import presentation.chats.ChatsViewModel
import presentation.chats.models.ChatsAction
import ui.theme.AppTheme

@Composable
fun AppNavGraph() {
    val navigator = rememberNavigator()
    koinViewModel(vmClass = MainViewModel::class).apply { launch() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
    ) {
        NavHost(
            navigator = navigator,
            initialRoute = AppNavigation.Chats.route,
        ) {
            scene(route = AppNavigation.Chats.route) {
                val viewModel = koinViewModel(vmClass = ChatsViewModel::class)
                ChatsScreen(viewModel) {
                    when (it) {
                        is ChatsAction.OpenChatScreen -> navigator.navigateToChatWithArgs(it.args)
                    }
                }
            }
            scene(route = AppNavigation.Chat.route) { backStackEntry ->
                val args: String? = backStackEntry.query<String>(NAV_ARGS)
                println("args - $args")
                ChatScreen()
            }
        }
    }
}
