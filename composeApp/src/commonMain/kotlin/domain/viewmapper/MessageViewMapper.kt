package domain.viewmapper

import domain.modes.domain.message.MessageModel
import domain.modes.presentation.message.MessageItem
import presentation.chats.models.ChatLastMessageItem

fun MessageModel.toMessageItem(): MessageItem {
    return MessageItem(
        userId = this.userId,
        chatId = this.chatId,
        chatName = this.chatName,
        message = this.message,
        isRead = false
    )
}

fun MessageItem.toMessageModel(): MessageModel {
    return MessageModel(
        userId = this.userId,
        chatId = this.chatId,
        chatName = this.chatName,
        message = this.message,
    )
}

fun MessageItem.toChatLastMessageItem(): ChatLastMessageItem = ChatLastMessageItem(chatId, userId, message)
