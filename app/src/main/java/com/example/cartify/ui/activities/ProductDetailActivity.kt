package com.example.cartify.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cartify.domain.model.ProductUiItem
import com.example.cartify.ui.screens.ProductDetailScreen
import com.example.cartify.ui.theme.CartifyTheme

class ProductDetailActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartifyTheme {
                val productData = intent.getParcelableExtra<ProductUiItem>("product_data")
                ProductDetailScreen(product = productData)

            }
        }
    }
}