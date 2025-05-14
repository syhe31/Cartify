package com.example.cartify.core.utils

import android.util.Log

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23) // Logcat TAG 長度限制
    }

// 定義擴展
fun Any.logd(message: String, tag: String? = null) {
    val logTag = tag ?: TAG
    Log.d(logTag, message)
}

fun Any.logi(message: String, tag: String? = null) = {
    val logTag = tag ?: TAG
    Log.i(logTag, message)
}

fun Any.logw(message: String, tag: String? = null) = {
    val logTag = tag ?: TAG
    Log.w(logTag, message)
}

fun Any.loge(message: String, throwable: Throwable? = null, tag: String? = null) {
    val logTag = tag ?: TAG
    if (throwable != null) {
        Log.e(TAG, message, throwable)
    } else {
        Log.e(TAG, message)
    }
}