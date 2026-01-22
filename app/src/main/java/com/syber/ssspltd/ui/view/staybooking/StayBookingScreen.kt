package com.syber.ssspltd.ui.view.staybooking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.JsonParser
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.data.model.staybooking.GuestMasterDetail
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.utils.AppSharedPreferences
import com.syber.ssspltd.utils.DateTimeParser.parseDate
import com.syber.ssspltd.utils.DateTimeParser.parseTime
import com.syber.ssspltd.utils.DateTimeParser.to12HourFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingRequestScreen(navController: NavController,
    modifier: Modifier = Modifier,
    viewModel1: AuthViewModel,
    onSaveClick: () -> Unit = {


    }
) {
    val guestList by viewModel1.guestList.collectAsState()
    val stayBookingByRecordID by viewModel1.stayBookingByRecordID.collectAsState()
    val deleteGuestResult by viewModel1.deleteGuestResult.collectAsState()

    val selectedGuests = remember { mutableStateListOf<GuestMasterDetail>() }
    val branchList by viewModel1.branchList.collectAsState()
    var selectedGuestImages by remember { mutableStateOf<List<String>>(emptyList()) }
    var showSheet by remember { mutableStateOf(false) }
    val bookingResult by viewModel1.bookingResult.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedBranchName by remember { mutableStateOf("Select Branch") }
      var selectedBranch by remember { mutableStateOf("") }

    var showNickNameDialog by remember { mutableStateOf(false) }
    var showCustomerDialog by remember { mutableStateOf(false) }

    var selectedBrancId by remember { mutableStateOf("") }
    var selectedNickNameId by remember { mutableStateOf("") }
    var selectedCustomerId by remember { mutableStateOf("") }

    // Track whether check-in time is selected
    var isCheckInTimeSelected by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var isNewParty by remember { mutableStateOf(false) }
    var firmName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var checkInDate by remember { mutableStateOf<LocalDate?>(null) }
    var checkOutDate by remember { mutableStateOf<LocalDate?>(null) }

    var checkInTime by remember { mutableStateOf<LocalTime?>(null) }
    var checkOutTime by remember { mutableStateOf<LocalTime?>(null) }
/*    var selectedNickName = viewModel1.selectedNickName.value
    var selectedCustomerName = viewModel1.selectedCustomerName.value*/
    var selectedNickName by remember { mutableStateOf("") }

    var selectedCustomerName by remember { mutableStateOf("") }
    var selectedCheckInDate= viewModel1.selectedCheckInDate.value
    var selectedCheckOutDate = viewModel1.selectedCheckOutDate.value
    var stayOption by remember { mutableStateOf("Yes") }
    var stayBooking by remember { mutableStateOf(true) }
    var checkGuestList by remember { mutableStateOf<List<String>>(emptyList()) }

    /* Api/StayBooking/GetBranchDetailList*/

    // Call API when screen first opens
 /*   LaunchedEffect(Unit) {
        AppSharedPreferences.getInstance(context).isPartyCode?.let {
            viewModel1.fetchGuestList(
                "",
                it
            )
        }


    }*/
    val booking = viewModel1.selectedBooking
    if (booking == null) {
        if( AppSharedPreferences.getInstance(context).groupCode.equals("Customer")){
            viewModel1.fetchBranchList()
        }
    } else {
        LaunchedEffect(booking) {
            booking.let {
                selectedNickName = it.nickName.orEmpty()
                selectedBranch = it.branchName.orEmpty()
                selectedBrancId = it.branchID.orEmpty()
                selectedCustomerName = it.accountName.orEmpty()
                selectedNickNameId=it.nickNameID.orEmpty()
                selectedCustomerId=it.accountID.orEmpty()
                AppSharedPreferences.getInstance(context).isPartyCode?.let { it1 ->
                    viewModel1.fatchStayBookingListByRecordIdReq(it.id.toString(),
                        it1
                    )
                }
                viewModel1.fetchGuestList(
                    "",
                    selectedCustomerId
                )


            }
            // --- Check In ---
            booking.checkInDate?.let { dateStr ->
                checkInDate = parseDate(dateStr)
            }

            booking.checkInTime?.let { timeStr ->
                checkInTime = parseTime(timeStr)
                isCheckInTimeSelected = true
            }

            // --- Check Out ---
            booking.checkoutDate?.let { dateStr ->
                checkOutDate = parseDate(dateStr)
            }

            booking.checkoutTime?.let { timeStr ->
                checkOutTime = parseTime(timeStr)
            }
            booking.isStay?.let { value ->
                stayBooking = booking.isStay ?: false
            }

        }


    }







    LaunchedEffect(selectedCustomerId.isNotEmpty()) {
        viewModel1.fetchGuestList(
            selectedCustomerId,
            AppSharedPreferences.getInstance(context).isPartyCode.toString()
        )
    }
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
                                navController.navigate(Screen.AddGuestScreen.route)
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
                                    "Add Guest",
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
        }

    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            if( AppSharedPreferences.getInstance(context).groupCode.equals("Customer")){
                var showBranchDialog by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = selectedBranch,
                    onValueChange = {},
                    label = { Text("Select Branch") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                showBranchDialog = true
                                viewModel1.fetchBranchList()   // ⬅ API call on click
                            }
                        )
                    }
                )
                if (showBranchDialog) {

                    val branchList by viewModel1.branchList.collectAsState()
                    val loading by viewModel1.loading.collectAsState()

                    var searchText by remember { mutableStateOf("") }

                    Dialog(onDismissRequest = { showBranchDialog = false }) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {

                                // 🔍 Search TextField
                                OutlinedTextField(
                                    value = searchText,
                                    onValueChange = { searchText = it },
                                    label = { Text("Search Branch") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                if (loading) {
                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(200.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                } else {

                                    // 🔎 Filter list based on search
                                    val filteredList = branchList.filter { item ->
                                        item.branchName?.contains(searchText, ignoreCase = true) == true
                                    }

                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    ) {
                                        items(filteredList) { item ->
                                            Text(
                                                text = item.branchName ?: "Unknown",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {

                                                        selectedBranchName = item.branchName ?: ""
                                                        selectedBranch = item.branchName ?: ""
                                                        selectedBrancId = item.id ?: ""

                                                        // Close dialog
                                                        showBranchDialog = false
                                                    }
                                                    .padding(10.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
            else{

                StayOptionSelector(
                    isStay = stayBooking,
                    onChange = { value ->
                        stayBooking = value
                        stayOption = if (value) "Yes" else "No"
                    }
                )
                OutlinedTextField(
                    value = selectedNickName,
                    onValueChange = {},
                    label = { Text("Select Nick Name") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                showNickNameDialog = true
                                viewModel1.fetchNickNameList()
                            }
                        )
                    }
                )
                if (showNickNameDialog) {

                    val nickNameList by viewModel1.nickNameList.collectAsState()
                    val loading by viewModel1.loading.collectAsState()

                    var searchText by remember { mutableStateOf("") }

                    Dialog(onDismissRequest = { showNickNameDialog = false }) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {

                                // 🔍 Search TextField
                                OutlinedTextField(
                                    value = searchText,
                                    onValueChange = { searchText = it },
                                    label = { Text("Search Nick Name") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                if (loading) {
                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(200.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                                else {

                                    // 🔎 Filter list based on search text
                                    val filteredList = nickNameList.filter { item ->
                                        item.name?.contains(searchText, ignoreCase = true) == true
                                    }

                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    ) {
                                        items(filteredList) { item ->
                                            Text(
                                                text = item.name ?: "Unknown",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {

                                                        selectedNickName = item.name ?: ""
                                                        isNewParty = item.name == "NEW PARTY"
                                                        selectedCustomerName = ""
                                                        selectedCustomerId = ""
                                                        viewModel1.selectedNickName.value =
                                                            item.name ?: ""
                                                        viewModel1.selectedNickNameId.value =
                                                            item.id ?: ""
                                                        viewModel1.clearGuestList()
                                                        selectedNickNameId = item.id ?: ""
                                                        showNickNameDialog = false
                                                    }
                                                    .padding(10.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (isNewParty) {
                    OutlinedTextField(
                        value = firmName,
                        onValueChange = { firmName = it },
                        label = { Text("Firm Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { value ->

                            // Accept only numbers
                            phoneNumber = value.filter { it.isDigit() }

                            // When length == 10 → call API
                            if (phoneNumber.length == 10) {
                                viewModel1.fetchGuestByPhoneNumList(
                                    phoneNumber,
                                    AppSharedPreferences.getInstance(context).isPartyCode.toString()
                                )
                            }else{
                                viewModel1.clearGuestList()
                            }
                        },
                        label = { Text("Phone Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }else{
                    OutlinedTextField(
                        value = selectedCustomerName,
                        onValueChange = {},
                        label = { Text("Select Customer") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    showCustomerDialog = true
                                    viewModel1.fetchCustomerList(selectedNickNameId)
                                }
                            )
                        }
                    )
                    if (showCustomerDialog) {

                        val nickNameList by viewModel1.customerList.collectAsState()
                        val loading by viewModel1.loading.collectAsState()

                        Dialog(onDismissRequest = { showCustomerDialog = false }) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                if (loading) {
                                    Box(
                                        Modifier
                                            .size(200.dp)
                                            .padding(20.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                } else {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(20.dp)
                                    ) {
                                        items(nickNameList) { item ->
                                            Text(
                                                text = item.name ?: "Unknown",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        viewModel1.selectedCustomerName.value =
                                                            item.name ?: ""
                                                        viewModel1.selectedCustomerId.value =
                                                            item.id ?: ""
                                                        selectedCustomerName = item.name ?: ""
                                                        selectedCustomerId = item.id ?: ""
                                                        showCustomerDialog = false
                                                        viewModel1.fetchGuestList(
                                                            selectedCustomerId,
                                                            AppSharedPreferences.getInstance(context).isPartyCode.toString()
                                                        )
                                                    }
                                                    .padding(10.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

// ----------------- Customer Dropdown -----------------


            }


            Spacer(Modifier.height(20.dp))


// 🗓️ Dates Row
            if(stayOption.equals("Yes")){
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // 🟩 Check-In Date
                    DateCard("Check In Date", checkInDate?.toString() ?: "Select Date") {
                        openCalendar(context, calendar) { picked ->
                            checkInDate = picked

                            // 🧹 Reset all dependent fields
                            checkInTime = null
                            checkOutDate = null
                            checkOutTime = null
                            isCheckInTimeSelected = false

                            Toast.makeText(
                                context,
                                "Please select Check-in time next",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    // 🟩 Check-Out Date
                    DateCard("Check Out Date", checkOutDate?.toString() ?: "Select Date") {
                        // 🔒 Prevent selecting Check-Out Date until Check-In Time is selected
                        if (!isCheckInTimeSelected) {
                            Toast.makeText(
                                context,
                                "Please select Check-in time first",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@DateCard
                        }

                        openCalendar(context, calendar) { picked ->
                            if (checkInDate != null && picked < checkInDate!!) {
                                Toast.makeText(
                                    context,
                                    "Check-out date must be after Check-in date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                checkOutDate = picked
                                checkOutTime = null // reset time on new date
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

// ⏰ Times Row
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Check-In Time
                    DateCard(
                        "Check In Time",
                        checkInTime?.to12HourFormat() ?: "Select Time"
                    ) {
                        if (checkInDate == null) {
                            Toast.makeText(context, "Please select Check-in date first", Toast.LENGTH_SHORT).show()
                            return@DateCard
                        }

                        openTimePicker(context) { pickedTime ->
                            checkInTime = pickedTime
                            isCheckInTimeSelected = true

                            checkOutDate = null
                            checkOutTime = null
                        }
                    }

                    // Check-Out Time
                    DateCard(
                        "Check Out Time",
                        checkOutTime?.to12HourFormat() ?: "Select Time"
                    ) {
                        if (checkOutDate == null) {
                            Toast.makeText(context, "Please select Check-out date first", Toast.LENGTH_SHORT).show()
                            return@DateCard
                        }

                        openTimePicker(context) { pickedTime ->
                            when {
                                checkInDate == null -> {
                                    Toast.makeText(context, "Please select Check-in date first", Toast.LENGTH_SHORT).show()
                                }

                                checkInTime == null -> {
                                    Toast.makeText(context, "Please select Check-in time first", Toast.LENGTH_SHORT).show()
                                }

                                checkOutDate!! < checkInDate!! -> {
                                    Toast.makeText(context, "Check-out date must be after Check-in date", Toast.LENGTH_SHORT).show()
                                }

                                checkOutDate == checkInDate && pickedTime <= checkInTime!! -> {
                                    Toast.makeText(context, "Check-out time must be after Check-in time", Toast.LENGTH_SHORT).show()
                                }

                                else -> checkOutTime = pickedTime
                            }
                        }
                    }
                }
            }



            Spacer(Modifier.height(20.dp))

            // Search button
            Button(
                onClick = {

                    val groupCode = AppSharedPreferences.getInstance(context).groupCode

                    // ===============================
                    // VALIDATION
                    // ===============================

                    if (groupCode == "Others") {
                        if (selectedNickName.isEmpty()||selectedNickName.equals("Select Nick Name")) {
                            Toast.makeText(context, "Please select a Nick Name", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if (selectedCustomerName.isEmpty()||selectedCustomerName.equals("Select Customer")) {
                            Toast.makeText(context, "Please select a Customer Name", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                    } else {

                        if (selectedBrancId.isNullOrEmpty()) {
                            Toast.makeText(context, "Please select a branch", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                    }

                    if (checkInDate == null) {
                        Toast.makeText(context, "Please select Check-In Date", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (checkInTime == null) {
                        Toast.makeText(context, "Please select Check-In Time", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (checkOutDate == null) {
                        Toast.makeText(context, "Please select Check-Out Date", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (checkOutTime == null) {
                        Toast.makeText(context, "Please select Check-Out Time", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (selectedGuests.isEmpty()) {
                        Toast.makeText(context, "Please select at least one guest", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // ===============================
                    // BUILD JSON REQUEST
                    // ===============================

                    try {

                        val jsonObject = JSONObject().apply {

                            put("id", JSONObject.NULL)

                            if (groupCode == "Others") {
                                // Creating new party
                                if (isNewParty) {
                                    put("accountID", JSONObject.NULL)
                                    put("isNewUser", true)
                                    put("mobileNo", phoneNumber)
                                    put("firmName", firmName)

                                } else {
                                    // Existing customer
                                    put("accountID", selectedCustomerId)
                                    put("isNewUser", false)

                                    if (stayBooking) {
                                        put("mobileNo", JSONObject.NULL)
                                        put("firmName", JSONObject.NULL)
                                    }

                                    put("branchID", JSONObject.NULL)
                                }

                            } else {
                                // GroupCode != Other
                                put("accountID", JSONObject.NULL)
                                put("branchID", selectedBrancId)
                                put("isNewUser", false)
                                put("mobileNo", JSONObject.NULL)
                                put("firmName", JSONObject.NULL)
                            }

                            put("companyID", JSONObject.NULL)
                            put("partyCode", AppSharedPreferences.getInstance(context).isPartyCode)

                            put("checkInDate", checkInDate?.toString() ?: "")
                            put("checkInTime", checkInTime!!.to12HourFormat()  ?: "")
                            put("checkoutDate", checkOutDate?.toString() ?: "")
                            put("checkoutTime", checkOutTime!!.to12HourFormat()  ?: "")
                            put("isStay", stayBooking)

                            if (groupCode == "Customer" || stayBooking) {
                                val ids = JSONArray()
                                selectedGuests.forEach { ids.put(it.id) }
                                put("guestIds", ids)
                                put("noOfPerson", selectedGuests.size.toString())

                            } else {
                                put("guestIds", JSONObject.NULL)
                                put("noOfPerson", "0")
                            }
                        }

                        // Optionally convert using Gson
                        val gsonObject = JsonParser.parseString(jsonObject.toString()).asJsonObject

                        Log.d("ADD Request", jsonObject.toString())

                        // ViewModel call example
                         viewModel1.submitStayBooking(gsonObject)

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF008080)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Submit", color = Color.White, fontSize = 18.sp)
            }




            LaunchedEffect(bookingResult) {
                bookingResult?.let { response ->
                    if (response.ResponseStatus) {
                        Toast.makeText(context, response.ResponseMessage, Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Booking failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            if(stayBooking)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text("Guest List", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    items(guestList) { guest ->

                        val isChecked =
                            stayBookingByRecordID.contains(guest.id) ||
                                    selectedGuests.any { it.id == guest.id }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    if (checked) {
                                        if (!selectedGuests.any { it.id == guest.id }) {
                                            selectedGuests.add(guest)
                                        }
                                    } else {
                                        selectedGuests.removeAll { it.id == guest.id }
                                    }
                                },
                                modifier = Modifier.size(22.dp)
                            )

                            Spacer(Modifier.width(10.dp))

                            Text(
                                text = guest.guestName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = "View Image",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF0066CC),
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .clickable {
                                        selectedGuestImages = guest.imageList
                                        showSheet = true
                                    }
                            )

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Guest",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        viewModel1.deleteGuestParam(guest.id)
                                    }
                            )
                        }
                    }
                }

                /*  GuestSelectionList(guestList,navController)*/
                if (showSheet) {
                    ImagePreviewBottomSheet(
                        images = selectedGuestImages,
                        onDismiss = { showSheet = false }
                    )
                }

            }
            LaunchedEffect(deleteGuestResult) {
                deleteGuestResult?.let { response ->
                    if (response.ResponseStatus) {
                        Toast.makeText(context, response.ResponseMessage, Toast.LENGTH_SHORT).show()
                        AppSharedPreferences.getInstance(context).isPartyCode?.let {
                            viewModel1.fetchGuestList(
                                "",
                                it
                            )
                        }
                    } else {
                        Toast.makeText(context, "Booking failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }




    }


}






@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ImagePreviewBottomSheet(
    images: List<String>,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {

        val pagerState = rememberPagerState(pageCount = { images.size })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // ---- Fullscreen Pager ----
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) { page ->
                AsyncImage(
                    model = images[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(16.dp))

            // ---- Thumbnails Grid ----
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(images.size) { index ->
                    AsyncImage(
                        model = images[index],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .clickable {
                                // jump pager to clicked image
                                CoroutineScope(Dispatchers.Main).launch {
                                    pagerState.scrollToPage(index)
                                }
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun DateCard(label: String, date: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = date,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color(0xFF008080),
                    modifier = Modifier.clickable { onClick() }
                )
            },
            modifier = Modifier
                .width(160.dp)
                .height(45.dp) // 👈 custom height
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateCardTime(label: String, date: LocalDateTime?, onClick: () -> Unit) {
    val display = date?.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")) ?: "Select"
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label)
            Spacer(Modifier.height(6.dp))
            Text(display)
        }
    }
}
@Composable
fun TripTypeChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        color = if (selected) Color(0xFFFFC107) else Color.LightGray.copy(alpha = 0.3f),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Color.Black else Color.DarkGray
        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun isCheckOutValid(checkIn: LocalDateTime?, checkOut: LocalDateTime?): Boolean {
    if (checkIn == null || checkOut == null) return true // nothing to compare yet
    return checkOut.isAfter(checkIn) // ✅ must be strictly later
}

@RequiresApi(Build.VERSION_CODES.O)
fun openTimePicker(context: Context, onTimePicked: (LocalTime) -> Unit) {
    val now = LocalTime.now()
    TimePickerDialog(
        context,
        { _, hour, minute ->
            onTimePicked(LocalTime.of(hour, minute))
        },
        now.hour,
        now.minute,
        true // 24-hour format
    ).show()
}



@Composable
fun StayOptionSelector(
    isStay: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isStay,
            onClick = { onChange(true) }
        )
        Text("Stay Yes")

        Spacer(Modifier.width(16.dp))

        RadioButton(
            selected = !isStay,
            onClick = { onChange(false) }
        )
        Text("Stay No")
    }
}



@RequiresApi(Build.VERSION_CODES.O)
fun openCalendar(
    context: Context,
    calendar: Calendar,
    onDateSelected: (LocalDate) -> Unit
) {
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val picked = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(picked)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun openCalendarTime(context: Context, calendar: Calendar, onDatePicked: (LocalDate) -> Unit) {
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, y, m, d ->
            val pickedDate = LocalDate.of(y, m + 1, d) // ✅ LocalDate, not String
            onDatePicked(pickedDate)
        },
        year, month, day
    ).show()
}



