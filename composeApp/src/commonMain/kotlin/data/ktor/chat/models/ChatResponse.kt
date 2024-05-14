package data.ktor.chat.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val chatId: Int? = null,
    val chatName: String? = null,
    val iconUrl: String? = null
)
