package domain.interactor

import core.store.ClearableBaseStore
import domain.modes.presentation.message.MessageItem
import domain.repository.MessageRepository
import domain.viewmapper.toMessageItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface MessageInteractor {
    fun launchWebSocket(): Flow<Unit>
    suspend fun isSessionActive(): Boolean
    suspend fun closeWebSocket()
}

class MessageInteractorImpl(
    private val messageRepository: MessageRepository,
    private val messageStore: ClearableBaseStore<List<MessageItem>>
) : MessageInteractor {
    override fun launchWebSocket(): Flow<Unit> {
        return messageRepository.initMessageWebSocket()
            .map { it.toMessageItem() }
            .onEach { println("observeMessage - $it") }
            .onEach { updateMessageStore(it) }
            .map { }
    }

    override suspend fun closeWebSocket() {
        messageRepository.closeMessageWebSocket()
    }

    override suspend fun isSessionActive(): Boolean {
        return messageRepository.isSessionActive()
    }

    private fun updateMessageStore(item: MessageItem) {
        messageStore.update {
            buildList {
                addAll(it)
                add(item)
            }
        }
    }
}
