package com.example.cartify.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cartify.ui.screens.LoginScreen
import com.example.cartify.ui.theme.CartifyTheme


class LoginActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartifyTheme {
                LoginScreen()
            }
        }
    }
}