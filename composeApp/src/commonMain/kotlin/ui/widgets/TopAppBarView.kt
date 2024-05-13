package ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(
    modifier: Modifier = Modifier,
    onSettingButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Life", //TODO Need to use shared resources
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.colors.primaryText
                )

                Text(
                    text = "4Earth", //TODO Need to use shared resources
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.colors.primaryText
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = AppTheme.colors.secondaryBackground),
        actions = {
            IconButton(onClick = onSettingButtonClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search", //TODO Need to use shared resources
                    tint = AppTheme.colors.secondaryText
                )
            }
        }
    )
}
