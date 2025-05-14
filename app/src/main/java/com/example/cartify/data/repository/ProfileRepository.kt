package com.example.cartify.data.repository

import com.example.cartify.core.model.Resource
import com.example.cartify.data.fake.FakeProfileDiscount
import com.example.cartify.domain.model.DiscountItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor() {
    private val profileDiscounts = FakeProfileDiscount.profileDiscounts

    fun getDiscountFlow(): Flow<Resource<List<DiscountItem>>> {
        return flow {
            // 先發射加載狀態
            emit(Resource.Loading)
            // 模擬網路請求
            delay(2000)
            emit(Resource.Success(profileDiscounts))
        }
    }
}