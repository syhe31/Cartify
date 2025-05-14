package com.example.cartify.domain.usecase

import com.example.cartify.core.model.Resource
import com.example.cartify.data.repository.ProfileRepository
import com.example.cartify.domain.model.DiscountUiItem
import com.example.cartify.core.utils.ProfileDiscountMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    fun getDiscount(): Flow<Resource<List<DiscountUiItem>>> {
        val result = profileRepository.getDiscountFlow()
        return result.map {
            when (it) {
                is Resource.Success -> {
                    // 成功才轉換成UI資料結構
                    Resource.Success(ProfileDiscountMapper.toUiList(it.data))
                }
                is Resource.Error -> Resource.Error(it.exception)
                is Resource.Empty -> Resource.Empty
                is Resource.Loading -> Resource.Loading
            }
        }
    }

}

