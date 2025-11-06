package com.syber.ssspltd

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.login.LoginScreen
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.simplifiedcoding.data.network.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: AuthViewModel
    private lateinit var navController: NavController

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)
        navController = mockk(relaxed = true)

        // Mock LiveData behavior
        every { viewModel.loginResponse } returns MutableLiveData(Resource.Loading)
        every { viewModel.checkOtpResponse } returns MutableLiveData(Resource.Loading)
        every { viewModel.resendOtpResponse } returns MutableLiveData(Resource.Loading)
    }

    @Test
    fun loginButton_showsError_whenMobileNumberIsEmpty() {
        val themeColors = ThemeColors(
            primary = Color(0xFF6200EE),
            background = Color(0xFF3700B3),
            festiveImageRes = R.drawable.ssslogopng // replace with your actual drawable
        )

        composeTestRule.setContent {
            LoginScreen(navController, viewModel, themeColors = themeColors)
        }

        // Tap Login
        composeTestRule.onNodeWithText("Login").performClick()

        // Check error
        composeTestRule.onNodeWithText("Mobile number is required") // adjust if your error message differs
            .assertIsDisplayed()
    }

    @Test
    fun loginButton_callsViewModel_whenValidInput() {
        // Provide mocked successful LiveData
        val themeColors = ThemeColors(
            primary = Color(0xFF6200EE),
            background = Color(0xFF3700B3),
            festiveImageRes = R.drawable.ssslogopng // replace with your actual drawable
        )
        val mockLiveData = MutableLiveData<Resource<JsonObject>>()
        every { viewModel.loginResponse } returns mockLiveData
        composeTestRule.setContent {
            LoginScreen(navController, viewModel, themeColors = themeColors)
        }

        // Input valid mobile
        composeTestRule.onNodeWithText("Mobile Number").performTextInput("9876543210")

        // Click login
        composeTestRule.onNodeWithText("Login").performClick()

        verify { viewModel.login(any()) }
    }
}
