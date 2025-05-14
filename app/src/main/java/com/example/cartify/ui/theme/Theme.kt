package com.example.cartify.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// 自定義顏色類
data class CustomColors(
    val primary: Color,
    val pagePrimaryBg: Color,
    val headerPrimaryBg: Color,
    val headerPrimaryText: Color,
    val textPrimary: Color,
    val textActivated: Color,
    val textNotActivated: Color,
    val textPrice: Color,
)

// 定義 CompositionLocal 提供器
val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        primary = Color.Unspecified,
        pagePrimaryBg = Color.Unspecified,
        headerPrimaryBg = Color.Unspecified,
        headerPrimaryText = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textActivated = Color.Unspecified,
        textNotActivated = Color.Unspecified,
        textPrice = Color.Unspecified,
    )
}


// 擴展訪問自定義的顏色
val MaterialTheme.customColors: CustomColors
    @Composable
    get() = LocalCustomColors.current


@Composable
fun CartifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )

    // 自定義顏色
    val customColors = CustomColors(
        primary = Primary,
        pagePrimaryBg = PagePrimaryBg,
        headerPrimaryBg = HeaderPrimaryBg,
        headerPrimaryText = HeaderPrimaryText,
        textPrimary = TextPrimary,
        textActivated = TextActivated,
        textNotActivated = TextNotActivated,
        textPrice = TextPrice,
    )

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

//    CompositionLocalProvider(LocalCustomColors provides customColors) {
//        MaterialTheme(
//            colorScheme = colorScheme,
//            typography = Typography,
//        ) {
//            content(customColors)
//        }
//    }


}