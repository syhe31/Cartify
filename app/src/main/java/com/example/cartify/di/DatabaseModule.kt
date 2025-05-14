package com.example.cartify.di

import android.content.Context
import androidx.room.Room
import com.example.cartify.data.source.local.database.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_database"
        )
            .fallbackToDestructiveMigration() // 刪除不匹配的資料庫，重新建立
            .build()
    }


    @Provides
    @Singleton
    fun provideProductDao(productDatabase: ProductDatabase) = productDatabase.productDao()
}