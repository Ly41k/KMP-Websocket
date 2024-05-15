package domain.modes.domain.message

data class MessageModel(
    val userId: String,
    val chatId: Int,
    val chatName: String,
    val message: String
)
