package domain.modes.presentation.chats

data class ChatItem(
    val id: Int,
    val name: String,
    val iconUrl: String,
    val lastMessage: String? = null,
    val authorLastMessage: String? = null,
    val unreadCount: Int = 0
) {
    fun getUnreadMessageCount(): String? {
        return when {
            unreadCount <= 0 -> null
            unreadCount in 1..99 -> unreadCount.toString()
            else -> "99+" //TODO Need to use shared resources
        }
    }
}
