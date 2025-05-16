package com.example.cartify.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun CommonDialog(
    title: String = "提示",
    message: String = "",
    btnNum: Int = 2,
    confirmText: String = "確定",
    cancelText: String = "取消",
    onDismissRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        content = {

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextH1(title)
                    TextH2(message)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SolidBtn(
                            text = confirmText,
                            onClick = {
                                onConfirm()
                                onDismissRequest()
                            },
                            modifier = Modifier
                                .weight(1f)
                        )
                        if (btnNum > 1)
                            OutlinedBtn(
                                text = cancelText,
                                onClick = {
                                    onCancel()
                                    onDismissRequest()
                                },
                                modifier = Modifier
                                    .weight(1f)
                            )
                    }
                }
            }
        }
    )
}