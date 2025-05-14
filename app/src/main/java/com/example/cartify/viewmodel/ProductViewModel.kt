package com.example.cartify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartify.core.model.Resource
import com.example.cartify.core.utils.ProductMapper
import com.example.cartify.domain.model.ProductItem
import com.example.cartify.domain.model.ProductUiItem
import com.example.cartify.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val useCase: ProductUseCase,
) : ViewModel() {

    // 通知用戶的訊息
    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent: SharedFlow<String> = _uiEvent.asSharedFlow()

    // 選中的商品
    private val _selectedProduct = MutableStateFlow<ProductItem?>(null)
    val selectedProduct: StateFlow<ProductItem?> = _selectedProduct.asStateFlow()

    // 選中的分類
    private val _selectedCategory = MutableStateFlow(ProductMapper.CATEGORY_MAP.values.first())
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // 商品列表
    private val _productState = MutableStateFlow<Resource<List<ProductItem>>>(Resource.Empty)
//    val productState: StateFlow<Resource<List<ProductItem>>> = _productState.asStateFlow()

    // 過濾後的商品列表 (UI 模型)
    val filteredProductState: StateFlow<Resource<List<ProductUiItem>>> =
        // 結合兩個flow，其中一個數值改變都會觸發
        combine(_productState, _selectedCategory) { allProducts, category ->
            when (allProducts) {
                is Resource.Success -> {
                    // 轉換成 UI 模型
                    val uiProducts = ProductMapper.toUiList(allProducts.data)
                    // 過濾
                    val filteredList = uiProducts.filter { it.categoryName == category }
                    if (filteredList.isEmpty()) {
                        Resource.Empty
                    } else {
                        Resource.Success(filteredList)
                    }
                }

                is Resource.Loading -> Resource.Loading
                is Resource.Error -> Resource.Error(allProducts.exception)
                is Resource.Empty -> Resource.Empty
            }
        }.stateIn( //普通的 Flow 轉換成 StateFlow
            viewModelScope, //協程作用域
            SharingStarted.WhileSubscribed(5000), //若超時(ms)。最後一個訂閱者5000毫秒内沒有新的訂閱者出現，StateFlow 會停止收集上游 Flow，以釋放資源。當有新的訂閱者出現，會重新開始收集。
            Resource.Loading // 初始值为加載中
        )


    // 獲取商品列表
    fun getProductList() {
        viewModelScope.launch {
            useCase.getProductList().collect { result ->
                _productState.value = result
            }
        }
    }

    // 根據 itemId 查找原始商品資料
    fun getProductById(itemId: String) {
        viewModelScope.launch {
            val product = useCase.getProductById(itemId)
            _selectedProduct.value = product
        }
    }

    // 切換商品收藏狀態
    fun toggleFavorite(itemId: String) {
        viewModelScope.launch {
            // 調用 UseCase 處理收藏切換邏輯
            useCase.toggleFavorite(itemId)

            // 獲取更新後的列表並更新 UI
            getProductList()
        }
    }

    // 改變當前選中的分類
    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun sendUiEvent(event: String) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

}