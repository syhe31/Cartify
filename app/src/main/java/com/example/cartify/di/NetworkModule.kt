package com.example.cartify.di

import com.example.cartify.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


//@Qualifier 通過標記不同的依賴，如果之後比較多可以創建Qualifiers.kt統一檔案管理
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DynamicPathInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://run.mocky.io/v3/"

    @HeaderInterceptor
    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer your_token_here") // 添加認證 Token
                .addHeader("Accept", "application/json")               // 添加其他 Header
                .addHeader("qid", generateRandomQid()) // 添加動態生成的 qid
                .build()
            chain.proceed(request)
        }
    }

    private fun generateRandomQid(): String {
        // 生成一個隨機的 UUID 作為 qid
        return java.util.UUID.randomUUID().toString()
    }

    @DynamicPathInterceptor
    @Provides
    @Singleton
    fun provideDynamicPathInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val orignaUrl = originalRequest.url

            val newUrl = orignaUrl.newBuilder()
                .addPathSegment("api/v1")
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        @HeaderInterceptor headerInterceptor: Interceptor,
        @DynamicPathInterceptor dynamicPathInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) //連接超時
            .addInterceptor(headerInterceptor) // 自定義header
//            .addInterceptor(dynamicPathInterceptor) // 修改路徑
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }


}