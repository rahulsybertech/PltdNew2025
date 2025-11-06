package com.syber.ssspltd.ui.view.addorder

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ExposedDropdownMenuDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.LocalTextStyle
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.syber.ssspltd.data.model.userType.ApiResponse
import com.syber.ssspltd.data.model.userType.UserType
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.theme.WhiteSmoke
import com.syber.ssspltd.utils.CustomButton
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import com.syber.ssspltd.utils.MyConstant
import com.syber.ssspltd.utils.NetworkUtils
import com.syber.ssspltd.utils.SubmitButton
import net.simplifiedcoding.data.network.Resource


@Composable
fun SupplierOrderScreen(navController: NavController,viewModel1: AuthViewModel,themeColors: ThemeColors) {
    val context = LocalContext.current
    val activity = (context as? Activity)
    val userTypeResponse by viewModel1.userTypeResponse.observeAsState(Resource.Loading)
    var selectedType by remember { mutableStateOf<String?>(null) }
    var userTypes by remember { mutableStateOf<List<UserType>>(emptyList()) }


    // Handle back press
    BackHandler {
        activity?.finish()
    }

    // Fetch user types on composition
    LaunchedEffect(userTypeResponse) {
        if (userTypeResponse is Resource.Loading) {
            val jsonObject = JsonObject().apply {
                addProperty("MOBILENO", "7290087642")
            }
            viewModel1.userType(jsonObject)
        }
    }

    // Handle API response
    when (val response = userTypeResponse) {
        is Resource.Success<*> -> {
            val jsonResponse: ApiResponse = Gson().fromJson(response.value.toString(), ApiResponse::class.java)
            userTypes = jsonResponse.UsersTypeListResult
        }
        is Resource.Failure -> {
            Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
        }
        Resource.Loading -> {
            // Optional: Keep showing progress bar while loading
        }
    }
    AddOrderScreen(navController)
  //  AddOrderScreen(themeColors)
    /*Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Order") },
                backgroundColor = Color.Red,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NickNameAndOrderNoFields()
            MarketerField()
         //   ModelDropdown()
            SalePartyDropdown(userTypes, onItemSelected = {})

        }
    }*/



}


@Composable
fun AddOrderScreen(themeColors: ThemeColors,
    onBackClick: () -> Unit = {}
) {
    var subPartyRemarkVisible by remember { mutableStateOf(true) }
    var subPartyRemark by remember { mutableStateOf("") }
    var transport by remember { mutableStateOf("") }
    var station by remember { mutableStateOf("") }
    var scheme by remember { mutableStateOf("") }
    var nickName by remember { mutableStateOf("") }
//    var userTypes by remember { mutableStateOf<List<UserType>>(emptyList()) }
    var selectedUserType by remember { mutableStateOf<UserType?>(null) }
    val userTypes = listOf(
        UserType("1", "Retailer","",""),
        UserType("2", "Wholesaler","",""),
        UserType("3", "Distributor","","")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Order",fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold,fontSize = 15.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
                ,
                backgroundColor = Color.White
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        )
                {
                    // Marketer Code & Serial No
                    Row(Modifier.fillMaxWidth()) {
                        ReadOnlyField(
                            label = "Marketer Code",
                            value = "123456",
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                        ReadOnlyField(
                            label = "Serial No",
                            value = "SN987654",
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        )
                    }
                    SaleReportFilterScreen()

                    SubmitButton(
                        text = "Submit",
                        onClick = {
                            var valid = true

                    /*        if (honorName.isEmpty()) { honorNameError = "Honor Name is required"; valid = false }
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

                            }*/
                        },
                        modifier = Modifier
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),themeColors
                    )
                /*    // Call reusable section
                    AdditionalFieldsSection(
                        subPartyRemarkVisible = subPartyRemarkVisible,
                        subPartyRemark = subPartyRemark,
                        transport = transport,
                        station = station,
                        scheme = scheme,
                        nickName = nickName,
                        onSubPartyRemarkChange = { subPartyRemark = it },
                        onTransportChange = { transport = it },
                        onStationChange = { station = it },
                        onSchemeChange = { scheme = it },
                        onNickNameChange = { nickName = it },
                        onSearchClick = {
                            Log.d("AddOrderScreen", "Searching nickname: $nickName")
                        }
                    )*/
                }


    }
}

@Composable
fun ReadOnlyField(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = label, color = Color.Gray,fontSize = 15.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .background(WhiteSmoke)
                .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
                .padding(12.dp)
        ) {
            Text(text = value,fontSize = 13.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium)
        }
    }
}


@Composable
fun AdditionalFieldsSection(
    subPartyRemarkVisible: Boolean,
    subPartyRemark: String,
    transport: String,
    station: String,
    scheme: String,
    nickName: String,
    onSubPartyRemarkChange: (String) -> Unit,
    onTransportChange: (String) -> Unit,
    onStationChange: (String) -> Unit,
    onSchemeChange: (String) -> Unit,
    onNickNameChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Sub Party Remark Field (conditionally shown)
        if (subPartyRemarkVisible) {
            OutlinedTextField(
                value = subPartyRemark,
                onValueChange = onSubPartyRemarkChange,
                label = { Text("Remark") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }


        // Scheme
        OutlinedTextField(
            value = scheme,
            onValueChange = onSchemeChange,
            label = { Text("Scheme") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        // Nickname Dropdown (AutoComplete)
        OutlinedTextField(
            value = nickName,
            onValueChange = onNickNameChange,
            label = { Text("Nick Name List") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}


@Preview
@Composable
fun NickNameAndOrderNoFields() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Nick Name") },
            modifier = Modifier.weight(1f),
            enabled = false,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Order No") },
            modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
    }
}
@Preview
@Composable
fun MarketerField() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Marketer Name",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body1
        )
        IconButton(onClick = { /* Clear action */ }) {
            Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.Red)
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ModelDropdown() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedFruit by remember { mutableStateOf<Fruit?>(null) }

    // Your list coming from model
    val fruitOptions = listOf(
        Fruit(1, "Apple"),
        Fruit(2, "Banana"),
        Fruit(3, "Mango"),
        Fruit(4, "Grapes"),
        Fruit(5, "Orange")
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedFruit?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Fruit") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            fruitOptions.forEach { fruit ->
                DropdownMenuItem(
                    text = { Text(text = fruit.name) },
                    onClick = {
                        selectedFruit = fruit
                        expanded = false
                        Toast.makeText(context, "Selected ID: ${fruit.id}, Name: ${fruit.name}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}



@Composable
fun MarketerDropdown(userTypes: List<UserType>, onItemSelected: (UserType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select User Type") },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }, // Make TextField clickable
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF1976D2)
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
            /* .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))*/
        ) {
            userTypes.forEach { userType ->
                DropdownMenuItem(
                    text = { Text(text = userType.Name) },
                    onClick = {
                        selectedText = userType.Name // Set the Name in TextField
                        expanded = false
                        onItemSelected(userType) // Pass full UserType object outside
                    }
                )
            }
        }
    }
}
@Composable
fun SalePartyDropdown(
    label: String,
    userTypes: List<UserType>,
    onItemSelected: (UserType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    // Filter the list based on the search query
    val filteredList by remember(searchQuery, userTypes) {
        derivedStateOf {
            if (searchQuery.isBlank()) userTypes
            else userTypes.filter { it.Name.contains(searchQuery, ignoreCase = true) }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text(label,fontSize = 15.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF1976D2)
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Add Search TextField
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // Display filtered user types
            filteredList.forEach { userType ->
                DropdownMenuItem(
                    text = { Text(text = userType.Name) },
                    onClick = {
                        selectedText = userType.Name
                        expanded = false
                        onItemSelected(userType)
                        searchQuery = "" // Clear search after selection
                    }
                )
            }

            if (filteredList.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No match found", color = Color.Gray) },
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun SaleReportFilterScreen() {
    val dummyList = listOf(
        UserType("1", "Item A","",""),
        UserType("2", "Item B","",""),
        UserType("3", "Item C","","")
    )
  var subPartyRemarkVisible: Boolean
    var selectedSaleParty by remember { mutableStateOf<UserType?>(null) }
    var selectedTransport by remember { mutableStateOf<UserType?>(null) }
    var selectedSubParty by remember { mutableStateOf<UserType?>(null) }
    var selectedStation by remember { mutableStateOf<UserType?>(null) }

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        SalePartyDropdown("Sale Party",userTypes = dummyList) {
            selectedSaleParty = it
        }

        Row(Modifier.fillMaxWidth().padding(top = 8.dp)) {
            ReadOnlyField(
                label = "Available Limit",
                value = "₹10,000",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            ReadOnlyField(
                label = "Average Days",
                value = "30",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = true, onClick = { subPartyRemarkVisible = false })
            Text("Sub Party", modifier = Modifier.padding(end = 16.dp),fontSize = 15.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium)
            RadioButton(selected = false, onClick = { subPartyRemarkVisible = true })
            Text("Sub Party as Remark",fontSize = 15.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium )
        }

        SalePartyDropdown("Sub Party",userTypes = dummyList) {
            selectedTransport = it
        }


        SalePartyDropdown("Transport",userTypes = dummyList) {
            selectedSubParty = it
        }

        SalePartyDropdown("Booking Station",userTypes = dummyList) {
            selectedStation = it
        }
    }
}








