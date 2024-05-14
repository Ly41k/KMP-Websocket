package core.utils

object Constants {
    //    private const val SERVER_URL: String = "192.168.1.120:8080"
    private const val SERVER_URL: String = "192.168.0.101:8080"

    const val BASE_WS_URL: String = "ws://$SERVER_URL/chat"
    const val BASE_URL: String = "http://$SERVER_URL"

    const val QUALIFIER_DEFAULT_HTTP_CLIENT: String = "DefaultHttpClient"
    const val QUALIFIER_WEBSOCKET_HTTP_CLIENT: String = "WebSocketHttpClient"
}
