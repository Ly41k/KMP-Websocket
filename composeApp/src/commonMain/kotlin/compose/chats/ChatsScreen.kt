package compose.chats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.chats.ChatsViewModel

@Composable
@Preview
fun ChatsScreen(viewModel: ChatsViewModel) {
    val state = viewModel.state.collectAsState().value
    MessagesView(state = state) { }
}
