package core.utils

import moe.tlaster.precompose.navigation.Navigator
import navigation.AppNavigation

fun Navigator.navigateToChatWithArgs(args: String) {
    val routeWithQuery = AppNavigation.Chat.route.createRouteWithQuery(args)
    this.navigate(routeWithQuery)
}
