package data.ktor.chats.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(val chatId: Int, val chatName: String)
