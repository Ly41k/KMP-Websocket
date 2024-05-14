package domain.interactor

import domain.modes.presentation.chats.ChatItem
import domain.repository.ChatRepository
import domain.viewmapper.toChatItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ChatsInteractor {
    fun getChatsFlow(): Flow<List<ChatItem>>
}

class ChatInteractorImpl(private val repository: ChatRepository) : ChatsInteractor {
    override fun getChatsFlow(): Flow<List<ChatItem>> = flow { emit(repository.getChats().map { it.toChatItem() }) }
}
