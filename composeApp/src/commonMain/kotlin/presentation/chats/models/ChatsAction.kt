package presentation.chats.models

sealed interface ChatsAction {
    data class OpenChatScreen(val args: String) : ChatsAction
}
