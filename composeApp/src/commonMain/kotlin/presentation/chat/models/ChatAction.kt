package presentation.chat.models

sealed interface ChatAction {
    data object PopUp : ChatAction
}
