package presentation

import data.ktor.KtorWebsocketClient
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ChatsViewModel(
    private val client: KtorWebsocketClient
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch { client.close() }
    }
}
