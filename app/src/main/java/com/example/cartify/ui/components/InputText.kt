package com.example.cartify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cartify.R
import com.example.cartify.ui.theme.customColors


@Composable
fun InputText(
    text: String = "",
    placeholder: String = "",
    isPassword: Boolean = false,
    prefixIcon: @Composable ((Boolean) -> Unit)? = null,   // 可選的前綴圖示
    suffixIcon: @Composable (() -> Unit)? = null,   // 可選的後綴圖示
    onTextChange: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
) {

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        cursorBrush = SolidColor(MaterialTheme.customColors.primary), // 設置光標顏色
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
        ),
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                // 更新焦點
                isFocused = it.isFocused
                onFocusChange(isFocused)
            }
            .height(44.dp)
            .background(
                if (isFocused) MaterialTheme.customColors.primary else
                    Color.Gray, shape = RoundedCornerShape(8.dp)
            )
            .padding(1.5.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp
        ),
        decorationBox = { innerTextField ->

            Row(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(6.dp))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                // 前綴圖示
                prefixIcon?.let {
                    it(isFocused)
                }

                // 輸入框
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty()) {
                        Text(
                            placeholder, style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        )
                    }
                    innerTextField()
                }

                // 清除
                if (isFocused && text.isNotEmpty()) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { onTextChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }

                if (isPassword) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { passwordVisible = !passwordVisible }) {
                        ImgWithCoil(
                            if (passwordVisible) R.drawable.ic_pwd_eye_open else R.drawable.ic_pwd_eye_close,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }


                // 後墜圖示
                suffixIcon?.let {
                    it()
                }

            }

        }
    )
}