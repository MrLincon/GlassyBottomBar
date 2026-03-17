package io.github.mrlincon.glassybottombar.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.mrlincon.glassybottombar.components.BackdropBlurState
import io.github.mrlincon.glassybottombar.components.backdropBlurChild

private val GlassShape = RoundedCornerShape(60.dp)

@Composable
internal fun FloatingBottomBar(
    navController: NavHostController,
    blurState: BackdropBlurState,
    items: List<FloatingNavItem>,
    isBlurSupported: Boolean,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val backgroundModifier = if (isBlurSupported) {
        Modifier.backdropBlurChild(
            state = blurState,
            blurRadius = 25f,
            noiseFactor = 0.2f,
            tintColor = Color.White.copy(alpha = 0.01f)
        )
    } else {
        // Fallback — solid frosted surface for API 30
        Modifier.background(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
            shape = GlassShape
        )
    }

    Box(
        modifier = modifier
            .wrapContentWidth()
            .height(66.dp)
            .clip(GlassShape)
            .then(backgroundModifier)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.5f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = GlassShape
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items.forEach { item ->
                FloatingBottomBarItem(
                    selected = currentRoute == item.route,
                    label = item.label,
                    iconRes = item.iconRes,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(items.first().route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}