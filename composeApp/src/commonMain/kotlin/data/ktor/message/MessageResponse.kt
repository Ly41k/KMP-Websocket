package data.ktor.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val userId: String? = null,
    val chatId: Int? = null,
    val chatName: String? = null,
    val message: String? = null
)
