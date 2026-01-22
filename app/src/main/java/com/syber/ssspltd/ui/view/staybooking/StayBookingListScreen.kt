package com.syber.ssspltd.ui.view.staybooking

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.data.model.login.CheckMobileResponse
import com.syber.ssspltd.data.model.staybooking.StayBookingResult
import com.syber.ssspltd.data.model.userType.ApiResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.login.resetLoginResponse
import com.syber.ssspltd.utils.AppSharedPreferences
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import net.simplifiedcoding.data.network.Resource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun StayBookingListScreen( navController: NavController,
                viewModel1: AuthViewModel,
                themeColors: ThemeColors) {
    val listState = rememberLazyListState()
    val bookings by viewModel1.stayBooking.collectAsState()


    val isLoading by viewModel1.loading.collectAsState()
    val updateTime by viewModel1.updateTime.collectAsState()
    val context = LocalContext.current  // ✅ Get the context
    var  userType=     AppSharedPreferences.getInstance(context).groupCode
    // Call API when screen first opens
    LaunchedEffect(Unit) {

        if (userType == "Others") {
            AppSharedPreferences.getInstance(context).isPartyCode?.let { viewModel1.fatchStayBookingListByBranchReq(it) }
        } else {
            AppSharedPreferences.getInstance(context).isPartyCode?.let { viewModel1.fetchStayBooking(it) }
        }
    }
    BookingListScreen(navController,bookings,userType,viewModel1,isLoading,context,updateTime)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingListScreen(
    navController: NavController,
    bookings: List<StayBookingResult>,
    userType: String?,
    viewModel1: AuthViewModel,
    isLoading: Boolean,
    context: Context,
    updateTime: ApiResponse?,
) {
    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 8.dp,
                color = Color(0xFF008080)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "Booking Request",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = {
                                viewModel1.selectedBooking = null
                                navController.navigate(Screen.BookingRequestScreen.route)
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Add Booking",
                                    color = Color.White
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    )
                )
            }
        },
      /*  floatingActionButton = {
            FloatingActionButton(onClick = {    //   navController.navigate(Screen.BookingRequestScreen.route)
                navController.navigate(Screen.BookingRequestScreen.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Visit")
            }
        }*/
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(bookings) { booking ->

                BookingCard(booking,userType, viewModel1,isLoading,context,updateTime,navController)
            }
        }
    }
}

@Composable
fun BookingCard(
    booking: StayBookingResult,
    userType: String?,
    viewModel1: AuthViewModel,
    isLoading: Boolean,
    context:Context,
    updateTime: ApiResponse?, navController: NavController,
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFontFamily1,
                        )
                    ) {
                        append("Visit Id : ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily1,
                        )
                    ) {
                        append(booking.bookingID.toString())   // IMPORTANT
                    }
                    if(booking.accountName=="NEW PARTY"){
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Medium,
                                fontFamily = poppinsFontFamily1,
                            )
                        ) {
                            append(booking.firmName.toString())   // IMPORTANT
                        }
                    }else{
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Medium,
                                fontFamily = poppinsFontFamily1,
                            )
                        ) {
                            append(" (${booking.accountName})")
                        // IMPORTANT
                        }
                    }

                },
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily1,
                maxLines = 1
            )

            Spacer(Modifier.height(2.dp))

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFontFamily1,
                        )
                    ) {
                        append("Branch Name : ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily1,
                        )
                    ) {
                        append(booking.branchName.toString())   // IMPORTANT
                    }
                },
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily1
            )
            if (userType == "Others" || booking.noOfPerson!! > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // ✅ LEFT SIDE: Check In / Check Out (ONE COLUMN)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {

                            Text(
                                buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = poppinsFontFamily1
                                        )
                                    ) {
                                        append("Check In : ")
                                    }
                                    append(booking.checkInDate.toString())
                                },
                                fontSize = 12.sp
                            )

                            Text(
                                buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = poppinsFontFamily1
                                        )
                                    ) {
                                        append("Check Out : ")
                                    }
                                    append(booking.checkoutDate.toString())
                                },
                                fontSize = 12.sp
                            )
                        }

                        // ✅ RIGHT SIDE: Button (ONE ROW)
                        val buttonText =
                            when {
                                booking.actualCheckInDate == null -> "Mark Check In"
                                booking.actualCheckoutDate == null -> "Mark Check Out"
                                else -> null
                            }

                        if (buttonText != null) {
                            Button(
                                onClick = {
                                    // API call logic here
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF008080)),
                                shape = RoundedCornerShape(7.dp),
                                modifier = Modifier
                                    .height(30.dp)
                            ) {
                                Text(
                                    buttonText,
                                    fontSize = 11.sp,
                                    fontFamily = poppinsFontFamily1
                                )
                            }
                        } else {
                            Text(
                                "Stay Completed",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

            }


            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    text = "No Of Person : ${booking.noOfPerson}",
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Row {
                    IconButton(
                        onClick = {
                            viewModel1.selectedBooking = booking
                            navController.navigate(Screen.BookingRequestScreen.route)
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }

                    IconButton(
                        onClick = { /*onDeleteClick(booking)*/ }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}


