package io.github.mrlincon.glassybottombar.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun FloatingBottomBarItem(
    selected: Boolean,
    label: String,
    @DrawableRes iconRes: Int,
    selectedCardColor: Color,
    unselectedCardColor: Color,
    selectedIconColor: Color,
    unselectedIconColor: Color,
    cardCornerRadius: Dp,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(cardCornerRadius)

    val containerColor by animateColorAsState(
        targetValue = if (selected) selectedCardColor else unselectedCardColor,
        animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic),
        label = "containerColor"
    )

    val iconTint by animateColorAsState(
        targetValue = if (selected) selectedIconColor else unselectedIconColor,
        animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic),
        label = "iconTint"
    )

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = iconTint
            )
            AnimatedVisibility(
                visible = selected,
                enter = expandHorizontally(
                    animationSpec = tween(300, easing = EaseInOutCubic)
                ) + fadeIn(tween(300)),
                exit = shrinkHorizontally(
                    animationSpec = tween(300, easing = EaseInOutCubic)
                ) + fadeOut(tween(150))
            ) {
                Row {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = label,
                        color = iconTint,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}