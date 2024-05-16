package domain.modes.domain.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageModel(
    val userId: String,
    val chatId: Int,
    val chatName: String,
    val message: String
)
