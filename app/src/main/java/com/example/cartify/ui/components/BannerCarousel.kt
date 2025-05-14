package com.example.cartify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cartify.R
import com.example.cartify.ui.theme.customColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun BannerCarousel(
    banners: List<Int> = listOf(
        R.drawable.banner_home_1,
        R.drawable.banner_home_2,
        R.drawable.banner_home_3,
        R.drawable.banner_home_4
    ),
    onClick: (page: Int) -> Unit = {},
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { banners.size })


    // 自动轮播效果
    LaunchedEffect(pagerState) {
        while (true) {
            yield() // 让 Compose 停止阻塞 UI
            delay(3000) // 切换间隔时间
            if (!pagerState.isScrollInProgress) {
                val nextPage = (pagerState.currentPage + 1) % banners.size
                pagerState.animateScrollToPage(nextPage) // 滚动到下一个页面
            }
        }
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(0.dp)
        ) { index ->
            Image(
                painter = painterResource(id = banners[index]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onClick(index) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }),
            )
        }

        // 指示器
        DotsIndicator(
            pagerState = pagerState,
            pageCount = banners.size,
            activeColor = MaterialTheme.customColors.primary,
            inactiveColor = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                .graphicsLayer { shadowElevation = 0f }

        )
    }

}

@Composable
fun DotsIndicator(
    pagerState: PagerState,
    pageCount: Int,
    activeColor: Color,
    inactiveColor: Color,
    modifier: Modifier = Modifier,
    dotSize: Dp = 6.dp,
    spacing: Dp = 4.dp
) {
    Row(
        modifier = modifier.padding(spacing),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            Row {
                Box(
                    modifier = Modifier
                        .size(if (isSelected) dotSize * 1.5f else dotSize)
                        .clip(CircleShape)
                        .background(if (isSelected) activeColor else inactiveColor)
                )
                if (index != pageCount - 1) Spacer(Modifier.width(spacing))
            }
        }
    }
}