package com.example.cartify.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.cartify.R

@Composable
fun ImgWithCoil(
    imgUrl: Any,
    placeholder: Int = R.mipmap.ic_app_logo,
    error: Int = R.drawable.ic_logo_gray,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f),
) {
    AsyncImage(
        model = imgUrl,
        contentDescription = null,
        placeholder = painterResource(placeholder),
        error = painterResource(error),
        modifier = modifier
    )
}