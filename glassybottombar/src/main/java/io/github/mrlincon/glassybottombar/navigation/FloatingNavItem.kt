package io.github.mrlincon.glassybottombar.navigation

import androidx.annotation.DrawableRes

data class FloatingNavItem(
    val route: String,
    val label: String,
    @param:DrawableRes val iconRes: Int
)