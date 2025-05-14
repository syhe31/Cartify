package com.example.cartify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartify.core.model.Resource
import com.example.cartify.domain.model.DiscountUiItem
import com.example.cartify.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase,
) : ViewModel() {

    // 優惠
    private val _discountState = MutableStateFlow<Resource<List<DiscountUiItem>>>(Resource.Empty)
    val discountState: StateFlow<Resource<List<DiscountUiItem>>> = _discountState.asStateFlow()

    fun getProfileDiscountList() {
        viewModelScope.launch {
            useCase.getDiscount().collect { result ->
                _discountState.value = result
            }
        }
    }


}