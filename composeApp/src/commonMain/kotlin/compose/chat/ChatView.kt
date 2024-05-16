package compose.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import core.di.LocalPlatform
import presentation.chat.models.ChatEvent
import presentation.chat.models.ChatViewState
import ui.theme.AppTheme
import ui.widgets.ChatAppBarView

@Composable
fun ChatView(
    state: ChatViewState,
    eventHandler: (ChatEvent) -> Unit
) {

    var textState by remember { mutableStateOf(TextFieldValue("")) } //TODO Need to move to state
    val keyboardController = LocalSoftwareKeyboardController.current
    val platform = LocalPlatform.current

    Scaffold(
        topBar = {
            ChatAppBarView(
                title = state.chatViewData.chatName,
                isBackButtonShowed = true,
                popUpClick = { eventHandler.invoke(ChatEvent.BackClick) },
                settingClick = {}
            )
        },
        containerColor = AppTheme.colors.primaryBackground
    ) {
        Surface(
            modifier = Modifier.padding(it).fillMaxSize(),
            color = AppTheme.colors.primaryBackground
        ) {

            Column {
                LazyColumn(Modifier.weight(1f)) {
                    items(state.chatViewData.messages) { item -> MessageView(item) }
                }

                Row(
                    modifier = Modifier
                        .imePadding()
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                shape = RoundedCornerShape(200.dp),
                                color = AppTheme.colors.secondaryBackground.copy(alpha = 0.7f)
                            )
                            .padding(16.dp)
                    ) {
                        BasicTextField(
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Send,
                                autoCorrect = false
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = { keyboardController?.hide() }),
                            modifier = Modifier.fillMaxWidth(),
                            value = textState,
                            onValueChange = { textState = it }
                        )
                        if (textState.text.isEmpty())
                            Text(
                                text = "Enter Message ...", //TODO Need to add placeholder in BasicTextField
                                color = AppTheme.colors.primaryText
                            )
                    }
                    Spacer(modifier = Modifier.size(10.dp))

                    Button(
                        onClick = {
                            eventHandler.invoke(ChatEvent.SendMessageClick(textState.text, platform))
                            textState = TextFieldValue("")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colors.primaryAction
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }
}
