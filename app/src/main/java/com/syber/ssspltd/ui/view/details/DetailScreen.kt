package com.syber.ssspltd.ui.view.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(name: String?) {
    Text(
        text = "Welcome to the details of $name",
        modifier = Modifier.padding(24.dp),
        style = MaterialTheme.typography.headlineMedium
    )
}