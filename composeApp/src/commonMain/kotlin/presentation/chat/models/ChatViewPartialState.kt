package presentation.chat.models

import domain.modes.presentation.message.MessageItem

sealed interface ChatViewPartialState {
    data class ChatDataLoaded(val messages: List<MessageItem>) : ChatViewPartialState
    data class ChatNavArgsLoaded(val chatId: Int, val chatName: String) : ChatViewPartialState
    data object ChatMessageSent : ChatViewPartialState
    data class ChatMessageSentError(val throwable: Throwable) : ChatViewPartialState
}
