package presentation.chats

sealed interface ChatsViewPartialState {
    data object ChatsDataLoading : ChatsViewPartialState
    data class ChatsDataLoadingError(val error: Throwable) : ChatsViewPartialState
    data class ChatsDataLoaded(val chatsViewData: ChatsViewData) : ChatsViewPartialState
}
