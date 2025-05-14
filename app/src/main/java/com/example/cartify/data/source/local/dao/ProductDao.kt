package com.example.cartify.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cartify.data.source.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    suspend fun getProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM product_table WHERE itemId = :itemId")
    suspend fun getProductById(itemId: String): ProductEntity

    @Update
    suspend fun updateProduct(product: ProductEntity)

}