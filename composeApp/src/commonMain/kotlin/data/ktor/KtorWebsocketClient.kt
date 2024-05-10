package data.ktor

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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface KtorWebsocketClient {
    fun getStateStream(): Flow<String>
    suspend fun sendAction(action: InputMessage)
    suspend fun close()
}

class KtorWebsocketClientImpl(
    private val client: HttpClient
) : KtorWebsocketClient {

    private var session: WebSocketSession? = null

    override fun getStateStream(): Flow<String> {
        return flow {
            session = client.webSocketSession {
                url("wss://echo.websocket.org")
            }
            val messageStates = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull {
                    it.readText()
                }

            emitAll(messageStates)
        }
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
