package com.syber.ssspltd.utils


import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable

fun CustomLoader(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .size(40.dp),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 3.dp
    )
}

