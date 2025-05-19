package com.example.cartify.data.repository

import com.example.cartify.core.model.ApiResponse
import com.example.cartify.core.model.Resource
import com.example.cartify.core.utils.logd
import com.example.cartify.core.utils.loge
import com.example.cartify.core.utils.logw
import kotlinx.coroutines.delay


abstract class BaseRepository {
    suspend inline fun <T> safeApiCall(
        maxRetryTimes: Int = 0, // 最大重試次數，默認0次，表示不重試
        delayMillis: Long = 1000, // 重试间隔默认1秒
        action: () -> ApiResponse<T>
    ): Resource<T> {

        var attempt = 0
        var lastException: Exception? = null

        while (attempt <= maxRetryTimes) {
            try {
                val response = action()
                if (response.status == "success") {

                    // 資料為空檢查
                    if (response.data == null ||
                        response.data is List<*> && (response.data as List<*>).isEmpty()
                    ) {
                        logw("safeApiCall empty")
                        return Resource.Empty
                    }
                    // 成功
                    logd("safeApiCall success")
                    return Resource.Success(response.data)
                } else {
                    loge("safeApiCall error: ${response.message}")
                    return Resource.Error(Exception(response.message ?: "Unknown error"))
                }
            } catch (e: Exception) {
                lastException = e
                loge("safeApiCall exception: ${e.message}")
                attempt++
                if (attempt > maxRetryTimes) {
                    break
                }
                logd("safeApiCall retrying... attempt: $attempt")
                delay(delayMillis)  // 非阻塞延迟
            }
        }
        // 失敗
        return Resource.Error(lastException ?: Exception("Unknown error"))
    }
}