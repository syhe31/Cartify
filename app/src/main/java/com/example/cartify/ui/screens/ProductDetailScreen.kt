package com.example.cartify.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cartify.domain.model.ProductUiItem
import com.example.cartify.ui.components.HeaderBar
import com.example.cartify.ui.components.ImgWithCoil
import com.example.cartify.ui.components.TextBody1
import com.example.cartify.ui.components.TextH1
import com.example.cartify.ui.components.TextH2
import com.example.cartify.ui.theme.customColors

@Composable
fun ProductDetailScreen(product: ProductUiItem?) {

    Scaffold(
        topBar = { HeaderBar() }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding), color = Color.White) {

            if (product == null) {
                TextH1("Product not found")

            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        ImgWithCoil(product.img)
                        TextH1(product.title)
                        TextH2(product.subtitle)
                        TextH1(product.formattedPrice, color = MaterialTheme.customColors.textPrice)
                        TextBody1(product.description)
                    }
                }
            }

        }
    }

}