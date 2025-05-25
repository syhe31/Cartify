package com.example.cartify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cartify.ui.components.TextH1
import com.example.cartify.ui.components.TextH2
import com.example.cartify.ui.theme.customColors
import kotlinx.coroutines.launch

@Composable
fun ExploreScreen(navController: NavHostController) {


//    val tabs = List(10) { "Tab ${it + 1}" } // 假设有很多 Tab
    val tabs = listOf("精選", "新品", "熱銷", "趨勢榜", "送禮指南", "好物說", "每日驚喜", "特惠")
    // PagerState，控制跟觀察 Pager 的狀態
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White, // TabRow 背景色
            edgePadding = 0.dp, // 可以邊緣內邊距，默認 52.dp
            divider = {}, // 移除默認的分割線
            indicator = { tabPositions ->
                // 自定義指示器
                if (tabPositions.isNotEmpty()) {
                    TabIndicator(tabPositions[pagerState.currentPage])
                }
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelect = pagerState.currentPage == index
                TabItem(isSelect, title, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            PageContent(tabs[pageIndex])
        }
    }
}

@Composable
fun TabIndicator(tabPosition: TabPosition) {
    Box(
        Modifier
            .tabIndicatorOffset(tabPosition)
            .fillMaxWidth() // Box 填滿 Tab 的宽度
            .height(4.dp) // 指示器整体高度
            .background(Color.Transparent) // 背景透明
    ) {
        Box(
            Modifier
                .align(Alignment.BottomCenter) // 放在中間底部
                .height(4.dp)
                .fillMaxWidth(0.5f) // 寬度為 Tab 宽度的 50%
                .background(
                    color = MaterialTheme.customColors.primary,
                    shape = RoundedCornerShape(10.dp)
                )
        )
    }
}

@Composable
fun TabItem(isSelect: Boolean, title: String, onClick: () -> Unit) {
    Tab(
        text = {
            TextH2(
                title,
                color = if (isSelect) MaterialTheme.customColors.textActivated
                else MaterialTheme.customColors.textNotActivated
            )
        },
        selected = isSelect,
        onClick = onClick,
    )
}


@Composable
fun PageContent(pageTitle: String) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        TextH1("Hello $pageTitle")
    }
}