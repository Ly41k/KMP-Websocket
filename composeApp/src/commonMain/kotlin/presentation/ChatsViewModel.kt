package presentation

import data.ktor.WebSocketClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ChatsViewModel(
    private val client: WebSocketClient
) : ViewModel() {

    val state: Flow<String> = client.getStateStream()
        .onEach { println("onEach - $it") }
        .map { it }
        .catch { println("catch - ${it.message}") }
        .stateIn(viewModelScope, SharingStarted.Eagerly, String())


    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch { client.close() }
    }
}
