package data.repository

import data.ktor.chat.KtorChatsRemoteDataSource
import domain.mapper.toChatModel
import domain.modes.domain.chats.ChatModel
import domain.repository.ChatRepository

class ChatRepositoryImpl(private val dataSource: KtorChatsRemoteDataSource) : ChatRepository {
    override suspend fun getChats(): List<ChatModel> = dataSource.performGetChats().map { it.toChatModel() }
}
