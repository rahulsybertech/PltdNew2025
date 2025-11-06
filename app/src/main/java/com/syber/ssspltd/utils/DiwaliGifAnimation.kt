package com.syber.ssspltd.utils

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext

@Composable
fun DiwaliGifAnimation(gifUrl: String) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            // Use ImageDecoder for API 28+, otherwise fallback to GifDecoder
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(gifUrl)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
        contentDescription = "Diwali Animation",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}



