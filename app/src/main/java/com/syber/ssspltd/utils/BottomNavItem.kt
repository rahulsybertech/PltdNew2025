package com.syber.ssspltd.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Gallery : BottomNavItem("gallery", Icons.Default.Phone, "Gallery")
    object More : BottomNavItem("more", Icons.Default.MoreVert, "More")
}
