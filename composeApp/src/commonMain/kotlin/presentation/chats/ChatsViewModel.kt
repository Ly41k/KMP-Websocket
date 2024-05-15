package presentation.chats

import core.exception.ExceptionService
import core.store.ClearableBaseStore
import core.viewmodel.BaseViewModel
import core.viewmodel.WrappedStateFlow
import domain.interactor.ChatsInteractor
import domain.modes.presentation.chats.ChatItem
import domain.modes.presentation.message.MessageItem
import domain.viewmapper.toChatLastMessageItem
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
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.viewModelScope
import presentation.chats.models.ChatNavData
import presentation.chats.models.ChatsAction
import presentation.chats.models.ChatsEvent

class ChatsViewModel(
    private val chatsInteractor: ChatsInteractor,
    private val exceptionService: ExceptionService,
    private val messageStore: ClearableBaseStore<List<MessageItem>>,
    private val json: Json
) : BaseViewModel<ChatsViewState, ChatsAction, ChatsEvent>() {

    private val state: StateFlow<ChatsViewState> = _state

    private val _state: StateFlow<ChatsViewState>
        get() {
            val chatDataFlow = chatDataFlow()
            val chatLastMessageFlow = chatLastMessageFlow()
            val chatUnreadbleCountFlow = chatUnreadbleCountFlow()

            return merge(
                chatDataFlow,
                chatLastMessageFlow,
                chatUnreadbleCountFlow
            )
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

        is ChatsViewPartialState.ChatLastMessageLoaded -> prevState.copy(
            chatsViewData = prevState.chatsViewData?.copy(
                itemModels = prevState.chatsViewData.itemModels.map { chatItem ->
                    changes.items.firstOrNull { lastMessageItem ->
                        lastMessageItem.chatId == chatItem.id
                    }?.let { chatItem.copy(lastMessage = it.message) } ?: chatItem
                }
            )
        )

        is ChatsViewPartialState.ChatUnreadbleCountLoaded -> prevState.copy(
            chatsViewData = prevState.chatsViewData?.copy(
                itemModels = prevState.chatsViewData.itemModels.map { chatItem ->
                    changes.items.firstOrNull {
                        it.chatId == chatItem.id
                    }?.let { chatItem.copy(unreadCount = it.count) } ?: chatItem
                }
            )
        )
    }

    override fun viewStates(): WrappedStateFlow<ChatsViewState> = WrappedStateFlow(state)

    override fun obtainEvent(viewEvent: ChatsEvent) {
        when (viewEvent) {
            is ChatsEvent.ChatClick -> openChatScreenById(viewEvent.id, viewEvent.title)
        }
    }

    private fun openChatScreenById(id: Int, title: String) {
        val args = ChatNavData(id, title).run { this.getSerializedData(json) }
        viewAction = ChatsAction.OpenChatScreen(args)
    }

    private fun chatDataFlow(): Flow<ChatsViewPartialState> {
        return chatsInteractor.getChatsFlow()
            .map<List<ChatItem>, ChatsViewPartialState> { ChatsViewPartialState.ChatsDataLoaded(ChatsViewData(it)) }
            .onStart { emit(ChatsViewPartialState.ChatsDataLoading) }
            .catch { emit(ChatsViewPartialState.ChatsDataLoadingError(it)) }
    }

    private fun chatLastMessageFlow(): Flow<ChatsViewPartialState> {
        return messageStore.observe()
            .map { messages ->
                messages to state.value.chatsViewData?.let { viewData ->
                    viewData.itemModels.map { it.id to it.lastMessage }
                }.orEmpty()
            }
            .map { (messages, idWithLastMessage) ->
                idWithLastMessage.mapNotNull { (id, lastMessage) ->
                    messages.lastOrNull { id == it.chatId && lastMessage != it.message }
                }
            }.map<List<MessageItem>, ChatsViewPartialState> { messages ->
                ChatsViewPartialState.ChatLastMessageLoaded(messages.map { it.toChatLastMessageItem() })
            }
    }

    private fun chatUnreadbleCountFlow(): Flow<ChatsViewPartialState> {
        return messageStore.observe()
            .map { messages ->
                messages.filter { !it.isRead } to state.value.chatsViewData?.let { viewData ->
                    viewData.itemModels.map { it.id }
                }.orEmpty()
            }
            .map { (messages, ids) ->
                ids
                    .map { chatId -> chatId to messages.count { it.chatId == chatId } }
                    .map { (id, count) -> ChatUnreadbleCount(id, count) }
            }
            .map<List<ChatUnreadbleCount>, ChatsViewPartialState> {
                ChatsViewPartialState.ChatUnreadbleCountLoaded(it)
            }
    }
}
