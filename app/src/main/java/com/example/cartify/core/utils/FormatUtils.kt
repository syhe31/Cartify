package com.example.cartify.core.utils

import java.text.DecimalFormat

// 擴展函數：格式化價格為千分位
fun String.toFormattedPrice(): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(this.toDoubleOrNull() ?: 0.0)
}