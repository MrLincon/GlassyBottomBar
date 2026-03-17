package io.github.mrlincon.glassybottombar

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    bottomPadding: Dp = 36.dp,
    content: @Composable (PaddingValues) -> Unit
) {
    val isBlurSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
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
            content(PaddingValues(bottom = 102.dp))
        }

        FloatingBottomBar(
            navController = navController,
            blurState = blurState,
            items = items,
            isBlurSupported = isBlurSupported,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPadding)
        )
    }
}