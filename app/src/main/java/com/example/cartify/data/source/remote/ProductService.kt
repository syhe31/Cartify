package com.example.cartify.data.source.remote

import com.example.cartify.core.model.ApiResponse
import com.example.cartify.data.source.remote.request.ProductRequestBody
import com.example.cartify.domain.model.ProductItem
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductService {

    @POST("886ddb17-1ad6-46d7-8353-41d699450947")
    suspend fun getProductList(
        @Body product : ProductRequestBody
    ): ApiResponse<List<ProductItem>>

}