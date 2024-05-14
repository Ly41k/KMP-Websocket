package data.ktor.chats


import data.ktor.chats.models.ChatResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorChatsRemoteDataSource(private val httpClient: HttpClient) {
    suspend fun performGetChats(): List<ChatResponse> = httpClient.get { url { path("chats") } }.body()
}
