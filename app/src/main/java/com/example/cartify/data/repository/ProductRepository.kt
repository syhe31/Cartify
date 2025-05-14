package com.example.cartify.data.repository

import com.example.cartify.core.model.Resource
import com.example.cartify.core.utils.ProductMapper
import com.example.cartify.core.utils.logd
import com.example.cartify.data.fake.FakeProductData
import com.example.cartify.data.source.local.dao.ProductDao
import com.example.cartify.data.source.remote.ProductService
import com.example.cartify.data.source.remote.request.ProductRequestBody
import com.example.cartify.domain.model.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val productService: ProductService
) : BaseRepository() {

    // 模擬的商品數據
    private val productList = FakeProductData.products

    // 用於內存緩存
    private val productCache = mutableMapOf<String, ProductItem>()

    // 獲取商品列表
    fun getProductListFlow(): Flow<Resource<List<ProductItem>>> = flow {


        // 先拿room數據
        val localData = productDao.getProducts()
        if (localData.isNotEmpty()) {
            // 有的話 轉成產品列表 先發射
            emit(Resource.Success(ProductMapper.toProductList(localData)))
        } else { // 用else是因為目前無法修改後端資料，若資料有修改會無法同步，所以優先使用本地的
            // 先發射加載狀態
            emit(Resource.Loading)
            // 獲取遠端數據
            val remoteData =
                safeApiCall { productService.getProductList(ProductRequestBody(loginName = "1234")) }

            if (remoteData is Resource.Success) {
                // 轉換格式後 數據保存到room
                productDao.insertProducts(ProductMapper.toEntityList(remoteData.data))
            }
            emit(remoteData)
        }
    }

    // 加載更多數據的方法
    fun loadMoreData(newProducts: List<ProductItem>) {
        //todo 可以將新數據合併並更新列表，這裡簡單模擬
    }

    // 根據 ID 獲取商品數據
    suspend fun getProductById(itemId: String): ProductItem? {
        // 先從內存緩存中查找
        return productCache[itemId]
            ?: run {
                // 再從本地數據庫查找
                val productEntity = productDao.getProductById(itemId)
                productEntity.let { ProductMapper.toProductItem(it) }.also {
                    // 順便更新內存
                    productCache[itemId] = it
                }
            }
    }


    // 更新產品
    suspend fun updateProduct(product: ProductItem) {
        logd("updateProduct: ${product.itemId}, ${product.isFavorited}")
        // 更新內存緩存
        productCache[product.itemId] =
            productCache[product.itemId]?.copy(isFavorited = product.isFavorited)
                ?: product // 如果不存在，則新增到緩存
        // 更新本地數據庫
        productDao.updateProduct(ProductMapper.toProductEntity(product)) // 更新本地數據庫
    }
}