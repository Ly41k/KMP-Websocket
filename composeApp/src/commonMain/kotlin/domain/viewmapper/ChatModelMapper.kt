package domain.viewmapper

import domain.modes.domain.chats.ChatModel
import domain.modes.presentation.chats.ChatItem

fun ChatModel.toChatItem(): ChatItem {
    return ChatItem(
        id = this.chatId,
        name = this.chatName,
        iconUrl = this.iconUrl,
        lastMessage = null,
        unreadCount = 0
    )
}
