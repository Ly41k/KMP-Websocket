package compose.chats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.ChatsViewModel
import ui.theme.AppTheme

@Composable
@Preview
fun ChatsScreen(viewModel: ChatsViewModel) {
    val state = viewModel.state.collectAsState("").value

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = AppTheme.typography.mediumHeading,
            text = state,
            color = AppTheme.colors.primaryText,
            textAlign = TextAlign.Center
        )
    }
}
