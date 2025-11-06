package com.syber.ssspltd.ui.view.splash

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.AppConfigResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.dataStore
import com.syber.ssspltd.ui.view.login.resetLoginResponse
import com.syber.ssspltd.utils.AppConfigPrefs
import com.syber.ssspltd.utils.AppSharedPreferences
import com.syber.ssspltd.utils.NetworkUtils
import kotlinx.coroutines.delay
import net.simplifiedcoding.data.network.Resource


@RequiresApi(Build.VERSION_CODES.P)
@Composable

fun SplashScreen(navController: NavController, viewModel1: AuthViewModel) {
    val appVersionResponse by viewModel1.appVersionResponse.observeAsState(Resource.Loading)
    val context = LocalContext.current

    // Zoom animation: scale state
    var scale by remember { mutableStateOf(1f) }
    val appThemeDetailsResponse by viewModel1.appThemeDetailsResponse.observeAsState(Resource.Loading)

    // Animate scale from 1f to 1.2f (zoom-in) and back to 1f (zoom-out)
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
        label = "ZoomAnimation"
    )

    /////Api App Theme
// Trigger API only once when Composable is first composed
    LaunchedEffect(Unit) {
        if (appThemeDetailsResponse is Resource.Loading) {
            val jsonObject = JsonObject().apply {
                addProperty("MOBILENO", "7290087642")
            }
            viewModel1.appThemeDetailsReq(jsonObject)
        }
    }

    when (val response = appThemeDetailsResponse) {
        is Resource.Success<*> -> {
            Log.d("responseThem", response.value.toString())
            resetApthemeResponse(viewModel1)

            val jsonResponse: AppConfigResponse = Gson().fromJson(response.value.toString(), AppConfigResponse::class.java)
            val config = jsonResponse.data.firstOrNull()

            // This block must be called inside a coroutine
            config?.let { safeConfig ->
                LaunchedEffect(key1 = safeConfig) {
                    context.dataStore.edit { prefs ->
                        prefs[AppConfigPrefs.appThemeColor] = safeConfig.appThemeColor ?: "#FFFFFF"
                        prefs[AppConfigPrefs.appThemeColor] = safeConfig.appThemeColor ?: "#FFFFFF"
                        prefs[AppConfigPrefs.appVersion] = safeConfig.appVerson ?: "1.0"
                        prefs[AppConfigPrefs.appLogo] = safeConfig.appLogo ?: ""
                    }
                }
            }
        }

        is Resource.Failure -> {
            resetApthemeResponse(viewModel1)
            Toast.makeText(context, response.errorBody ?: "Unknown error", Toast.LENGTH_SHORT).show()
        }

        Resource.Loading -> {
            // Optional loading UI
        }
    }



    LaunchedEffect(Unit) {
        scale = 1.2f // Start zoom-in

        delay(1500L)
        scale = 1f // Then zoom-out

        delay(1500L)
        if (AppSharedPreferences.getInstance(context).isPartyCode.equals("")) {
            appVersionCheck(appVersionResponse,context, viewModel1)
        } else {
            appVersionCheck(appVersionResponse, context, viewModel1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Full screen background image
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Splash Logo",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = animatedScale,
                    scaleY = animatedScale
                )
        )

        // Content inside the Box
   /*     Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome!", fontSize = 20.sp, color = Color.White)
            AppVersionInfo()
        }*/
    }


    when (val response = appVersionResponse) {
        is Resource.Success<*> -> {
            Log.d("appVersionResponse",appVersionResponse.toString())
            goToNext(context, navController)
            resetLoginResponse(viewModel1)
        }
        is Resource.Failure -> {
            resetLoginResponse(viewModel1)
            Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
        }
        Resource.Loading -> { /* You can show a progress indicator here if needed */ }
    }
}

fun resetApthemeResponse(viewModel1: AuthViewModel) {
    viewModel1._appThemeDetailsResponse.value = Resource.Loading
}


@Composable
fun ShowUpdateDialog(
    context: Context,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Prevent dismiss by clicking outside */ },
        title = { Text(text = "Update Required") },
        text = { Text("A new version of the app is available. Please update to continue.") },
        confirmButton = {
            TextButton(
                onClick = {
                    val appPackageName = context.packageName
                    try {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")
                            ).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                        )

                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                            ).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                        )
                    }
                    onDismiss()
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {} // Hides the skip button
    )
}


@RequiresApi(Build.VERSION_CODES.P)
private fun appVersionCheck(
    appVersionResponse: Resource<JsonObject>,
    context: Context,
    viewModel1: AuthViewModel,

    ) {
    val isConnected = NetworkUtils.isNetworkAvailable(context)
    if (!isConnected) {
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
    } else {
        if (appVersionResponse is Resource.Loading) {
            val jsonObject = JsonObject().apply {
                addProperty("datakey", "SSSPLTD")
                addProperty("device", "Android")
            }
            viewModel1.appVersion(jsonObject)
            resetLoginResponse(viewModel1)
        }

    }
}


fun resetAppVersionResponse(viewModel1: AuthViewModel) {
    viewModel1._appVersionResponse.value = Resource.Loading
}
private fun goToNext(context: Context, navController: NavController) {
    // Access the shared preferences once using 'apply'
    val prefs = AppSharedPreferences.getInstance(context)

    // Using 'apply' to retrieve all shared preferences values in a cleaner way
    prefs.apply {
        val userMobile = token
        var isLogin = false // This will be true or false

        if (login.equals("true")) {
            isLogin=true
        } else {
            isLogin=false
            // Handle when isLogin is false (i.e., "false")
        }

        val isAnyChosen: String
        if (AppSharedPreferences.getInstance(context).userType.equals("true")) {
            isAnyChosen="true"
        } else {
            isAnyChosen="false"
            // Handle when isLogin is false (i.e., "false")
        }

        val isSuperSelected = isSupperSelected
        val whereToGo = isWeretogo

        // Log for debugging purposes
        Log.i("TAG", "goToNext --> $userMobile == $isLogin == $isAnyChosen == $isSuperSelected == $whereToGo")

        // Navigate based on conditions
        when {
            userMobile!!.isNotEmpty() && isLogin && isAnyChosen == "true" -> {
                navController.navigate(Screen.MainActivity.route)
            }
            userMobile.isNotEmpty() && isLogin && isAnyChosen == "false" -> {
                navController.navigate(Screen.UserType.route)
            }
            userMobile.isNotEmpty() && isLogin && isSuperSelected == "true" && isAnyChosen == "true" -> {
                navController.navigate(Screen.MainActivity.route)
            }
            userMobile.isNotEmpty() && isLogin && isSuperSelected == "true" && isAnyChosen!!.isEmpty() -> {
                navController.navigate(Screen.MainActivity.route)
            }
            userMobile.isNotEmpty() && isLogin && isSuperSelected == "false" && isAnyChosen!!.isEmpty() -> {
                navController.navigate(Screen.MainActivity.route)
            }
            isLogin && isAnyChosen.isEmpty() && whereToGo == "reg_msg" -> {
                navController.navigate(Screen.SignUp.route)
            }
            userMobile.isNotEmpty() && isLogin && isAnyChosen!!.isEmpty() && whereToGo == "main_act" -> {
                navController.navigate(Screen.Login.route)
            }
            userMobile.isNotEmpty() && isLogin && isAnyChosen!!.isEmpty() && whereToGo == "choose_cat" -> {
                navController.navigate(Screen.UserType.route)
            }
            else -> {
                navController.navigate(Screen.Login.route)
            }
        }
    }
}





