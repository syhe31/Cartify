package com.example.cartify.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cartify.ui.screens.HomeScreen
import com.example.cartify.ui.screens.ProfileScreen
import com.example.cartify.ui.theme.customColors

@Composable
fun NavigationGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(BottomNavItem.Home.route) { HomeScreen(navController) }
        composable(BottomNavItem.Explore.route) {
            Screen(
                BottomNavItem.Explore.label,
                navController
            )
        }
        composable(BottomNavItem.Notification.route) {
            Screen(
                BottomNavItem.Notification.label,
                navController
            )
        }
        composable(BottomNavItem.Cart.route) { Screen(BottomNavItem.Cart.label, navController) }
        composable(BottomNavItem.Profile.route) { ProfileScreen(navController) }


        composable(
            "productDetail/{productId}", // 定義路由的名稱和參數
            arguments = listOf(navArgument("productId") {
                type = NavType.StringType
            }) // 定義參數 定了路由參數 productId 的類型是String
        ) { backStackEntry -> //backStackEntry 是傳遞給 composable 的一個參數，包含當前導航堆疊的所有信息
            // 從路由中的參數獲取值 可以使用 backStackEntry 來訪問從導航中傳遞過來的參數。在這裡，我們使用它來獲取
            // backStackEntry.arguments 是一個 Bundle?，用來存儲導航過程中傳遞的所有參數
            // getString("productId") 用來從 arguments 中提取 productId。如果找不到該參數，則使用 ?: "" 來設置一個默認值 ""，表示如果沒有傳遞 productId，則設為 ""
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            // 呼叫ProductDetailScreen，並將取得的productId傳遞過去
//            ProductDetailScreen(navController, productId)
        }
    }
}

// 擴展函數，避免重複使用
fun NavController.navigateSingleTop(route: String) {
    navigate(route) {
        popUpTo("home") { inclusive = true }
        launchSingleTop = true
    }
}


//
@Composable
fun Screen(name: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello $name",
            style = TextStyle(color = MaterialTheme.customColors.textPrimary)
        )

        //攔截返回
//        BackHandler {
//            navController.navigate("home")
//        }
    }
}
