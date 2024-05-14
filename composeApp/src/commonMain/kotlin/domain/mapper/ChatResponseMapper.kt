package domain.mapper

import core.utils.orZero
import data.ktor.chat.models.ChatResponse
import domain.modes.domain.chats.ChatModel

fun ChatResponse.toChatModel(): ChatModel {
    return ChatModel(
        chatId = this.chatId.orZero(),
        chatName = this.chatName.orEmpty(),
        iconUrl = this.iconUrl.orEmpty()
    )
}
