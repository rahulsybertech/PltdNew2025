package com.syber.ssspltd.ui.view.signup

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.login.LoginResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.login.resetLoginResponse
import com.syber.ssspltd.utils.CustomButton
import com.syber.ssspltd.utils.CustomLoader
import com.syber.ssspltd.utils.CustomOutlinedTextField
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import com.syber.ssspltd.utils.LocationUtils
import com.syber.ssspltd.utils.MyConstant
import com.syber.ssspltd.utils.NetworkUtils
import com.syber.ssspltd.utils.ToolbarUtils
import net.simplifiedcoding.data.network.Resource
import org.json.JSONException
import org.json.JSONObject

@Composable
fun SignUpScreen(navController: NavController, viewModel1: AuthViewModel, themeColors: ThemeColors) {
    val signUpResponse by viewModel1.signUpResponse.observeAsState(Resource.Loading)
    val verifyReferralResponse by viewModel1.verifyReferralResponse.observeAsState(Resource.Loading)
    var honorName by remember { mutableStateOf("") }
    var navigateToMain by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var mobileNumber by remember { mutableStateOf("") }
    var gstNumber by remember { mutableStateOf("") }
    var refferalCode by remember { mutableStateOf("") }
    var isLoggingIn by remember { mutableStateOf(false) }
    var isReff by remember { mutableStateOf(false) }
    var isTextVisible by remember { mutableStateOf(false) }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var isLoader by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val locationUtils = remember { LocationUtils(context) }

    var honorNameError by remember { mutableStateOf<String?>(null) }
    var mobileNumberError by remember { mutableStateOf<String?>(null) }
    var gstNumberError by remember { mutableStateOf<String?>(null) }
    var refError by remember { mutableStateOf<String?>(null) }
    var referralCodeError by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                locationUtils.getCurrentLocation() { lat, lon ->
                    latitude = lat
                    longitude = lon
                }
            }
        }
    )

    LaunchedEffect(true) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToolbarUtils.CustomToolbar(
            title = "Sign Up",
            onBackClick = { navController.popBackStack() },
            themeColors
        )

        Column(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ssslogopng),
                contentDescription = "Company Logo",
                modifier = Modifier
                    .height(110.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomOutlinedTextField(
                value = honorName,
                onValueChange = {
                    honorName = it
                    if (it.isNotBlank()) honorNameError = null
                },
                label = "Honor Name",
                isError = honorNameError != null,

                errorText = honorNameError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = mobileNumber,

                onValueChange = {
                    mobileNumber = it
                    if (mobileNumber.length <= 10 && mobileNumber.all { it.isDigit() }) {
                        mobileNumber = mobileNumber
                        if (mobileNumberError != null) {
                            mobileNumberError = null
                        }
                    }
                  //  if (it.isNotBlank()) mobileNumberError = null
                },
                label = "Mobile Number",
                isError = mobileNumberError != null,
                errorText = mobileNumberError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = gstNumber,
                onValueChange = {
                    gstNumber = it
                    if (it.isNotBlank()) gstNumberError = null
                },
                label = "Gst Number",
                isError = gstNumberError != null,
                errorText = gstNumberError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = refferalCode,
                onValueChange = {
                    refferalCode = it
                    if (it.isNotBlank()) refError = null
                    isReff = false
                },
                label = "Referral Code",
                isError = refError != null,
                errorText = refError
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Verify Referral",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFF1E88E5),
                modifier = Modifier
                    .clickable {
                        isReff = true
                        val isValid = validateInputs(
                            ref = refferalCode,
                            onRefCode = { refError = it }
                        )
                        if (isReff && isValid) {
                            val jsonObject = JsonObject().apply {
                                addProperty("referralCode", refferalCode)
                            }
                            if (!NetworkUtils.isNetworkAvailable(context)) {
                                Toast
                                    .makeText(context, "No internet connection", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                isLoggingIn = true
                                viewModel1.verifyReferralResponseResponse(jsonObject,refferalCode)
                            }
                        }
                    }
                    .padding(start = 10.dp)
            )
         /*   if (isLoggingIn) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomLoader()
                }
            }*/

            Spacer(modifier = Modifier.height(24.dp))

            if (isTextVisible) {
                Text(
                    text = "Create Account",
                    fontSize = 13.sp,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = themeColors.primary
                    )
                )
            }
            CustomButton(
                text = "Submit",
                onClick = {
                    var valid = true

                    if (honorName.isEmpty()) { honorNameError = "Honor Name is required"; valid = false }
                    if (mobileNumber.isEmpty()) { mobileNumberError = "Mobile number must be at least 10 digits"; valid = false }
                    if (gstNumber.isEmpty()) { gstNumberError = "Gst Number is required"; valid = false }
                    if (refferalCode.isEmpty()) { referralCodeError = "Referral Code is required"; valid = false }

                    if (valid) {

                        val jsonObject = JsonObject().apply {
                            addProperty("MOBILENO", mobileNumber)
                            addProperty("REFERRAL", refferalCode)
                            addProperty("GSTNO", gstNumber)
                            addProperty("Name", honorName)
                            addProperty("Address", "xyz")
                            addProperty("Lattitude", latitude.toString())
                            addProperty("Longitude", longitude.toString())
                            addProperty("FirmName", honorName)
                        }

                        if (!NetworkUtils.isNetworkAvailable(context)) {
                            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                        } else {
                            isLoggingIn = true
                            MyConstant.THEMECOLUR = "ffff"
                            viewModel1.signUpResponse(jsonObject)
                            Log.e("jsonObject",jsonObject.toString())
                         //   resetLoginResponse(viewModel1)
                        }

                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),themeColors
            )
            // Loader Section
            if (isLoggingIn) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White, shape = RoundedCornerShape(4.dp))
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )
                }
            }
            when (val response = signUpResponse) {
                is Resource.Success -> {
                    isLoggingIn = false
                    try {
                        val jsonString = Gson().toJson(response.value)
                        val jsonObject = JSONObject(jsonString)

                        if (jsonObject.optBoolean("success")) {
                            navigateToMain = true
                        } else {
                            errorMessage = jsonObject.optString("message", "Something went wrong")
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        errorMessage = "Invalid response format"
                    }
                }

                is Resource.Failure -> {
                    isLoggingIn = false
                    errorMessage = response.errorBody
                }

                Resource.Loading -> {
                    // Optional: Show loading state
                }
            }

            val context = LocalContext.current

            LaunchedEffect(errorMessage) {
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    errorMessage = null // reset
                }
            }

            LaunchedEffect(navigateToMain) {
                if (navigateToMain) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Already have an account?", fontSize = 15.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Login",
                    fontSize = 15.sp,
                    fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }



            when (val response = verifyReferralResponse) {
                is Resource.Success<*> -> {
                    viewModel1._verifyReferralResponse.value = Resource.Loading
                    isLoggingIn=false
                    var jsonObject = JSONObject(response.value.toString())
                    if(jsonObject.optBoolean("success")){
                        Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show()
                    }



                }
                is Resource.Failure -> {
                    isLoggingIn = false
                    resetLoginResponse(viewModel1)
                    viewModel1._verifyReferralResponse.value = Resource.Loading
                    Toast.makeText(context, response.errorBody, Toast.LENGTH_SHORT).show()
                }
                Resource.Loading -> {}
            }





        }

    }


}




@Composable
fun ReferralCodeWithButton(
    referralCode: String,
    onReferralChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            value = referralCode,
            onValueChange = onReferralChange,
            label = { Text("Referral Code") },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            shape = RoundedCornerShape(10.dp)
        )
        Button(
            onClick = onButtonClick,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(56.dp)
                .padding(start = 10.dp)
        ) {
            Text("Check Code")
        }
    }
}


fun validateInputs(
    ref: String,
    onRefCode: (String?) -> Unit
): Boolean {
    var isValid = true

    /* if (email.isBlank()) {
         onEmailError("Email is required")
         isValid = false
     } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
         onEmailError("Invalid email address")
         isValid = false
     }

     if (password.isBlank()) {
         onPasswordError("Password is required")
         isValid = false
     } else if (password.length < 6) {
         onPasswordError("Password must be at least 6 characters")
         isValid = false
     }*/
    if (ref.isBlank()) {
        onRefCode("Ref Code is required")
        isValid = false
    }


    return isValid
}

