package com.example.cartify.data.repository

import com.example.cartify.core.model.ApiResponse
import com.example.cartify.core.model.Resource
import com.example.cartify.core.utils.logd
import com.example.cartify.core.utils.loge
import com.example.cartify.core.utils.logw


abstract class BaseRepository {
    inline fun <T> safeApiCall(action: () -> ApiResponse<T>): Resource<T> {
        return try {
            val response = action()
            if (response.status == "success") {
                logd("safeApiCall success")
                if (response.data == null ||
                    response.data is List<*> && (response.data as List<*>).isEmpty()
                ) {
                    // 如果數據為空
                    logw("safeApiCall empty")
                    Resource.Empty
                } else {
                    Resource.Success(response.data)
                }
            } else {
                loge("safeApiCall error: ${response.message}")
                Resource.Error(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            loge("safeApiCall error2: ${e.message}")
            Resource.Error(e)
        }
    }
}