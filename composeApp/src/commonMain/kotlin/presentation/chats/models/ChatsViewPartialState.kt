package presentation.chats.models

sealed interface ChatsViewPartialState {
    data object ChatsDataLoading : ChatsViewPartialState
    data class ChatsDataLoadingError(val error: Throwable) : ChatsViewPartialState
    data class ChatsDataLoaded(val chatsViewData: ChatsViewData) : ChatsViewPartialState
    data class ChatLastMessageLoaded(val items: List<ChatLastMessageItem>) : ChatsViewPartialState
    data class ChatUnreadbleCountLoaded(val items: List<ChatUnreadbleCount>) : ChatsViewPartialState
}

data class ChatLastMessageItem(
    val chatId: Int,
    val userId: String,
    val message: String?
) //TODO need to change this class to MessageItem

data class ChatUnreadbleCount(val chatId: Int, val count: Int)
