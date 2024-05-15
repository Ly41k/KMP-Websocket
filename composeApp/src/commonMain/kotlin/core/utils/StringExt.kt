package core.utils

import core.utils.Constants.NAV_ARGS_QUERY_

fun String.createRouteWithQuery(args: String): String = this + NAV_ARGS_QUERY_ + args
