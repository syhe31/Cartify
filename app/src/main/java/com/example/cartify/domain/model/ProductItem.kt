package com.example.cartify.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ProductItem(
    val itemId: String,
    val categoryId: Int,
    val img: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val price: String,
    val isFavorited: Boolean,
) : Parcelable

// UI 專用資料結構
@Parcelize
data class ProductUiItem(
    val itemId: String,
    val categoryId: Int,
    val img: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val formattedPrice: String, // 格式化後的價格字串
    val categoryName: String, // 中文分類名稱
    val isFavorited: Boolean,
) : Parcelable

