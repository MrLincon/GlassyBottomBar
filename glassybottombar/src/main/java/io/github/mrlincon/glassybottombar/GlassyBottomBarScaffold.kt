package io.github.mrlincon.glassybottombar

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.mrlincon.glassybottombar.components.BackdropBlurState
import io.github.mrlincon.glassybottombar.components.backdropBlurSource
import io.github.mrlincon.glassybottombar.navigation.FloatingBottomBar
import io.github.mrlincon.glassybottombar.navigation.FloatingNavItem

@Composable
fun GlassyBottomBarScaffold(
    navController: NavHostController,
    items: List<FloatingNavItem>,

    // Layout
    bottomPadding: Dp = 36.dp,
    barHeight: Dp = 66.dp,

    // Blur
    enableBlur: Boolean = true,
    disableBorder: Boolean = false,
    blurRadius: Float = 0.5f,
    noiseFactor: Float = 0.2f,

    // Background (the glass bar)
    bgTintColor: @Composable () -> Color = { MaterialTheme.colorScheme.surface },
    bgOpacity: Float = 0.05f,
    bgCornerRadius: Dp = 100.dp,

    // Selected pill (card)
    selectedCardColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    unselectedCardColor: @Composable () -> Color = { MaterialTheme.colorScheme.surfaceVariant },
    cardOpacity: Float = 0.5f,
    cardCornerRadius: Dp = 100.dp,

    // Icon colors
    selectedIconColor: @Composable () -> Color = { MaterialTheme.colorScheme.onPrimary },
    unselectedIconColor: @Composable () -> Color = { MaterialTheme.colorScheme.onSurface },

    content: @Composable (PaddingValues) -> Unit
) {
    val isBlurSupported = enableBlur && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val blurState = remember { BackdropBlurState() }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = if (isBlurSupported) {
                Modifier
                    .fillMaxSize()
                    .backdropBlurSource(blurState)
            } else {
                Modifier.fillMaxSize()
            }
        ) {
            content(PaddingValues(bottom = barHeight + bottomPadding))
        }

        FloatingBottomBar(
            navController = navController,
            blurState = blurState,
            items = items,
            isBlurSupported = isBlurSupported,
            isBorderDisabled = disableBorder,
            blurRadius = blurRadius,
            noiseFactor = noiseFactor,
            bgTintColor = bgTintColor().copy(alpha = bgOpacity),
            bgCornerRadius = bgCornerRadius,
            selectedCardColor = selectedCardColor,
            unselectedCardColor = unselectedCardColor,
            cardOpacity = cardOpacity,
            cardCornerRadius = cardCornerRadius,
            selectedIconColor = selectedIconColor,
            unselectedIconColor = unselectedIconColor,
            barHeight = barHeight,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPadding)
        )
    }
}