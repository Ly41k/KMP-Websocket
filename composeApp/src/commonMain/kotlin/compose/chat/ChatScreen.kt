package compose.chat

import androidx.compose.runtime.Composable
import core.viewmodel.collectAsState
import core.viewmodel.observeAsState
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.chat.ChatViewModel
import presentation.chat.models.ChatAction
import presentation.chat.models.ChatEvent.SetNavArgs

@Composable
@Preview
fun ChatScreen(
    viewModel: ChatViewModel,
    args: String?,
    navAction: (ChatAction) -> Unit
) {

    viewModel.obtainEvent(SetNavArgs(args))
    val state = viewModel.viewStates().collectAsState().value
    val action = viewModel.viewActions().observeAsState().value

    ChatView(state = state) { viewModel.obtainEvent(it) }
    action?.let(navAction)
}
