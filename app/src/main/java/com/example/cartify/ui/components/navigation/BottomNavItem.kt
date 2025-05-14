package com.example.cartify.ui.components.navigation

import com.example.cartify.R

sealed class BottomNavItem(
    val route: String,
    val iconNotActivated: Int,
    val iconActivated: Int,
    val label: String
) {
    object Home : BottomNavItem("home", R.drawable.ic_home_not_activated, R.drawable.ic_home_activated, "首頁")
    object Explore : BottomNavItem("explore", R.drawable.ic_explore_not_activated, R.drawable.ic_explore_activated, "探索")
    object Notification : BottomNavItem("notification", R.drawable.ic_notification_not_activated, R.drawable.ic_notification_activated, "通知")
    object Cart : BottomNavItem("cart", R.drawable.ic_cart_not_activated, R.drawable.ic_cart_activated, "購物車")
    object Profile : BottomNavItem("profile", R.drawable.ic_user_not_activated, R.drawable.ic_user_activated, "會員")
}