package compose.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.di.LocalPlatform
import domain.modes.presentation.message.MessageItem
import ui.theme.AppTheme

@Composable
fun MessageView(item: MessageItem) {
    val platform = LocalPlatform.current.name
    val isCurrentPlatform = platform == item.userId
    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.Bottom) {
        if (isCurrentPlatform) Spacer(Modifier.weight(0.2f))
        if (!isCurrentPlatform) MessageAvatar(item)

        Box(
            modifier = Modifier
                .weight(0.5f)
                .clip(RoundedCornerShape(20))
                .background(
                    if (isCurrentPlatform) AppTheme.colors.primaryAction.copy(alpha = .7f)
                    else AppTheme.colors.primaryAction.copy(alpha = .3f)
                )
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.userId,
                    style = AppTheme.typography.smallHeading,
                    modifier = Modifier,
                    lineHeight = 18.sp,
                    color = AppTheme.colors.secondaryText
                )
                Text(
                    text = item.message,
                    style = AppTheme.typography.normalText,
                    modifier = Modifier,
                    lineHeight = 18.sp,
                    color = AppTheme.colors.primaryText
                )
            }
        }
        if (!isCurrentPlatform) Spacer(Modifier.weight(0.5f))
        if (isCurrentPlatform) MessageAvatar(item)
    }
}

@Composable
fun MessageAvatar(item: MessageItem) {
    Box(
        modifier = Modifier.padding(horizontal = 8.dp).size(54.dp).clip(CircleShape).background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.userId[0].uppercase(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
