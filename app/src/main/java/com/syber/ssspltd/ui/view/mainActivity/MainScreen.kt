package com.syber.ssspltd.ui.view.mainActivity

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*


import androidx.compose.material3.*

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.AppThemeType
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.theme.ThemeManager
import com.syber.ssspltd.ui.view.galleryscreen.ModernGalleryScreen
import com.syber.ssspltd.ui.view.home.HomeScreen
import com.syber.ssspltd.ui.view.morescreen.MoreScreen
import com.syber.ssspltd.ui.view.usertype.UserTypeScreen

@Composable

fun MainScreen(navController: NavController, viewModel1: AuthViewModel,themeColors: ThemeColors) {
    val bottomNavController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Gallery,
        BottomNavItem.More
    )

    Scaffold(
        bottomBar = {
            BeautifulBottomBar(bottomNavController, items)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                val viewModel: AuthViewModel = hiltViewModel()
              //  val navController = rememberNavController()
           //     UserTypeScreen(navController = navController, viewModel1 = viewModel)
                HomeScreen(navController,viewModel1, themeColors = themeColors)
             //   HomeScreen(themeColors = themeColors) // ✅ Pass themeColors here
            }
            composable(BottomNavItem.Gallery.route) {
                ModernGalleryScreen(navController/*, viewModel1 = viewModel,themeColors*/)
            //    GalleryScreen()
            }
            composable(BottomNavItem.More.route) {
                val viewModel: AuthViewModel = hiltViewModel()
                val themeColors = ThemeManager.getThemeColors(AppThemeType.DEFAULT)
                MoreScreen(navController/*, viewModel1 = viewModel,themeColors*/)

            }
        }
    }
}

