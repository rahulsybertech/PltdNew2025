package com.syber.ssspltd.ui.view.home

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import com.google.gson.JsonObject

import com.syber.ssspltd.data.model.home.AppMenuPermission
import com.syber.ssspltd.data.model.home.BannerResponse
import com.syber.ssspltd.data.model.home.FinancialYearItem
import com.syber.ssspltd.data.model.home.FinancialYearResponse
import com.syber.ssspltd.data.model.home.MenuPermissionResponse
import com.syber.ssspltd.data.model.userType.UserTypeItem
import com.syber.ssspltd.data.model.userType.UserTypeResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.usertype.saveAnyChosen
import com.syber.ssspltd.utils.AppSharedPreferences
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import com.syber.ssspltd.utils.ToolbarUtils
import com.syber.ssspltd.utils.showToast
import kotlinx.coroutines.delay
import net.simplifiedcoding.data.network.Resource

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel1: AuthViewModel, themeColors: ThemeColors) {
    var userManuallySelectedYear by remember { mutableStateOf(false) }
    val bannerListResponse by viewModel1.bannerListResponse.observeAsState(Resource.Loading)
    val securityCheckReportResponse by viewModel1.securityCheckReportResponse.observeAsState(Resource.Loading)
    var bannerList by remember { mutableStateOf<List<String>>(emptyList()) }
    var showGif by remember { mutableStateOf(true) }
    var showSheet by remember { mutableStateOf(false) }
    var showUser by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as? Activity
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var toolbarText by remember { mutableStateOf("") }
    val userTypeResponse by viewModel1.userTypeResponse.observeAsState(Resource.Loading)
    val fYearListResponse by viewModel1.fYearListResponse.observeAsState(Resource.Loading)
    val fYearList by remember { mutableStateOf(arrayListOf<FinancialYearItem>()) }
    var fYear by remember { mutableStateOf<List<String>>(emptyList()) }
    var userTypes by remember { mutableStateOf<List<UserTypeItem>>(emptyList()) }
    var appMenuPermissionList by remember { mutableStateOf<List<AppMenuPermission>>(emptyList()) }
    val appMenuPermissionDataResponse by viewModel1.appMenuPermissionDataResponse.observeAsState(
        Resource.Loading
    )


    LaunchedEffect(Unit) {
        delay(3000) // hide after 3 seconds
        showGif = false
    }
    LaunchedEffect(fYearListResponse) {
        if (userTypeResponse is Resource.Loading) {
            // keyboardController?.hide() // Hide keyboard when API is loading
            val jsonObject = JsonObject().apply {
                addProperty("MOBILENO", AppSharedPreferences.getInstance(context).phoneNumber)
            }
            viewModel1.fYearReq(jsonObject)
        }
    }
    when (val response = fYearListResponse) {
        is Resource.Success<*> -> {
            val jsonResponse: FinancialYearResponse = Gson().fromJson(response.value.toString(), FinancialYearResponse::class.java)
            val listResult = jsonResponse.data?.fYearListResult.orEmpty() // Avoid NPE

            fYear = listResult.map { it.fyear.orEmpty() } // Safe unwrap

            fYearList.clear()
            fYearList.addAll(listResult)

//  Set selected year if user manually selected
            val selected = AppSharedPreferences.getInstance(context).userManuallySelectedYear
            if (!selected.isNullOrEmpty()) {
                toolbarText = selected
            } else {
                val defaultYear = listResult.find { it.defaultDB == true }?.fyear
                defaultYear?.let {
                    toolbarText = it
                }
            }

            //  Set selected year when defaultDB is true
            if( AppSharedPreferences.getInstance(context).userManuallySelectedYear!!.isNotEmpty()){
                toolbarText = AppSharedPreferences.getInstance(context).userManuallySelectedYear!!
            }else{
                val defaultYear = listResult.find { it.defaultDB == true }?.fyear
                defaultYear?.let {
                    toolbarText = it // <-- This is the variable to show selected year in UI
                }
            }

        }
        is Resource.Failure -> {
     //       Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
        }
        Resource.Loading -> {
            // Optional: Keep showing progress bar while loading
        }
    }

    LaunchedEffect(userTypeResponse) {
        if (userTypeResponse is Resource.Loading) {
            // keyboardController?.hide() // Hide keyboard when API is loading
            val jsonObject = JsonObject().apply {
                addProperty("MOBILENO", AppSharedPreferences.getInstance(context).phoneNumber)
            }
            viewModel1.userType(jsonObject)
        }
    }

    // Handle API response
    when (val response = userTypeResponse) {
        is Resource.Success<*> -> {
            val jsonResponse = try {
                Gson().fromJson(response.value.toString(), UserTypeResponse::class.java)
            } catch (e: Exception) {
             context.showToast("Error parsing user types")
                return
            }

            val userTypeList = jsonResponse.data?.usersTypeListResult

            if (userTypeList.isNullOrEmpty()) {
                context. showToast("No user types found")
                return
            }

            userTypes = userTypeList // <- safe to assign now
        }
        is Resource.Failure -> {
        //    Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
        }
        Resource.Loading -> {
            // Optional: Keep showing progress bar while loading
        }
    }



    LaunchedEffect(bannerListResponse) {
        if (bannerListResponse is Resource.Loading) {
            // keyboardController?.hide() // Hide keyboard when API is loading
            val jsonObject = JsonObject().apply {
                addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            }
            viewModel1.bannerList(jsonObject)
        }
    }

    when (val response = bannerListResponse) {
        is Resource.Success<*> -> {
            Log.d("BannerList",response.value.toString())
            val jsonResponse: BannerResponse = Gson().fromJson(response.value.toString(), BannerResponse::class.java)

             bannerList = jsonResponse.data?.bannerList
                ?.map { it.LinkPath }
                ?: emptyList()

        }
        is Resource.Failure -> {
        //    Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
        }
        Resource.Loading -> {
            // Optional: Keep showing progress bar while loading
        }
    }


    LaunchedEffect(appMenuPermissionDataResponse) {
        if (appMenuPermissionDataResponse is Resource.Loading) {
            // keyboardController?.hide() // Hide keyboard when API is loading
            val jsonObject = JsonObject().apply {
                addProperty("id", AppSharedPreferences.getInstance(context).userId)
                addProperty("mobileno", AppSharedPreferences.getInstance(context).phoneNumber)
                addProperty("usertype", AppSharedPreferences.getInstance(context).groupCode)
            }
            viewModel1.appMenuPermissionDataReq(jsonObject)
        }
    }


    when (val response = appMenuPermissionDataResponse) {
        is Resource.Success<*> -> {
            Log.d("menulist", response.value.toString())
            val jsonResponse: MenuPermissionResponse =
                Gson().fromJson(response.value.toString(), MenuPermissionResponse::class.java)
            appMenuPermissionList=jsonResponse.data.appMenuPermission
        }

        is Resource.Failure -> {
         //   Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
        }

        Resource.Loading -> {
            // Optional: Keep showing progress bar while loading

        }
    }


    var showExitDialog by remember { mutableStateOf(false) }

    // Handle back press
    BackHandler {
        showExitDialog = true
    }
    val expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        val showUserRow = false

        ToolbarUtils.CustomToolbarWithCalendar(
            showUserRow = showUserRow,
            userName = toolbarText,
            onBackClick = {
                // navController.popBackStack()
                showExitDialog = true
            },
            onCalendarClick = {
                showSheet = true
            },
            expanded = expanded
        )


        userTypes.let {
            SalePartyDropdown(viewModel1,userTypes = it, onItemSelected = { /* handle selection */ })
        }
        BannerScreen(appMenuPermissionList,bannerList,navController,viewModel1,context)
        Spacer(modifier = Modifier.height(16.dp))


        // Show exit confirmation dialog
        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = { Text("Exit App") },
                text = { Text("Are you sure you want to exit?") },
                confirmButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                        activity?.finish()
                        /* val activity = LocalContext.current as? Activity
                         activity?.finish()*/
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) {
                        Text("No")
                    }
                }
            )
        }

    }

    var selectedItem by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (selectedItem == null) {
            selectedItem = "2025-2026"
        }
    }
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Financial year list", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                fYear.forEach { item ->
                    val isSelected = item == selectedItem

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.Transparent
                            )
                            .clickable {
                                selectedItem = item
                                AppSharedPreferences.getInstance(context).saveUserManuallySelectedYear(item)
                                toolbarText = item
                                showSheet = false
                            }
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        )

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }



    if (showUser) {
        ModalBottomSheet(
            onDismissRequest = { showUser = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("List", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                userTypes.forEach { item ->

                    Text(
                        text = item.Name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                // handle item click
                                toolbarText=item.Name
                                showUser = false
                            }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }


}

@Composable
fun DiwaliGifFromDrawable() {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    AsyncImage(
        model = ImageRequest.Builder(context)
        /*    .data("file:///android_asset/design_trippy.gif") // Reference to drawable GIF*/
            .build(),
        imageLoader = imageLoader,
        contentDescription = "Diwali GIF",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

@Composable
fun SalePartyDropdown(
    viewModel1: AuthViewModel,
    userTypes: List<UserTypeItem>,
    onItemSelected: (UserTypeItem) -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(userTypes) {
        val savedPartyName = AppSharedPreferences.getInstance(context).selectedPartyName
        selectedText = savedPartyName ?: ""

        val matchedItem = userTypes.find { it.Name == savedPartyName }
        matchedItem?.let {
            onItemSelected(it)

            val jsonObject = JsonObject().apply {
                addProperty("id", AppSharedPreferences.getInstance(context).userId)
                addProperty("mobileno", AppSharedPreferences.getInstance(context).phoneNumber)
                addProperty("usertype", AppSharedPreferences.getInstance(context).groupCode)
            }
            viewModel1.appMenuPermissionDataReq(jsonObject)
        }
    }

    val filteredList by remember(searchQuery, userTypes) {
        derivedStateOf {
            if (searchQuery.isBlank()) userTypes
            else userTypes.filter { it.Name.contains(searchQuery, ignoreCase = true) }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        // Card as dropdown trigger
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(40.dp)
                .clickable { expanded = !expanded },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (selectedText.isEmpty()) "Select Party..." else selectedText,
                        color = if (selectedText.isEmpty()) Color.Gray else Color.Black,
                        fontSize = 13.sp,
                        fontFamily = poppinsFontFamily1,
                        fontWeight = FontWeight.Normal
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Search box
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )

            // Dropdown items
            if (filteredList.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No match found", color = Color.Gray) },
                    onClick = {}
                )
            } else {
                filteredList.forEach { userType ->
                    DropdownMenuItem(
                        text = { Text(userType.Name, fontSize = 15.sp) },
                        onClick = {
                            selectedText = userType.Name
                            expanded = false
                            searchQuery = ""
                            onItemSelected(userType)

                            // Save to SharedPreferences
                            AppSharedPreferences.getInstance(context).apply {
                                saveSelectedPartyName(userType.Name)
                                savePartyCode(userType.PartyCode)
                                savePartyId(userType.ID)
                                saveUserType("true")
                                saveGroupCode(userType.GroupCode)
                                saveUserId(userType.ID)
                                saveAnyChosen(true, context)
                            }

                            // Call API
                            val jsonObject = JsonObject().apply {
                                addProperty("id", AppSharedPreferences.getInstance(context).userId)
                                addProperty("mobileno", AppSharedPreferences.getInstance(context).phoneNumber)
                                addProperty("usertype", AppSharedPreferences.getInstance(context).groupCode)
                            }
                            viewModel1.appMenuPermissionDataReq(jsonObject)
                        }
                    )
                }
            }
        }
    }
}
