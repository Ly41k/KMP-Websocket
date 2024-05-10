package navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import compose.chat.ChatScreen
import compose.chats.ChatsScreen
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import presentation.ChatsViewModel
import ui.theme.AppTheme

@Composable
fun AppNavGraph() {
    val navigator = rememberNavigator()
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
                ChatsScreen(viewModel)
            }
            scene(route = AppNavigation.Chat.route) {
                ChatScreen()
            }
        }
    }
}
