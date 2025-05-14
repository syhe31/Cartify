package com.example.cartify.core.utils

import com.example.cartify.data.source.local.entity.ProductEntity
import com.example.cartify.domain.model.ProductItem
import com.example.cartify.domain.model.ProductUiItem

object ProductMapper {
    // 商品分類對應表
    val CATEGORY_MAP = mapOf(
        1 to "3C",
        2 to "家電",
        3 to "運動",
        4 to "美妝",
        5 to "服飾",
        6 to "保健\\食品",
        7 to "生活",
        8 to "其他"
    )

    fun toProductEntity(product: ProductItem): ProductEntity {
        return ProductEntity(
            itemId = product.itemId,
            categoryId = product.categoryId,
            img = product.img,
            title = product.title,
            subtitle = product.subtitle,
            description = product.description,
            price = product.price,
            isFavorited = product.isFavorited
        )
    }

    fun toProductItem(entity: ProductEntity): ProductItem {
        return ProductItem(
            itemId = entity.itemId,
            categoryId = entity.categoryId,
            img = entity.img,
            title = entity.title,
            subtitle = entity.subtitle,
            description = entity.description,
            price = entity.price,
            isFavorited = entity.isFavorited
        )
    }

    fun toUiItem(product: ProductItem): ProductUiItem {
        return ProductUiItem(
            itemId = product.itemId,
            categoryId = product.categoryId,
            img = product.img,
            title = product.title,
            subtitle = product.subtitle,
            description = product.description,
            formattedPrice = "${product.price.toFormattedPrice()}元",
            categoryName = CATEGORY_MAP[product.categoryId] ?: "未知分類",
            isFavorited = product.isFavorited,
        )
    }


    fun toEntityList(products: List<ProductItem>): List<ProductEntity> {
        return products.map { toProductEntity(it) }
    }

    fun toProductList(products: List<ProductEntity>): List<ProductItem> {
        return products.map { toProductItem(it) }
    }

    fun toUiList(products: List<ProductItem>): List<ProductUiItem> {
        return products.map { toUiItem(it) }
    }

}