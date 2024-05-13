package compose.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.chats.models.ChatGroupItem
import presentation.chats.models.MessageStatusTypes
import ui.theme.AppTheme

@Composable
fun ChatGroupView(
    item: ChatGroupItem,
    onItemClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth().heightIn(max = 70.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.Black),
                onClick = { onItemClick.invoke() }
            )
    ) {

        Box(
            modifier = Modifier.padding(8.dp).size(54.dp).clip(CircleShape).background(Color.Cyan),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "GG",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp, end = 8.dp)
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = item.groupName,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = AppTheme.colors.primaryText
                )

                Icon(
                    modifier = Modifier.size(14.dp).padding(start = 2.dp),
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = when (item.lastMessageStatus) {
                        MessageStatusTypes.NONE -> Color.Transparent
                        MessageStatusTypes.READ -> Color.Green
                        MessageStatusTypes.SENT,
                        MessageStatusTypes.DELIVERED -> AppTheme.colors.secondaryText
                    }
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    end = 8.dp
                )
            ) {
                Text(
                    modifier = Modifier.weight(1f).padding(bottom = 12.dp),
                    text = item.latestMessage.orEmpty(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = AppTheme.colors.secondaryText
                )

                Spacer(modifier = Modifier.weight(1f))

                item.getUnreadMessageCount()?.let {
                    Badge(
                        modifier = Modifier,
                        containerColor = Color.LightGray

                    ) { Text(text = it) }
                }

            }

            HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp).background(AppTheme.colors.secondaryText))
        }
    }
}