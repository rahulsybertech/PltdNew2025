package com.syber.ssspltd.ui.view.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.login.CheckMobileResponse
import com.syber.ssspltd.data.model.login.CheckOtpResponse
import com.syber.ssspltd.data.model.login.LoginResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.AppSharedPreferences
import com.syber.ssspltd.utils.CustomButton
import com.syber.ssspltd.utils.CustomDialog.CustomLoginDialog
import com.syber.ssspltd.utils.CustomDialog.OtpResendDialog
import com.syber.ssspltd.utils.CustomLoader
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import com.syber.ssspltd.utils.MyConstant
import com.syber.ssspltd.utils.NetworkUtils
import com.syber.ssspltd.utils.OtpBottomSheet
import com.syber.ssspltd.utils.ToolbarUtils

import net.simplifiedcoding.data.network.Resource


@Composable
fun LoginScreen(navController: NavController, viewModel1: AuthViewModel, themeColors: ThemeColors) {
    val context = LocalContext.current
    val activity = (context as? Activity)
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val prefs = remember { AppSharedPreferences(context) }

    var showResendDialog by remember { mutableStateOf(false) }
    val loginResponse by viewModel1.loginResponse.observeAsState(Resource.Loading)
    val checkOtpResponse by viewModel1.checkOtpResponse.observeAsState(Resource.Loading)
    val resendOtpResponse by viewModel1.resendOtpResponse.observeAsState(Resource.Loading)
    var showOtpSheet by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var showUserNotRegisDialog by remember { mutableStateOf(false) }
    
    var isLoggingIn by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var mobileNumberError by remember { mutableStateOf<String?>(null) }

    MyConstant.THEMECOLUR = "fff"

    val poppinsFontFamily = FontFamily(
        Font(R.font.poppins_thin, FontWeight.Thin), // Example using a regular weight
        /*    Font(R.font.poppins_bold, FontWeight.Bold) // Example using a bold weight*/
    )


    val typography = Typography(
        h1 = TextStyle(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold, fontSize = 24.sp),
        body1 = TextStyle(fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium, fontSize = 16.sp)
    )
    BackHandler {
        activity?.finish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ===============================
        // Main Login UI Column
        // ===============================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            ToolbarUtils.CustomToolbar(
                title = "Login",
                onBackClick = { activity!!.finish() },
                themeColors
            )
            Spacer(modifier = Modifier.height(60.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.ssslogopng),
                contentDescription = "Company Logo",
                modifier = Modifier
                    .height(110.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(60.dp))

            TextField(
                value = mobileNumber,
                onValueChange = { input ->
                    if (input.length <= 10 && input.all { it.isDigit() }) {
                        mobileNumber = input
                        if (mobileNumberError != null) {
                            mobileNumberError = null
                        }
                    }
                },
                placeholder = { Text("Enter your Mobile Number", fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Normal) },
                isError = mobileNumberError != null,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone Icon")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFF5F5F5), // Light gray background
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(horizontal = 16.dp)
            )

            if (mobileNumberError != null) {
                Text(
                    text = mobileNumberError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 24.dp, top = 4.dp)
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(
                text = "Login",
                onClick = {
                    val isValid = validateInputs(
                        email = email,
                        password = password,
                        mobileNumber = mobileNumber,
                        onEmailError = { emailError = it },
                        onPasswordError = { passwordError = it },
                        onMobileNumberError = { mobileNumberError = it }
                    )
                    if (isValid) {
                        val jsonObject = JsonObject().apply {
                            addProperty("mobileno", mobileNumber)
                        }

                        val isConnected = NetworkUtils.isNetworkAvailable(context)
                        if (!isConnected) {
                            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                        } else {
                            isLoggingIn = true
                            viewModel1.login(jsonObject)
                            resetLoginResponse(viewModel1)
                        }
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                themeColors
            )

            when (val response = loginResponse) {
                is Resource.Success<*> -> {
                    isLoggingIn=false
                    Log.d("LoginResponse",response.toString())
                    val jsonResponse : CheckMobileResponse = Gson().fromJson(response.value.toString(), CheckMobileResponse::class.java)
                    //    prefs.saveToken(jsonResponse.accessToken!!)
                    if (jsonResponse.success) {
                        if (jsonResponse.data.userStatus.equals("REGUSER", ignoreCase = true)) {
                            Toast.makeText(context, "OTP sent on mobile number", Toast.LENGTH_SHORT).show()
                            showOtpSheet = true //
                            //    showOtpSheet = false //
                            resetLoginResponse(viewModel1)

                        }
                        else if (jsonResponse.data.userStatus.equals("NEWUSER", ignoreCase = true)){
                        }
                        else{
                            showOtpSheet = false
                            showUserNotRegisDialog=false
                            showOtpSheet = false
                            resetLoginResponse(viewModel1)
                            navController.navigate(Screen.SignUp.route)
                        }
                    }else{
                        resetLoginResponse(viewModel1)
                        Toast.makeText(LocalContext.current, " This mobile number is not registered", Toast.LENGTH_SHORT).show()
                    }


                }
                is Resource.Failure -> {
                    isLoggingIn = false
                    //    navController.navigate(Screen.Home.route)
                    Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
                }

                Resource.Loading ->{

                }

            }

            // Observe loginResponse here...
            // Your existing response handling code

            Spacer(modifier = Modifier.height(10
                .dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Don't have an account?", fontSize = 15.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Sign up",
                    fontSize = 15.sp,
                    fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.SignUp.route)
                    }
                )
            }



        OtpBottomSheet(
            showSheet = showOtpSheet,
            onDismiss = { showOtpSheet = false },
            onOtpSubmit = { otp ->


                val jsonObject = JsonObject().apply {
                    addProperty("mobileno", mobileNumber)
                    addProperty("otptype", "dd")
                    addProperty("otp", otp)
                }

                val isConnected = NetworkUtils.isNetworkAvailable(context)
                if (!isConnected) {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                }else{
                    isLoggingIn = true

                    viewModel1.checkOtp(jsonObject)
                    resetLoginResponse(viewModel1)
                }

                Log.d("OTP", "Submitted OTP: $otp")
                showOtpSheet = false
                keyboardController?.show()
            },
            onResendOtp = { otp ->
                showResendDialog = true
               /* if(showResendDialog){
                    showResendDialog = true
                }*/


              //  showOtpSheet = false
             //   keyboardController?.show()
            },
            themeColors
        )
        //Response Api
        when (val response = checkOtpResponse) {
            is Resource.Success<*> -> {
                isLoggingIn=false
                val jsonResponse : CheckOtpResponse = Gson().fromJson(response.value.toString(), CheckOtpResponse::class.java)

                if(jsonResponse.success){
                    AppSharedPreferences.getInstance(context).apply {
                        mobileNumber=mobileNumber
                        saveToken(jsonResponse.data.accessToken)
                        jsonResponse.data.userStatus?.let { saveUserStatus(it) }
                        savePhoneNumber(mobileNumber)
                        isLogin("true")
                        startDate=jsonResponse.data.startDate.toString()
                        endDate=jsonResponse.data.endDate.toString()

                    }

                    navController.navigate(Screen.UserType.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }


                }else{

                }
               resetLoginResponse(viewModel1)

            }
            is Resource.Failure -> {
                resetLoginResponse(viewModel1)
                //    navController.navigate(Screen.Home.route)
                Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
            }

            Resource.Loading ->{

            }

        }


        if (showResendDialog) {
            OtpResendDialog(
                onTextOtpClick = {
                    Log.d("OTP", "Text OTP Clicked")
                    showResendDialog = false
                    val jsonObject = JsonObject().apply {
                        addProperty("MOBILENO", mobileNumber)
                        addProperty("OTPTYPE", "")
                    }

                    val isConnected = NetworkUtils.isNetworkAvailable(context)
                    if (!isConnected) {
                        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                    }else{
                        isLoggingIn = true

                        viewModel1.resendOtp(jsonObject)
                        resetLoginResponse(viewModel1)
                    }
            //        Log.d("OTP", "Resend OTP: $otp")

                },
                onWhatsappOtpClick = {
                    Log.d("OTP", "WhatsApp OTP Clicked")

                    showResendDialog = false
                    val jsonObject = JsonObject().apply {
                        addProperty("MOBILENO", mobileNumber)
                        addProperty("OTPTYPE", "WHATSAPP")
                    }

                    val isConnected = NetworkUtils.isNetworkAvailable(context)
                    if (!isConnected) {
                        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                    }else{
                        isLoggingIn = true

                        viewModel1.resendOtp(jsonObject)
                        resetLoginResponse(viewModel1)
                    }
                },
                onDismiss = { showResendDialog = false }
            )
        }

        when (val response = resendOtpResponse) {
            is Resource.Success<*> -> {
                isLoggingIn=false
                val jsonResponse : LoginResponse = Gson().fromJson(response.value.toString(), LoginResponse::class.java)
                //    prefs.saveToken(jsonResponse.accessToken!!)
                if (jsonResponse.responseStatus) {
                    showOtpSheet = false //
                    //    showOtpSheet = false //
                    Toast.makeText(context, "New OTP sent on mobile number", Toast.LENGTH_SHORT).show()
                    resetResetResponse(viewModel1)

                }else{
                    showOtpSheet = false
                    showUserNotRegisDialog=false
                    resetResetResponse(viewModel1)
                    navController.navigate(Screen.SignUp.route)
                }

            }
            is Resource.Failure -> {
                resetResetResponse(viewModel1)
                isLoggingIn = false
                //    navController.navigate(Screen.Home.route)
                Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
            }

            Resource.Loading ->{

            }

        }

        CustomLoginDialog(showDialog = showUserNotRegisDialog, onDismiss = { showUserNotRegisDialog = false })

            if (isLoggingIn) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomLoader()
                }
            }
        }

        // ===============================
        // Bottom Image + Gradient Overlay
        // ===============================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // ... your Column (login form)

                BottomRightCurvedGradientWithImage(themeColors) // This is your new bottom decoration
            }
            /*Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xFFFBEAEA))
                        )
                    )
            )

            Image(
                painter = painterResource(id = R.drawable.image_one), // Your bottom image
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight()
                    .wrapContentWidth()
            )*/
        }
    }
}




fun resetLoginResponse(viewModel1: AuthViewModel) {
    viewModel1._loginResponse.value = Resource.Loading
}
fun resetResetResponse(viewModel1: AuthViewModel) {
    viewModel1._resendOtpResponse.value = Resource.Loading
}

fun validateInputs(
    email: String,
    password: String,
    mobileNumber: String,
    onEmailError: (String?) -> Unit,
    onPasswordError: (String?) -> Unit,
    onMobileNumberError: (String?) -> Unit
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
    if (mobileNumber.isBlank()) {
        onMobileNumberError("Mobile Number is required")
        isValid = false
    }else if (mobileNumber.length < 9) {
        onMobileNumberError("Mobile number must be at least 10 digits")
        isValid = false
    }


    return isValid
}

@Composable
fun LoginScreenContent(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    themeColors: ThemeColors
) {
    Column(
        modifier = Modifier.padding(10.dp, top = 50.dp, end = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.ssslogopng),
            contentDescription = "Company Logo",
            modifier = Modifier
                .height(200.dp)
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null
        )
        emailError?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null
        )
        passwordError?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        CustomButton(
            text = "Login",
            onClick = onLoginClick,
            modifier = Modifier.width(200.dp), themeColors
        )
    }
}

/*@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    var email by remember { mutableStateOf("test@example.com") }
    var password by remember { mutableStateOf("password123") }

    LoginScreenContent(
        email = email,
        password = password,
        emailError = null,
        passwordError = null,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLoginClick = { *//* No-op for preview *//* },
        themeColors = the
    )
}*/

@Composable
fun BottomRightCurvedGradientWithImage(themeColors: ThemeColors) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        // Draw curved background
        Canvas(modifier = Modifier.matchParentSize()) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                moveTo(0f, height * 0.95f) // Start low on left
                quadraticBezierTo(
                    width * 0.6f, height * 0.6f, // Curve upward
                    width, height * 0.6f         // End mid-right
                )
                lineTo(width, height)           // Bottom-right
                lineTo(0f, height)              // Bottom-left
                close()
            }

            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFF8A80), Color(0xFFFFEBEE)),
                    start = Offset.Zero,
                    end = Offset(0f, height)
                )
            )
        }

        // Add mannequin image
        Image(
            painter = painterResource(id = R.drawable.image_one),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(180.dp).padding(end = 10.dp)
        )
    }
}











