package com.syber.ssspltd.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.JsonObject
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.login.validateInputs
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpBottomSheet(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onOtpSubmit: (String) -> Unit,
    onResendOtp:   (String) -> Unit,
    themeColors: ThemeColors
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val otpLength = 4
    val otpDigits = remember { mutableStateListOf("", "", "", "") }
    var otpError by remember { mutableStateOf<String?>(null) }

    val focusRequesters = List(otpLength) { FocusRequester() }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                otpDigits.fill("")  // Clear OTP digits when dismissed
                onDismiss()  // Call the onDismiss function
            },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text("Enter OTP", fontSize = 20.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    otpDigits.forEachIndexed { index, digit ->
                        OutlinedTextField(
                            value = digit,
                            onValueChange = { newValue ->
                                if (newValue.length <= 1 && newValue.all { c -> c.isDigit() }) {
                                    otpDigits[index] = newValue
                                    if (newValue.isNotEmpty() && index < otpLength - 1) {
                                        focusRequesters[index + 1].requestFocus()
                                    } else if (newValue.isEmpty() && index > 0) {
                                        // Move focus to the previous field when backspace is pressed
                                        focusRequesters[index - 1].requestFocus()
                                    }
                                    otpError = null
                                }
                            },
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                                .focusRequester(focusRequesters[index])
                                .clip(RoundedCornerShape(2.dp)),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Didn't received code?",
                        fontSize = 15.sp,
                        fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Normal // or FontWeight.Medium, etc.
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Resend",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold, // Bold style
                        fontStyle = FontStyle.Italic, // Optional italic
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            onResendOtp("")
                         /*   navController.popBackStack()*/
                        }
                    )
                }


                if (otpError != null) {
                    Text(
                        text = otpError!!,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    text = "Verify",
                    onClick = {
                        val otp = otpDigits.joinToString("")
                        if (otp.length == otpLength) {
                            onOtpSubmit(otp)
                            scope.launch { bottomSheetState.hide() }
                                .invokeOnCompletion { if (!bottomSheetState.isVisible) onDismiss() }
                        } else {
                            otpError = "Please enter 4 digits"
                        }
                    },
                    modifier = Modifier.width(150.dp).align(Alignment.CenterHorizontally),
                    themeColors
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}


