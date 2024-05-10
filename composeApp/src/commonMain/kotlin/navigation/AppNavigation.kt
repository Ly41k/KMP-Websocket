package navigation

import core.navigaiton.BaseNavigation
import core.navigaiton.NavigationTree

sealed class AppNavigation(override val route: String) : BaseNavigation(route) {
    data object Chats : AppNavigation(route = NavigationTree.Main.Chats.name)
    data object Chat : AppNavigation(route = NavigationTree.Main.Chat.name)
}
