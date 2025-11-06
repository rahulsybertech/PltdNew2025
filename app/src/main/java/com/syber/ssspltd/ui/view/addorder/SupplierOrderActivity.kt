package com.syber.ssspltd.ui.view.addorder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.syber.ssspltd.navigation.AppNavGraph
import com.syber.ssspltd.out.AuthViewModel

class SupplierOrderActivity : ComponentActivity() {
    private val viewModell by viewModels<AuthViewModel>()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Set up the composable content
            val navController = rememberNavController()
            AppNavGraph(navController = navController)
            /*  LoginScreen(navController = navController,viewModell)*/
        }
    }
}