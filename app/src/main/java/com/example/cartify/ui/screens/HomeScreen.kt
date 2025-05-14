package com.example.cartify.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cartify.R
import com.example.cartify.core.model.Resource
import com.example.cartify.core.utils.ProductMapper
import com.example.cartify.domain.model.ProductUiItem
import com.example.cartify.ui.activities.ProductDetailActivity
import com.example.cartify.ui.components.BannerCarousel
import com.example.cartify.ui.components.ImgWithCoil
import com.example.cartify.ui.components.TextH1
import com.example.cartify.ui.components.TextH2
import com.example.cartify.ui.theme.customColors
import com.example.cartify.viewmodel.ProductViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            // banner
            BannerCarousel()
            // 內容頁
            ProductPage()
        }
    }
}

@Composable
fun ProductPage(viewModel: ProductViewModel = hiltViewModel()) {

    // 全部商品列表
//    val productState by viewModel.productState.collectAsState()
    // 選中的商品分類
    val selectedProduct by viewModel.selectedProduct.collectAsState() // 觀察選中的商品
    val context = LocalContext.current
    // 過濾後的商品列表
    val filteredProductState by viewModel.filteredProductState.collectAsState()
    // 用一個局部變數來存當前的值
    val currentState = filteredProductState

    // 加載數據
    LaunchedEffect(Unit) {
        viewModel.getProductList()
        viewModel.uiEvent.collect { event ->
            Toast.makeText(context, event, Toast.LENGTH_SHORT).show()
        }
    }

    // 當選中的商品變更時觸發事件
    LaunchedEffect(selectedProduct) {
        selectedProduct?.let { product ->
            viewModel.sendUiEvent("item: ${product.itemId}, ${product.price}")
        }
    }

    // 分類標籤 UI
    val categories = ProductMapper.CATEGORY_MAP.values.toList()
    ProductTab(categories = categories,
        selectedCategory = viewModel.selectedCategory.collectAsState().value,
        onCategorySelected = { viewModel.selectCategory(it) })

    // 商品列表改變時觸發的 UI
    when (currentState) {
        is Resource.Loading -> {
            MessageBox("Loading...")
        }

        is Resource.Success -> {
            // 商品列表
            ProductGrid(currentState.data, onCartClick = { item ->
                viewModel.getProductById(item.itemId)
            }, onFavoriteClick = { item ->
                viewModel.toggleFavorite(item.itemId)
            }, onItemClick = { item ->
                val intent = Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra("product_data", item)
                }
                context.startActivity(intent)
            })
        }

        is Resource.Error -> {
            val errorMessage = currentState.exception.message
            MessageBox("Error! $errorMessage")
        }

        is Resource.Empty -> {
            MessageBox("暫時沒有商品喔！")
        }
    }
}

@Composable
fun MessageBox(message: String) {
    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
        TextH2("暫時沒有商品喔！")
    }
}


@Composable
fun ProductTab(
    categories: List<String>, selectedCategory: String, onCategorySelected: (String) -> Unit
) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp), //確保標籤不緊貼螢幕邊緣
        horizontalArrangement = Arrangement.spacedBy(8.dp) //籤之間的間距
    ) {
        items(categories) { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // 文字置中
            ) {
                Text(text = category,
                    modifier = Modifier
                        .clickable { onCategorySelected(category) }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    color = if (category == selectedCategory) MaterialTheme.customColors.textActivated else MaterialTheme.customColors.textNotActivated)

                Box(
                    modifier = Modifier
                        .width(30.dp)
                        .height(4.dp)
                        .background(
                            if (category == selectedCategory) MaterialTheme.customColors.primary else Color.Transparent,
                            shape = RoundedCornerShape(10.dp)
                        )
                )
            }

        }
    }

}


@Composable
fun ProductGrid(
    products: List<ProductUiItem>,
    onCartClick: (ProductUiItem) -> Unit = {},
    onFavoriteClick: (ProductUiItem) -> Unit = {},
    onItemClick: (ProductUiItem) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 固定兩列
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products, key = { item -> item.itemId }) { product ->
            ProductCard(
                product,
                product.isFavorited,
                onCartClick = onCartClick,
                onFavoriteClick = onFavoriteClick,
                onItemClick = onItemClick
            )
        }
    }
}


@Composable
fun ProductCard(
    item: ProductUiItem,
    isFavorited: Boolean,
    onCartClick: (ProductUiItem) -> Unit = {},
    onFavoriteClick: (ProductUiItem) -> Unit = {},
    onItemClick: (ProductUiItem) -> Unit = {},
) {

    Box(modifier = Modifier.clickable { onItemClick(item) }) {
        Column() {
            ImgWithCoil(item.img)
            TextH1(text = item.title, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            TextH2(text = item.subtitle, maxLines = 2)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                TextH1(
                    text = item.formattedPrice,
                    color = MaterialTheme.customColors.textPrice,
                    modifier = Modifier.weight(1f)
                )

                ImgWithCoil(R.drawable.ic_add_to_cart, modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onCartClick(item)
                    })

                ImgWithCoil(if (isFavorited) R.drawable.ic_heart_selected else R.drawable.ic_heart_un_selected,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onFavoriteClick(item)
                        })

            }
        }
        ImgWithCoil(
            R.drawable.ic_discount_tags, modifier = Modifier
                .size(50.dp)
                .padding(start = 4.dp)
        )
    }

}

