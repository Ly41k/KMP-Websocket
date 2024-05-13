package presentation.chats.models

data class ChatGroupItem(
    val groupName: String,
    val latestMessage: String? = null,
    val lastMessageStatus: MessageStatusTypes = MessageStatusTypes.NONE,
    val iconUrl: String? = null,
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

enum class MessageStatusTypes {
    NONE, SENT, DELIVERED, READ
}
