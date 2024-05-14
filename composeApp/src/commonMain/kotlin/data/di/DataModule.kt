package data.di

import core.utils.Constants
import data.ktor.chat.KtorChatsRemoteDataSource
import data.ktor.message.KtorWebSocketClient
import data.ktor.message.WebSocketClient
import data.repository.ChatRepositoryImpl
import domain.repository.ChatRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<KtorChatsRemoteDataSource> {
        KtorChatsRemoteDataSource(httpClient = get(qualifier = named(Constants.QUALIFIER_DEFAULT_HTTP_CLIENT)))
    }

    single<WebSocketClient> {
        KtorWebSocketClient(httpClient = get(qualifier = named(Constants.QUALIFIER_WEBSOCKET_HTTP_CLIENT)))
    }

    single<ChatRepository> { ChatRepositoryImpl(dataSource = get()) }

}
