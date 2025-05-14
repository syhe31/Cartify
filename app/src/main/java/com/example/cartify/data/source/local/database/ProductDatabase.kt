package com.example.cartify.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cartify.data.source.local.dao.ProductDao
import com.example.cartify.data.source.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

}