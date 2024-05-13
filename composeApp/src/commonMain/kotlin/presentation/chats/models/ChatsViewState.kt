package presentation.chats.models

data class ChatsViewState(
    val chats: List<ChatGroupItem> = getMockkData()
)

private fun getMockkData(): List<ChatGroupItem> {
    return buildList {
        add(
            ChatGroupItem(
                groupName = "First Group",
                latestMessage = null,
                lastMessageStatus = MessageStatusTypes.READ,
                iconUrl = "https://images.pexels.com/photos/1391498/pexels-photo-1391498.jpeg",
                unreadCount = 5
            )
        )

        add(
            ChatGroupItem(
                groupName = "Second Group",
                latestMessage = null,
                lastMessageStatus = MessageStatusTypes.NONE,
                iconUrl = "https://images.pexels.com/photos/1758144/pexels-photo-1758144.jpeg",
                unreadCount = 0
            )
        )
    }
}
