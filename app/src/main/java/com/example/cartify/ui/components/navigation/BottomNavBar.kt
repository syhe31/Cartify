package com.example.cartify.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cartify.ui.components.ImgWithCoil
import com.example.cartify.ui.theme.customColors


@Composable
fun BottomNavBar(
    navController: NavHostController,
    items: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
        BottomNavItem.Explore,
        BottomNavItem.Notification,
        BottomNavItem.Cart,
        BottomNavItem.Profile,
    )
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {

        val currentRoute =
            navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            // 是否選中
            val isSelected = item.route == currentRoute

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // 禁用水波纹效果
                    ) {
                        navController.navigateSingleTop(item.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ImgWithCoil(
                        if (isSelected) item.iconActivated else item.iconNotActivated,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = item.label,
                        fontSize = 14.sp,
                        color = if (isSelected) MaterialTheme.customColors.textActivated else MaterialTheme.customColors.textNotActivated
                    )
                }
            }

        }


    }


}

