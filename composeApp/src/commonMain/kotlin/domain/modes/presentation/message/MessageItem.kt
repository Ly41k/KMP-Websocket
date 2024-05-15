package domain.modes.presentation.message

data class MessageItem(
    val userId: String,
    val chatId: Int,
    val chatName: String,
    val message: String,
    val isRead: Boolean = false
)
