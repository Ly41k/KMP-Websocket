package compose.chats

import androidx.compose.runtime.Composable
import core.viewmodel.collectAsState
import core.viewmodel.observeAsState
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.chats.ChatsViewModel
import presentation.chats.models.ChatsAction

@Composable
@Preview
fun ChatsScreen(
    viewModel: ChatsViewModel,
    navAction: (ChatsAction) -> Unit
) {
    val state = viewModel.viewStates().collectAsState().value
    val action = viewModel.viewActions().observeAsState().value

    MessagesView(state = state) { viewModel.obtainEvent(it) }
    action?.let(navAction)
}
