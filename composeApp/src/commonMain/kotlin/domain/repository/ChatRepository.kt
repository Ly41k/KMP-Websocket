package domain.repository

import domain.modes.domain.chats.ChatModel

interface ChatRepository {
    suspend fun getChats(): List<ChatModel>
}
