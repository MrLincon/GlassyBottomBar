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
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.mrlincon.glassybottombar.components.BackdropBlurState
import io.github.mrlincon.glassybottombar.components.backdropBlurChild

@Composable
internal fun FloatingBottomBar(
    navController: NavHostController,
    blurState: BackdropBlurState,
    items: List<FloatingNavItem>,
    isBlurSupported: Boolean,
    isBorderDisabled: Boolean,
    blurRadius: Float,
    noiseFactor: Float,
    bgTintColor: Color,
    bgCornerRadius: Dp,
    selectedCardColor: @Composable () -> Color,
    unselectedCardColor: @Composable () -> Color,
    cardOpacity: Float,
    cardCornerRadius: Dp,
    selectedIconColor: @Composable () -> Color,
    unselectedIconColor: @Composable () -> Color,
    barHeight: Dp,
    modifier: Modifier = Modifier
) {
    val glassShape = RoundedCornerShape(bgCornerRadius)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Theme-aware border — light in dark mode, dark in light mode
    val isLight = MaterialTheme.colorScheme.background.luminance() > 0.5f
    val borderHighlight = if (isLight) Color.Black.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.5f)
    val borderShadow = if (isLight) Color.Black.copy(alpha = 0.05f) else Color.White.copy(alpha = 0.1f)

    val backgroundModifier = if (isBlurSupported) {
        Modifier.backdropBlurChild(
            state = blurState,
            blurRadius = blurRadius,
            noiseFactor = noiseFactor,
            tintColor = bgTintColor
        )
    } else {
        Modifier.background(
            color = bgTintColor.copy(alpha = 0.85f),
            shape = glassShape
        )
    }

    Box(
        modifier = modifier
            .wrapContentWidth()
            .height(barHeight)
            .clip(glassShape)
            .then(backgroundModifier)
            .then(
                if (isBorderDisabled) Modifier
                else Modifier.border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(borderHighlight, borderShadow)
                    ),
                    shape = glassShape
                )
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
                    selectedCardColor = selectedCardColor(),
                    unselectedCardColor = unselectedCardColor().copy(alpha = cardOpacity),
                    selectedIconColor = selectedIconColor(),
                    unselectedIconColor = unselectedIconColor(),
                    cardCornerRadius = cardCornerRadius,
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