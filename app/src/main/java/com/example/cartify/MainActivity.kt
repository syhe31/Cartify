package com.example.cartify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cartify.ui.components.navigation.BottomNavItem
import com.example.cartify.ui.screens.MainScreen
import com.example.cartify.ui.theme.CartifyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = intent.getStringExtra("startDestination") ?: BottomNavItem.Home.route

        setContent {
            CartifyTheme {
                MainScreen(startDestination)
            }
        }
    }
}