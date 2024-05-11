package di

import core.di.PlatformConfiguration
import data.ktor.KtorWebSocketClient
import data.ktor.WebSocketClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import org.koin.dsl.module
import presentation.ChatsViewModel

fun appModule(configuration: PlatformConfiguration) = module {
    single<PlatformConfiguration> { configuration }

    single<HttpClient> {
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(WebSockets)
        }
    }

    factory<ChatsViewModel> { ChatsViewModel(get()) }

    single<WebSocketClient> { KtorWebSocketClient(client = get()) }
}
