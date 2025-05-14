package com.example.cartify.domain.usecase

import com.example.cartify.core.model.Resource
import com.example.cartify.core.utils.ProductMapper
import com.example.cartify.core.utils.logd
import com.example.cartify.data.repository.ProductRepository
import com.example.cartify.domain.model.ProductItem
import com.example.cartify.domain.model.ProductUiItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    // 獲取商品列表
    fun getProductList(): Flow<Resource<List<ProductItem>>> {
        return productRepository.getProductListFlow()
    }


    // 根據 itemId 查找原始商品資料
    suspend fun getProductById(itemId: String): ProductItem? {
        return productRepository.getProductById(itemId)
    }

    // 切換商品收藏狀態
    suspend fun toggleFavorite(itemId: String) {
        // 獲取當前產品
        val product = productRepository.getProductById(itemId)
        if (product != null) {
            // 切換收藏狀態
            logd("toggleFavorite: ${product.isFavorited}")
            val updatedProduct = product.copy(isFavorited = !product.isFavorited)
            logd("updatedProduct: ${updatedProduct.isFavorited}")
            // 更新本地數據庫
            productRepository.updateProduct(updatedProduct)

            // todo 同步到遠端服務器
        }
    }

}