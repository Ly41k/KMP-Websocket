package presentation.chat

import core.di.Platform
import core.exception.ExceptionService
import core.store.ClearableBaseStore
import core.viewmodel.BaseViewModel
import core.viewmodel.WrappedStateFlow
import domain.interactor.MessageInteractor
import domain.modes.presentation.message.MessageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.viewModelScope
import presentation.chat.models.ChatAction
import presentation.chat.models.ChatEvent
import presentation.chat.models.ChatViewPartialState
import presentation.chat.models.ChatViewState
import presentation.chats.models.ChatNavData

class ChatViewModel(
    private val exceptionService: ExceptionService,
    private val messageInteractor: MessageInteractor,
    private val messageStore: ClearableBaseStore<List<MessageItem>>,
    private val json: Json
) : BaseViewModel<ChatViewState, ChatAction, ChatEvent>() {

    private val messageChanges =
        MutableSharedFlow<String>(extraBufferCapacity = 1, onBufferOverflow = DROP_OLDEST)
    private val messageReadEvent =
        MutableSharedFlow<Int>(extraBufferCapacity = 1, onBufferOverflow = DROP_OLDEST)
    private val sendMessageClicks =
        MutableSharedFlow<MessageItem>(extraBufferCapacity = 1, onBufferOverflow = DROP_OLDEST)
    private val _navArgs = MutableStateFlow<ChatNavData?>(null)
    private val navArgs = _navArgs.filterNotNull()
    private val state: StateFlow<ChatViewState> = _state
    private val _state: StateFlow<ChatViewState>
        get() {
            val messagesFlow = messagesFlow()
            val navArgsFlow = navArgsFlow()
            val sendMessageClicksFlow = sendMessageClicksFlow()

            return merge(
                messagesFlow,
                navArgsFlow,
                sendMessageClicksFlow
            )
                .catch { exceptionService.logException(it) }
                .runningFold(ChatViewState(), ::viewStateReducer)
                .flowOn(Dispatchers.IO) // TODO Need to use custom Dispatchers via Koin
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Eagerly,
                    initialValue = ChatViewState()
                )
        }

    init {
        messageReadEvent
            .map { chatId ->
                messageStore.update { storeItem ->
                    storeItem.map { if (it.chatId == chatId) it.copy(isRead = true) else it }
                }
            }
            .catch { exceptionService.logException(it) }
            .launchIn(viewModelScope)
    }

    private fun viewStateReducer(
        prevState: ChatViewState,
        changes: ChatViewPartialState
    ): ChatViewState = when (changes) {
        is ChatViewPartialState.ChatDataLoaded -> prevState.copy(
            chatViewData = prevState.chatViewData.copy(messages = changes.messages)
        )

        is ChatViewPartialState.ChatNavArgsLoaded -> prevState.copy(
            chatViewData = prevState.chatViewData.copy(chatId = changes.chatId, chatName = changes.chatName)
        )

        is ChatViewPartialState.ChatMessageSent -> prevState
        is ChatViewPartialState.ChatMessageSentError -> {
            exceptionService.logException(changes.throwable)
            prevState
        }
    }

    private fun messagesFlow(): Flow<ChatViewPartialState> {
        return messageStore.observe().distinctUntilChanged()
            .combine(navArgs) { a, b -> a.filter { b.id == it.chatId } to b.id }
            .onEach { messageReadEvent.tryEmit(it.second) }
            .map { it.first }
            .map<List<MessageItem>, ChatViewPartialState> { ChatViewPartialState.ChatDataLoaded(it) }
    }

    private fun navArgsFlow(): Flow<ChatViewPartialState> {
        return navArgs.map<ChatNavData, ChatViewPartialState> {
            println("navArgsFlow - $it")
            ChatViewPartialState.ChatNavArgsLoaded(it.id, it.title)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun sendMessageClicksFlow(): Flow<ChatViewPartialState> {
        return sendMessageClicks
            .flatMapLatest {
                flow { emit(messageInteractor.sendMessage(it)) }
                    .catch { ChatViewPartialState.ChatMessageSentError(it) }
            }.map { ChatViewPartialState.ChatMessageSent }
    }

    override fun viewStates(): WrappedStateFlow<ChatViewState> = WrappedStateFlow(state)

    override fun obtainEvent(viewEvent: ChatEvent) {
        when (viewEvent) {
            is ChatEvent.SendMessageClick -> sendMessage(viewEvent.message, viewEvent.platform)
            is ChatEvent.SetNavArgs -> obtainNavArgs(viewEvent.args)
            is ChatEvent.MessageChanged -> obtainMessageChanged(viewEvent.value)
            ChatEvent.BackClick -> onPopUp()
        }
    }

    private fun sendMessage(message: String, platform: Platform) {
        if (message.isBlank()) return
        val messageItem = MessageItem(
            userId = platform.name,
            chatId = state.value.chatViewData.chatId,
            chatName = state.value.chatViewData.chatName,
            message = message
        )
        sendMessageClicks.tryEmit(messageItem)
    }

    private fun obtainMessageChanged(value: String) {
        messageChanges.tryEmit(value)
    }

    private fun obtainNavArgs(args: String?) {
        args?.let { json.decodeFromString<ChatNavData>(it).let { navData -> _navArgs.value = navData } }
    }

    private fun onPopUp() {
        viewAction = ChatAction.PopUp
    }
}
