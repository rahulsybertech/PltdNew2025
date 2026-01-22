package com.syber.ssspltd

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
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
    // Mocking dependencies
    private val mockNavController = mockk<NavController>(relaxed = true)
    private val mockViewModel = mockk<AuthViewModel>(relaxed = true)
    private val mockThemeColors = ThemeColors(
        primary = Color(0xFF008080), // Use your project's primary color hex
        background = Color(0xFFFFFFFF), // White or your default background
        festiveImageRes = com.syber.ssspltd.R.drawable.ssslogopng // Ensure this path matches your actual drawable name
    )


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
            festiveImageRes = com.syber.ssspltd.R.drawable.ssslogopng
        )
        val mockLiveData = MutableLiveData<Resource<JsonObject>>()
        every { viewModel.loginResponse } returns mockLiveData

        composeTestRule.setContent {
            LoginScreen(navController, viewModel, themeColors = themeColors)
        }

        // ✅ Use the testTag instead of onNodeWithText to avoid the AssertionError
        composeTestRule.onNodeWithTag("mobile_input_field").performTextInput("9876543210")

        composeTestRule.onAllNodes(hasText("Login") and hasClickAction())
            .onFirst()
            .performClick()

        verify { viewModel.login(any()) }
    }


    @Test
    fun loginScreen_InputMobileNumber_UpdatesValue() {
        startLoginScreen()

        val mobileInput = "9876543210"

        // ✅ Use onNodeWithTag to find the field
        composeTestRule.onNodeWithTag("mobile_input_field")
            .performTextInput(mobileInput)

        // Verify the text was entered
        composeTestRule.onNodeWithText(mobileInput).assertIsDisplayed()
    }
    private fun startLoginScreen() {
        composeTestRule.setContent {
            LoginScreen(
                navController = mockNavController,
                viewModel1 = mockViewModel,
                themeColors = mockThemeColors
            )
        }
    }
}
