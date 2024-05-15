package presentation

import core.exception.ExceptionService
import domain.interactor.MessageInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MainViewModel(
    private val messageInteractor: MessageInteractor,
    private val exceptionService: ExceptionService
) : ViewModel() {

    fun launch() {
        launchWebSocket()
    }

    private fun launchWebSocket() {
        viewModelScope.launch(Dispatchers.Default) {
            if (!messageInteractor.isSessionActive()) {
                messageInteractor.launchWebSocket()
                    .flowOn(Dispatchers.IO)
                    .catch { exceptionService.logException(it) }
                    .launchIn(viewModelScope)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch { messageInteractor.closeWebSocket() }
    }
}
