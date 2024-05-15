package data.ktor.message

import core.utils.Constants.BASE_WS_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface WebSocketClient {
    fun getStateStream(): Flow<String>
    suspend fun isSessionActive(): Boolean
    suspend fun sendAction(action: InputMessage)
    suspend fun close()
}

class KtorWebSocketClient(private val httpClient: HttpClient) : WebSocketClient {

    private var session: WebSocketSession? = null

    override fun getStateStream(): Flow<String> {
        return flow {
            session = httpClient.webSocketSession { url(BASE_WS_URL) }

            val messageStates = session?.let {
                it.incoming
                    .consumeAsFlow()
                    .filterIsInstance<Frame.Text>()
                    .mapNotNull { frame -> frame.readText() }
            }
            messageStates?.let { emitAll(it) }
        }
    }

    override suspend fun isSessionActive(): Boolean {
        return session?.isActive == true
    }

    override suspend fun sendAction(action: InputMessage) {
        session?.outgoing?.send(
            Frame.Text(Json.encodeToString(action))
        )
    }

    override suspend fun close() {
        session?.close()
        session = null
    }
}
