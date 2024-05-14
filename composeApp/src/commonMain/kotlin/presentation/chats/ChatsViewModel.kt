package presentation.chats

import core.exception.ExceptionService
import domain.interactor.ChatsInteractor
import domain.modes.presentation.chats.ChatItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ChatsViewModel(
    private val chatsInteractor: ChatsInteractor,
    private val exceptionService: ExceptionService
) : ViewModel() {
    val state: StateFlow<ChatsViewState> = _state

    private val _state: StateFlow<ChatsViewState>
        get() {
            val chatDataFlow = chatDataFlow()
            return merge(chatDataFlow)
                .catch { exceptionService.logException(it) }
                .runningFold(ChatsViewState(), ::viewStateReducer)
                .flowOn(Dispatchers.IO)
                .stateIn(
                    scope = viewModelScope,
                    started = Eagerly,
                    initialValue = ChatsViewState()
                )
        }

    private fun viewStateReducer(
        prevState: ChatsViewState,
        changes: ChatsViewPartialState
    ): ChatsViewState = when (changes) {
        ChatsViewPartialState.ChatsDataLoading -> prevState.copy(isChatsLoading = true)
        is ChatsViewPartialState.ChatsDataLoadingError -> prevState.copy(isChatsLoading = false)
        is ChatsViewPartialState.ChatsDataLoaded -> prevState.copy(
            isChatsLoading = false,
            chatsViewData = changes.chatsViewData
        )
    }

    private fun chatDataFlow(): Flow<ChatsViewPartialState> {
        return chatsInteractor.getChatsFlow()
            .map<List<ChatItem>, ChatsViewPartialState> { ChatsViewPartialState.ChatsDataLoaded(ChatsViewData(it)) }
            .onStart { emit(ChatsViewPartialState.ChatsDataLoading) }
            .catch { emit(ChatsViewPartialState.ChatsDataLoadingError(it)) }
    }
}
