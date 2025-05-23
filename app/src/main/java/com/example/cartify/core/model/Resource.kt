package com.example.cartify.core.model

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>() //Nothing 不帶具體數據
    object Empty : Resource<Nothing>()
}