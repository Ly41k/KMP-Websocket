package presentation.chat.models

import core.di.Platform

sealed interface ChatEvent {
    data class SendMessageClick(val message: String, val platform: Platform) : ChatEvent
    data class SetNavArgs(val args: String?) : ChatEvent
    data class MessageChanged(val value: String) : ChatEvent
    data object BackClick : ChatEvent
}
