package com.syber.ssspltd.ui.view.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import kotlinx.coroutines.delay
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.home.AppMenuPermission
import com.syber.ssspltd.data.model.home.MenuPermissionResponse
import com.syber.ssspltd.data.model.home.SecurityCheckResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.view.addorder.AddOrderActivityNew
import com.syber.ssspltd.utils.AppSharedPreferences
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import com.syber.ssspltd.utils.MyConstant

import net.simplifiedcoding.data.network.Resource


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerScreen(
    appMenuPermissionList: List<AppMenuPermission>,
    images: List<String>,
    navController: NavController,
    viewModel1: AuthViewModel,
    context: Context
) {
    val bannerImages = images
    val securityCheckReportResponse by viewModel1.securityCheckReportResponse.observeAsState(
        Resource.Loading
    )
    var showDialog by remember { mutableStateOf(true) }

    var pen_be by remember { mutableStateOf("0") }
    var isSecurity by remember { mutableStateOf(true) }
    var currentBalance by remember { mutableStateOf("0.00") }
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { bannerImages.size })
    val transition = rememberInfiniteTransition()


    if (AppSharedPreferences.getInstance(context).userStatus == "Customer") {
        isSecurity = true
    } else {
        isSecurity = false
    }

    // Auto scroll logic
    LaunchedEffect(bannerImages) {
        if (bannerImages.isNotEmpty()) {
            while (true) {
                delay(2500)
                val nextPage = (pagerState.currentPage + 1) % bannerImages.size
                pagerState.animateScrollToPage(nextPage)
            }
        }

    }


    LaunchedEffect(securityCheckReportResponse) {
        if (securityCheckReportResponse is Resource.Loading) {
            // keyboardController?.hide() // Hide keyboard when API is loading
            val jsonObject = JsonObject().apply {
                addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            }
            viewModel1.securityCheckReportReq(jsonObject)
        }
    }
    HandleSecurityCheck(
        pen_be,
        securityCheckReportResponse = securityCheckReportResponse,
        navController = navController,context
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp)
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                val scale = 1f - kotlin.math.abs(pageOffset) * 0.25f
                var rotationY = pageOffset * 30f

                Image(
                    painter = rememberAsyncImagePainter(images[page]),
                    contentDescription = "Banner",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            rotationY = rotationY

                        }
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxSize()
                )
            }
        }

        item {
            CurrentBalanceCard(context,isSecurity, balance = "10,42,800", securityCheck = "Cleared")
        }
        item {     DynamicGridMenu(
            appMenuPermissionList = appMenuPermissionList,
            navController = navController,context
        )  }
    }
}


@Composable
fun DynamicGridMenu(
    appMenuPermissionList: List<AppMenuPermission>,
    navController: NavController,context: Context
) {
    val itemsPerRow = 2
    val rowCount = (appMenuPermissionList.size + itemsPerRow - 1) / itemsPerRow
    val itemHeight = 120.dp
    val verticalSpacing = 12.dp

    val totalHeight = remember(rowCount) {
        (itemHeight * rowCount) + (verticalSpacing * (rowCount - 1))
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(itemsPerRow),
        modifier = Modifier
            .fillMaxWidth()
            .height(totalHeight),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        userScrollEnabled = false
    ) {
        items(appMenuPermissionList) { item ->
            Column(
                modifier = Modifier.size(120.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp)
                        .clickable {

                            when (item.menuType) {
                                "Sale Report" -> {
                                    navController.navigate(Screen.SaleReport.route)
                                }
                                "DashBoard" -> {
                                    navController.navigate(Screen.DasbordScreen.route)
                                }
                                "Ledger" -> {
                                    navController.navigate(Screen.LegerScreen.route)
                                }
                                "Add Order" -> {
                                    context.startActivity(Intent(context, AddOrderActivityNew::class.java))

                                    //  navController.navigate(Screen.AddOrder.route)
                                }
                                "Pending Order" -> {
                                    navController.navigate(Screen.PendingOrderList.route)
                                }
                                "Stock in office" -> {
                                    navController.navigate(Screen.StockInOrderScreen.route)
                                }
                                "Debit Note to" -> {
                                }
                                "Brands" -> {
                                    navController.navigate(Screen.BrandsScreen.route)
                                }
                                "Honhar Khiladi" -> {
                                    navController.navigate(Screen.HonorListScreen.route)
                                }
                                "Stay Booking" -> {
                                    navController.navigate(Screen.StayBookingListScreen.route)
                                 //   navController.navigate(Screen.BookingRequestScreen.route)
                                }
                            }

                            Log.d("CardClick", "You clicked on the card with item: $item")
                        },

                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(item.iconImage),
                            contentDescription = "Grid Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(45.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.menuType,
                            fontSize = 12.sp,
                            fontFamily = poppinsFontFamily1,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HandleSecurityCheck(pen_bal:String,
    securityCheckReportResponse: Resource<Any>,
    navController: NavController,context: Context
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var lockMsg by remember { mutableStateOf("") }

    when (securityCheckReportResponse) {
        is Resource.Success<*> -> {
            val json = securityCheckReportResponse.value.toString()
            Log.d("BannerList", json)

            val jsonResponse = try {
                Gson().fromJson(json, SecurityCheckResponse::class.java)
            } catch (e: Exception) {
                Log.e("ParseError", "Invalid JSON: ${e.message}")
                null
            }


            jsonResponse?.let { data ->
                if (data.data.responseMessage == "Success") {

                    if (data.data.statusLock) {
                        lockMsg = data.data.lockMsg
                        showDialog = true
                    }
                 var cout=   data.data.securityCheckReportResult.get(0).Count
                    MyConstant.currentBalance=data.data.securityCheckReportResult.get(0).CurrentBalance
                    if (AppSharedPreferences.getInstance(context).groupCode== "Customer") {


                        when (cout) {
                            "0" -> MyConstant.pendingBalance="3"
                            "1" ->  MyConstant.pendingBalance="2"
                            "2" ->  MyConstant.pendingBalance="1"
                            "3" ->  MyConstant.pendingBalance="0"
                        }
                    }

                  //  data.data.securityCheckReportResult.get()


                    // else continue normal flow
                } else {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }

        is Resource.Failure -> {
           // Toast.makeText(context, securityCheckReportResponse.errorBody, Toast.LENGTH_SHORT).show()
        }

        Resource.Loading -> {
            // Optional: Add loading indicator if needed
        }
    }

    if (showDialog) {
        LockStatusDialog(
            message = lockMsg,
            showCross = true,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun CurrentBalanceCard(context: Context,
    isSecurity:Boolean,
    balance: String,
    securityCheck: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    )
    {
        // Current Balance Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.rupee),
                    contentDescription = "Rupee Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )

                Text(
                    text = "Current Balance:",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                )
            }

            Text(
                text = "₹ ${MyConstant.currentBalance} /-",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
        }

        // Security Check Row
        if ( AppSharedPreferences.getInstance(context).groupCode== "Customer"){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.bill), // Replace with actual security icon
                        contentDescription = "Security Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),

                        )

                    Text(
                        text = "Security Check:",
                        style = TextStyle(
                            fontFamily = poppinsFontFamily1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    )
                }

                Text(
                    text = MyConstant.pendingBalance,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                )
            }
        }

    }
}

@Composable
fun LockStatusDialog(
    message: String,
    showCross: Boolean = false,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { /* Do not dismiss on outside */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 40.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Center Image
                Image(
                    painter = painterResource(id = R.drawable.sss_icon),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(width = 60.dp, height = 55.dp)
                        .clip(CircleShape)
                        .shadow(5.dp, shape = CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Message
                Text(
                    text = message,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(15.dp))

                // OK Button
                Text(
                    text = "OK",
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFF4286f4), Color(0xFF373B44)) // Example gradient
                            )
                        )
                        .clickable { onDismiss() }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }

            // Optional Close Icon (top-right)
            if (showCross) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_criss_cross),
                    contentDescription = "Close",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.TopEnd)
                        .clickable { onDismiss() }
                        .padding(4.dp)
                )
            }
        }
    }

}



