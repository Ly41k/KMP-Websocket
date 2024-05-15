package domain.repository

import data.ktor.message.MessageResponse
import data.ktor.message.WebSocketClient
import domain.mapper.toMessageModel
import domain.modes.domain.message.MessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

interface MessageRepository {
    fun initMessageWebSocket(): Flow<MessageModel>
    suspend fun isSessionActive(): Boolean
    suspend fun closeMessageWebSocket()
}

class MessageRepositoryImpl(
    private val webSocketClient: WebSocketClient,
    private val json: Json
) : MessageRepository {
    override fun initMessageWebSocket(): Flow<MessageModel> {
        return webSocketClient.getStateStream()
            .map { json.decodeFromString<MessageResponse>(it).toMessageModel() }
    }

    override suspend fun isSessionActive(): Boolean {
        return webSocketClient.isSessionActive()
    }


    override suspend fun closeMessageWebSocket() {
        webSocketClient.close()
    }
}
