package compose.chats

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.chats.ChatsViewModel
import presentation.chats.models.ChatsViewState

@Composable
@Preview
fun ChatsScreen(viewModel: ChatsViewModel) {
    MessagesView(state = ChatsViewState()) { }
}
