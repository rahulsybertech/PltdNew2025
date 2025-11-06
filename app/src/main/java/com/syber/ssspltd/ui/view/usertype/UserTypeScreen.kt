package com.syber.ssspltd.ui.view.usertype
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.userType.ApiResponse
import com.syber.ssspltd.data.model.userType.UserType
import com.syber.ssspltd.data.model.userType.UserTypeItem
import com.syber.ssspltd.data.model.userType.UserTypeResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.login.BottomRightCurvedGradientWithImage
import com.syber.ssspltd.utils.AppSharedPreferences
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import net.simplifiedcoding.data.network.Resource
import kotlin.math.log

@Composable
fun UserTypeScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {
    val context = LocalContext.current
    val activity = (context as? Activity)
    val userTypeResponse by viewModel1.userTypeResponse.observeAsState(Resource.Loading)
    var selectedType by remember { mutableStateOf<String?>(null) }
    var userTypes by remember { mutableStateOf<List<UserTypeItem>>(emptyList()) }

    // Handle back press
    BackHandler {
        activity?.finish()
    }

    // Fetch user types on composition
    LaunchedEffect(userTypeResponse) {
        if (userTypeResponse is Resource.Loading) {
            val jsonObject = JsonObject().apply {
                addProperty("MOBILENO", AppSharedPreferences.getInstance(context).phoneNumber)
            }
            viewModel1.userType(jsonObject)
        }
    }

    // Handle API response
    when (val response = userTypeResponse) {
        is Resource.Success<*> -> {
            val jsonResponse: UserTypeResponse =
                Gson().fromJson(response.value.toString(), UserTypeResponse::class.java)
            userTypes = jsonResponse.data.usersTypeListResult

          /*  jsonResponse.apply {
                AppSharedPreferences.getInstance(context).saveUserType(userTypes)
            }*/
        }

        is Resource.Failure -> {
            Toast.makeText(context, response.errorBody, Toast.LENGTH_SHORT).show()
        }

        Resource.Loading -> Unit
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Fixed Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sss_icon),
                    contentDescription = "Calendar",
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Select User Type",
                    fontSize = 20.sp,
                    fontFamily = poppinsFontFamily1,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Scrollable List Only
            LazyColumn(
                modifier = Modifier
                    .weight(1f).padding(20.dp)
                    .fillMaxWidth().background(Color.White),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(userTypes) { type ->
                    val isSelected = type.Name == selectedType
                    val backgroundColor =
                        if (isSelected) Color(0xFF1976D2) else Color(0xFFF5F5F5)
                    val contentColor = if (isSelected) Color.White else Color.Black

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedType = type.Name
                                AppSharedPreferences.getInstance(context).apply {
                                    savePartyCode(type.PartyCode)
                                    savePartyId(type.ID)
                                    saveUserType("true")
                                    saveGroupCode(type.GroupCode)
                                    saveUserId(type.ID)
                                    saveAnyChosen(true, context)
                                }

                                navController.navigate(Screen.MainActivity.route)
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = contentColor
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = type.Name,
                                fontSize = 12.sp,
                                color = contentColor
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = Color.White
                                )

                            }
                        }
                    }
                }
            }
        }




        // Bottom Decoration
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
                BottomRightCurvedGradientWithImage(themeColors)
            }
        }

        // Loading Overlay
        if (userTypeResponse is Resource.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}



fun saveAnyChosen(b: Boolean, context: Context) {
  //  AppSharedPreferences.getInstance(context).anyChoosen="true";
}


