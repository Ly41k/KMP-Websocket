package compose.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.chats.ChatsViewState
import presentation.chats.models.ChatsEvent
import ui.theme.AppTheme
import ui.widgets.TopAppBarView

@Composable
fun MessagesView(
    state: ChatsViewState,
    eventHandler: (ChatsEvent) -> Unit
) {
    Scaffold(
        topBar = { TopAppBarView { } },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                shape = RoundedCornerShape(50)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }
        },
        containerColor = AppTheme.colors.primaryBackground
    ) {
        LazyColumn(modifier = Modifier.padding(it).fillMaxSize()) {
            items(state.chatsViewData?.itemModels.orEmpty()) {
                ChatGroupView(it) { println("OnClick") }
            }
        }
    }
}
