package presentation.chats

import data.ktor.WebSocketClient
import data.ktor.chats.KtorChatsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ChatsViewModel(
    private val client: WebSocketClient,
    private val dataSource: KtorChatsRemoteDataSource
) : ViewModel() {

//    val state: Flow<String> = client.getStateStream()
//        .onEach { println("onEach - $it") }
//        .map { it }
//        .catch { println("catch - ${it.message}") }
//        .stateIn(viewModelScope, SharingStarted.Eagerly, String())

    val state = flow {
        emit(dataSource.performGetChats())
    }
        .onEach { println("onEach - $it") }
        .map { it }
        .flowOn(Dispatchers.IO)
        .catch { println("catch - ${it.message}") }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch { client.close() }
    }
}
