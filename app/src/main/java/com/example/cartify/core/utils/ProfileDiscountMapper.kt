package com.example.cartify.core.utils

import com.example.cartify.domain.model.DiscountItem
import com.example.cartify.domain.model.DiscountUiItem

object ProfileDiscountMapper {

    // 優惠分類對應表
    val CATEGORY_MAP = mapOf(
        1 to "C幣",
        2 to "折價券",
        3 to "電子票券",
        4 to "紅利金",
    )

    val UNIT_LIST = listOf(
        "C幣", "紅利金"
    )


    fun toUiItem(profileDiscountItem: DiscountItem): DiscountUiItem {
        return DiscountUiItem(
            categoryId = profileDiscountItem.categoryId,
            amount = profileDiscountItem.amount,
            formattedAmount = if (CATEGORY_MAP[profileDiscountItem.categoryId] in UNIT_LIST) "$${profileDiscountItem.amount}" else "${profileDiscountItem.amount}",
            categoryName = CATEGORY_MAP[profileDiscountItem.categoryId] ?: "未知分類",
        )
    }


    fun toUiList(profileDiscount: List<DiscountItem>): List<DiscountUiItem> {
        return profileDiscount.map { toUiItem(it) }
    }
}