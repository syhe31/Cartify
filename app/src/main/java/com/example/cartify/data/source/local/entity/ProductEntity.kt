package com.example.cartify.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey val itemId: String,
    val categoryId: Int,
    val img: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val price: String,
    val isFavorited: Boolean,
)
