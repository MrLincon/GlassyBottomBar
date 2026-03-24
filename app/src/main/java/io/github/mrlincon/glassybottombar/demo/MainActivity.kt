package io.github.mrlincon.glassybottombar.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.mrlincon.glassybottombar.GlassyBottomBarScaffold
import io.github.mrlincon.glassybottombar.demo.ui.theme.GlassyBottomBarTheme
import io.github.mrlincon.glassybottombar.navigation.FloatingNavItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlassyBottomBarTheme {
                val navController = rememberNavController()

                val items = listOf(
                    FloatingNavItem("home", "Home", R.drawable.ic_home),
                    FloatingNavItem("trending", "Trending", R.drawable.ic_trending),
                    FloatingNavItem("category", "Category", R.drawable.ic_category),
                    FloatingNavItem("setting", "Setting", R.drawable.ic_settings),
                )
                GlassyBottomBarScaffold(
                    navController = navController,
                    items = items,
                    disableBorder = true,
                    bgTintColor = { Color(0xFF262626) },
                    bgOpacity = 1f,
                    bgCornerRadius = 100.dp,
                    selectedCardColor = { Color.White },
                    unselectedCardColor = { Color.White },
                    cardOpacity = 0.12f,
                    selectedIconColor = { Color.Black },
                    unselectedIconColor = { Color.White.copy(alpha = 0.6f) }
                ){ paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") { HomeScreen() }
                        composable("trending") { TrendingScreen() }
                        composable("category") { CategoryScreen() }
                        composable("setting") { SettingsScreen() }
                    }
                }
            }
        }
    }
}