package presentation.chat.models

import domain.modes.presentation.message.MessageItem

data class ChatViewState(val chatViewData: ChatViewData = ChatViewData())

data class ChatViewData(
    val messages: List<MessageItem> = emptyList(),
    val chatName: String = "",
    val chatId: Int = Int.MIN_VALUE,
    val message: String = ""
) // TODO This class is redundant
