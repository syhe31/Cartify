package com.example.cartify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cartify.R
import com.example.cartify.core.model.Resource
import com.example.cartify.ui.components.TextH1
import com.example.cartify.ui.components.TextH2
import com.example.cartify.ui.theme.customColors
import com.example.cartify.viewmodel.ProfileViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Composable
fun ProfileScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box()
        {
            TopBg()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    UserInfo()
                    ProfileDiscount()
                    OrderCard()
                    ActivityCard()
                    ServiceCard()
                }
            }
        }
    }


}

@Composable
fun TopBg() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
            .background(MaterialTheme.customColors.primary)
    )
}


@Composable
fun UserInfo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),  // 讓 Row 的高度與最高子元素對齊 (Column 的高度將與包含它的 Row 中最高的子元素)
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_avatar_capoo),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(100.dp))
                .border(2.dp, Color.White, RoundedCornerShape(100.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            TextH1(text = "Hi, Capoo", color = Color.White)
            TextH2(text = "會員編號：123456789", color = Color.White)
        }
    }
}


@Composable
fun ProfileDiscount(viewModel: ProfileViewModel = hiltViewModel()) {

    val discountState by viewModel.discountState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProfileDiscountList()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        val currentDiscountState = discountState
        when (currentDiscountState) {
            is Resource.Loading -> {
                repeat(4) { // 顯示 4 個占位符
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 模擬數據區域的占位
                        Box(
                            modifier = Modifier
                                .fillMaxWidth() // 讓 Box 填滿 Column 的寬度
                                .height(46.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .placeholder(
                                    visible = true,
                                    color = Color.Gray,
                                    highlight = PlaceholderHighlight.shimmer(), //動畫
                                )
                        )

                    }
                }
            }

            is Resource.Success -> {
                currentDiscountState.data.forEach { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                    ) {
                        TextH1(item.formattedAmount, color = Color.White)
                        TextH2(item.categoryName, color = Color.White)
                    }

                }
            }

            else -> {

            }

        }

    }
}


@Composable
fun OrderCard() {
    ProfileCard(
        "訂單", listOf(
            "訂單",
            "買過清單",
            "禮物紀錄",
            "週期購",
            "未付款",
            "未出貨",
            "已出貨",
            "門市未取",
        )
    )
}

@Composable
fun ActivityCard() {
    ProfileCard(
        "活動專區", listOf(
            "登記活動",
            "Plus會員",
            "卡片申辦",
            "C幣好康",
            "點數",
            "得獎公告",
            "活動總覽",
            "邀請朋友",
        )
    )
}

@Composable
fun ServiceCard() {
    ProfileCard(
        "服務", listOf(
            "帳戶設定",
            "綁定",
            "舊機回收",
            "我的紅包",
            "暫收款",
            "中獎發票",
            "補貨通知",
        )
    )
}


@Composable
fun ProfileCard(title: String, items: List<String>, clickCallBack: (String) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextH1(title)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 每行固定顯示 4 個
                items.chunked(4).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { item ->
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        clickCallBack(item)
                                    },
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    Alignment.CenterVertically
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_logo_gray),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                                TextH2(item)
                            }
                        }

                        // 如果不足 4 個，補空的
                        repeat(4 - rowItems.size) {
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                }
            }
        }
    }
}


