package io.github.mrlincon.glassybottombar.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class SettingsItem(
    val title: String,
    val subtitle: String,
    val hasToggle: Boolean = false
)

private val settingsGroups = listOf(
    "Downloads" to listOf(
        SettingsItem("Auto Download", "Download on Wi-Fi only", hasToggle = true),
        SettingsItem("Storage Location", "Internal storage"),
        SettingsItem("Clear Cache", "12.4 MB"),
    ),
    "About" to listOf(
        SettingsItem("Version", "1.0.0"),
        SettingsItem("Developer", "MrLincon"),
        SettingsItem("GitHub", "github.com/MrLincon"),
    )
)

@Composable
fun SettingsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 24.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                text = "Setting",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = (-1).sp
            )
        }

        settingsGroups.forEach { (groupName, items) ->
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = groupName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.45f),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )

                    items.forEachIndexed { index, item ->
                        SettingsRow(item = item)
                        if (index < items.lastIndex) {
                            Divider(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.07f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsRow(item: SettingsItem) {
    var toggled by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.45f)
            )
        }
        if (item.hasToggle) {
            Switch(
                checked = toggled,
                onCheckedChange = { toggled = it }
            )
        }
    }
}