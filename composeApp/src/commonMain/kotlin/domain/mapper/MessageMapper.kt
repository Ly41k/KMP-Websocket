package domain.mapper

import data.ktor.message.MessageResponse
import domain.modes.domain.message.MessageModel

fun MessageResponse.toMessageModel(): MessageModel {
    return MessageModel(
        chatId = requireNotNull(this.chatId),
        userId = requireNotNull(this.userId),
        chatName = requireNotNull(this.message),
        message = requireNotNull(this.message),
    )
}
