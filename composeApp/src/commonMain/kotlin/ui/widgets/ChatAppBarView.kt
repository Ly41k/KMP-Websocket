package ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBarView(
    modifier: Modifier = Modifier,
    title: String,
    isBackButtonShowed: Boolean,
    popUpClick: () -> Unit,
    settingClick: () -> Unit
) {
    TopAppBar(
        title = {
            Column(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.colors.primaryText
                )
            }
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.alpha(if (isBackButtonShowed) 1F else 0F),
                onClick = { popUpClick.invoke() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    tint = AppTheme.colors.primaryText,
                    contentDescription = "Back"
                )
            }
        },
        modifier = modifier.shadow(
            elevation = 5.dp,
            spotColor = AppTheme.colors.secondaryBackground
        ),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = AppTheme.colors.secondaryBackground),
        actions = {
            IconButton(onClick = { settingClick.invoke() }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Setting",
                    tint = AppTheme.colors.secondaryText
                )
            }
        }
    )
}
