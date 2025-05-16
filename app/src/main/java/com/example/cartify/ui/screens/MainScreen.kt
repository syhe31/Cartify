package com.example.cartify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cartify.ui.components.navigation.BottomNavBar
import com.example.cartify.ui.components.HeaderBar
import com.example.cartify.ui.components.navigation.BottomNavItem
import com.example.cartify.ui.components.navigation.NavigationGraph

@Composable
fun MainScreen(startDestination: String) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        topBar = { HeaderBar() },
        bottomBar = {
            // 根據路由決定是否顯示底部導航
            if (isShowBottomNavBar(currentRoute)) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding), color = Color.White) {
            NavigationGraph(navController, startDestination)
        }
    }
}

fun isShowBottomNavBar(currentRoute: String?): Boolean {
    return currentRoute in listOf(
        BottomNavItem.Home.route,
        BottomNavItem.Explore.route,
        BottomNavItem.Notification.route,
        BottomNavItem.Cart.route,
        BottomNavItem.Profile.route)
}
