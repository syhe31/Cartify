package com.example.cartify.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object UserSession {

    var userName by mutableStateOf("")

    val isLogin: Boolean
        get() = userName.isNotEmpty()

    fun login(name: String) {
        userName = name
    }

    fun logout() {
        userName = ""
    }

}