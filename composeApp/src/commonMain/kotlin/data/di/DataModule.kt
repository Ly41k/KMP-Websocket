package data.di

import core.store.ClearableBaseStore
import core.utils.Constants.QUALIFIER_DEFAULT_HTTP_CLIENT
import core.utils.Constants.QUALIFIER_WEBSOCKET_HTTP_CLIENT
import data.ktor.chat.KtorChatsRemoteDataSource
import data.ktor.message.KtorWebSocketClient
import data.ktor.message.WebSocketClient
import data.repository.ChatRepositoryImpl
import data.store.MessageStore
import domain.modes.presentation.message.MessageItem
import domain.repository.ChatRepository
import domain.repository.MessageRepository
import domain.repository.MessageRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<KtorChatsRemoteDataSource> {
        KtorChatsRemoteDataSource(httpClient = get(qualifier = named(QUALIFIER_DEFAULT_HTTP_CLIENT)))
    }

    single<WebSocketClient> {
        KtorWebSocketClient(httpClient = get(qualifier = named(QUALIFIER_WEBSOCKET_HTTP_CLIENT)))
    }

    single<ChatRepository> { ChatRepositoryImpl(dataSource = get()) }
    single<MessageRepository> { MessageRepositoryImpl(webSocketClient = get(), json = get()) }
    single<ClearableBaseStore<List<MessageItem>>> { MessageStore() }
}
