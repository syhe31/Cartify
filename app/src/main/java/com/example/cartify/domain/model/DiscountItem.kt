package com.example.cartify.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DiscountItem(
    val categoryId: Int,
    val amount: Int,
) : Parcelable

// UI 專用資料結構
@Parcelize
data class DiscountUiItem(
    val categoryId: Int,
    val amount: Int,
    val formattedAmount: String, // 格式化後的價格字串
    val categoryName: String, // 中文分類名稱
) : Parcelable

