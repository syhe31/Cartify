package com.example.cartify.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cartify.ui.theme.customColors


@Composable
fun SolidBtn(
    text: String = "",
    onClick: () -> Unit = {},
    enable: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.customColors.primary,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.LightGray
    ),
    content: @Composable () -> Unit = {},
    modifier: Modifier = Modifier.height(44.dp)
) {
    Button(
        onClick = onClick,
        enabled = enable,
        modifier = modifier,
        shape = shape,
        colors = colors
    ) {
        if (text.isNotBlank()) {
            TextH1(text, color = Color.White)
        } else {
            content()
        }
    }
}


@Composable
fun OutlinedBtn(
    text: String = "",
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    borderColor: Color = MaterialTheme.customColors.primary,
    disabledBorderColor: Color = Color.Gray,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit = {},
    modifier: Modifier = Modifier.height(44.dp)
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = BorderStroke(
            width = borderWidth,
            color = if (enabled) borderColor else disabledBorderColor
        )
    ) {
        if (text.isNotBlank()) {
            TextH1(
                text,
                color = if (enabled) MaterialTheme.customColors.primary else Color.Gray
            )
        } else {
            content()
        }
    }
}

