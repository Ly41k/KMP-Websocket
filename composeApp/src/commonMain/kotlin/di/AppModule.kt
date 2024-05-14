package di

import core.Constants.QUALIFIER_DEFAULT_HTTP_CLIENT
import core.Constants.QUALIFIER_WEBSOCKET_HTTP_CLIENT
import core.di.PlatformConfiguration
import core.ktor.ktorModule
import data.ktor.KtorWebSocketClient
import data.ktor.WebSocketClient
import data.ktor.chats.KtorChatsRemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module
import presentation.chats.ChatsViewModel

fun appModule(configuration: PlatformConfiguration) = module {
    single<PlatformConfiguration> { configuration }
    includes(ktorModule)
    factory<ChatsViewModel> { ChatsViewModel(get(), get()) }
    single<WebSocketClient> { KtorWebSocketClient(client = get(qualifier = named(QUALIFIER_WEBSOCKET_HTTP_CLIENT))) }
    factory<KtorChatsRemoteDataSource> {
        KtorChatsRemoteDataSource(get(qualifier = named(QUALIFIER_DEFAULT_HTTP_CLIENT)))
    }
}


