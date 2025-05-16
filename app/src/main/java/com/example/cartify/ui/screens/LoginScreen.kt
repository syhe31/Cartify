package com.example.cartify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cartify.MainActivity
import com.example.cartify.R
import com.example.cartify.core.UserSession
import com.example.cartify.ui.activities.LoginActivity
import com.example.cartify.ui.components.ImgWithCoil
import com.example.cartify.ui.components.InputText
import com.example.cartify.ui.components.SolidBtn
import com.example.cartify.ui.components.navigation.BottomNavItem
import com.example.cartify.ui.theme.customColors

@Composable
fun LoginScreen() {
    Scaffold(
        modifier = Modifier.imePadding()
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.customColors.pagePrimaryBg)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Spacer(Modifier.height(50.dp))
                Logo()
                Spacer(modifier = Modifier.height(10.dp))
                UserAndPwd()
                Spacer(Modifier.height(50.dp))
            }
        }
    }
}

@Composable
fun Logo() {
    Box {
        Text(
            text = "Welcome",
            style = TextStyle(
                color = MaterialTheme.customColors.primary,
                fontSize = 32.sp,
                drawStyle = Stroke(
                    width = 10.0f, //像素寬度
                    join = StrokeJoin.Round //描繪的時候加入直線跟曲線處理
                )
            )
        )
        Text(
            text = "Welcome",
            style = TextStyle(
                color = Color.White,
                fontSize = 32.sp
            )
        )
    }

    ImgWithCoil(
        R.drawable.ic_logo_transparent,
        modifier = Modifier.size(100.dp)
    )
}


@Composable
fun UserAndPwd() {
    val context = LocalContext.current as LoginActivity
    var inputUserName by remember { mutableStateOf("") }
    var inputPwd by remember { mutableStateOf("") }


    // 檢查兩個輸入框是否都有值
    val isFormValid = inputUserName.isNotBlank() && inputPwd.isNotBlank()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        InputText(
            text = inputUserName,
            placeholder = "帳號",
            prefixIcon = { isFocused ->
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    tint = if (isFocused) MaterialTheme.customColors.primary else Color.Gray
                )
            },
            onTextChange = { inputUserName = it },
        )

        InputText(
            text = inputPwd,
            placeholder = "密碼",
            isPassword = true,
            prefixIcon = { isFocused ->
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "",
                    tint = if (isFocused) MaterialTheme.customColors.primary else Color.Gray
                )
            },
            onTextChange = { inputPwd = it },
        )

        SolidBtn("登入", onClick = {

            // 模擬登入功能
            UserSession.login(inputUserName)

            val intent = android.content.Intent(context, MainActivity::class.java)
            // 跳轉到會員中心
            intent.putExtra("startDestination", BottomNavItem.Profile.route)
            context.startActivity(intent)
            context.finish()

        }, enable = isFormValid, modifier = Modifier.fillMaxWidth())

    }
}


