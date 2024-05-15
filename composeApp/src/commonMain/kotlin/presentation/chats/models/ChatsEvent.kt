package presentation.chats.models

sealed interface ChatsEvent {
    data class ChatClick(val id: Int, val title: String) : ChatsEvent
}
