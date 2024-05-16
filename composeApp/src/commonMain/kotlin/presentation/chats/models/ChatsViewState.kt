package presentation.chats.models

data class ChatsViewState(
    val chatsViewData: ChatsViewData? = null, // TODO Need to delete this data class and list from this class move to state
    val isChatsLoading: Boolean = false
)
