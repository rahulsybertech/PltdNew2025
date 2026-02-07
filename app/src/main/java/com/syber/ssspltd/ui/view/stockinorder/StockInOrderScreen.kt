package com.syber.ssspltd.ui.view.stockinorder

import android.app.Activity
import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.syber.ssspltd.R
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.saleReport.FilterBottomSheet
import com.syber.ssspltd.utils.AppSharedPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockInOrderScreen(
    navController: NavController,
    viewModel1: AuthViewModel
) {
    val orderList by viewModel1.stockInoffice.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    // Correct list to pass to LazyColumn:
    val context = LocalContext.current

// Call API when screen first opens
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {
            addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("PARTYCODE",AppSharedPreferences.getInstance(context).isPartyCode )
            addProperty("DATAKEY", "SUPPLIER")
            addProperty("DBNAME", "")
            addProperty("FILTERTYPE", "STOCKINOFFICE")
        }
        viewModel1.fetchStockInOffice(jsonObject)
    }

    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 8.dp,
                color = Color(0xFF008080) // Teal color for AppBar
            ) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                "Stock in Office",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            if (orderList.isNotEmpty()) {
                               /* Text(
                                    text = "${orderList.get(0).DefaultStartDate ?: "--"} to ${orderList.get(0).DefaultEndDate ?: "--"}",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )*/
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {      showFilterSheet = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.filter),
                                contentDescription = "Filter",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent, // Surface gives background
                        titleContentColor = Color.White
                    )
                )
            }
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            when {
                isLoading -> {
                    // Loading Indicator
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                orderList.isEmpty() -> {
                    // Empty State
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No data available")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(orderList) { order ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(6.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    // Info Section
                                    InfoRow("Supplier Name", order.Supplier ?: "", Color(0xFF2E7D32))
                                    InfoRow("Marketer Name", order.Marketer ?: "")
                                    InfoRow("Bill No", order.BillNo ?: "")
                                    InfoRow("Sub Party", order.SubParty ?: "")
                                    InfoRow("Status", order.BillStatus ?: "", Color(0xFFD32F2F))

                                    Spacer(modifier = Modifier.height(16.dp))
                                    Divider(thickness = 1.dp, color = Color(0xFFE0E0E0))
                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Table with horizontal scroll
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Column {
                                            Text("Date", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(order.BillDate ?: "", color = Color.Gray, fontSize = 13.sp)
                                        }
                                        Column {
                                            Text("Purchase No.", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(order.PurchaseNo ?: "", color = Color(0xFFD32F2F), fontSize = 13.sp)
                                        }
                                        Column {
                                            Text("PCS", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(order.Pcs ?: "", fontSize = 13.sp)
                                        }
                                        Column {
                                            Text("Amount", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(order.PAmount ?: "", fontWeight = FontWeight.Bold, color = Color(0xFF1565C0), fontSize = 13.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }


            }
            if (showFilterSheet) {
                FilterBottomSheetStock(viewModel=viewModel1,
                    sheetState = sheetState,
                    onDismiss = { showFilterSheet = false },
                    onApply = {
                        showFilterSheet = false
                        // Apply filter logic
                    }
                )
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, valueColor: Color = Color.Black) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "$label :",
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF424242),
            fontSize = 14.sp
        )
        Text(
            value,
            color = valueColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetStock(
    viewModel: AuthViewModel,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    val saleItems by viewModel.saleItems.collectAsState()
    var selectedFilter by remember { mutableStateOf("Date") }
    val adjustmentType by viewModel.adjustmentType.collectAsState()
    val branchList by viewModel.branch.collectAsState()
    val subPartyList by viewModel.subParty.collectAsState()
    val supplierList by viewModel.supplier.collectAsState()
    val transporterList by viewModel.transporter.collectAsState()

    val selectedBranches = remember { mutableStateListOf<String>() }
    val selectedSubParty = remember { mutableStateListOf<String>() }
    val selectedSupplier = remember { mutableStateListOf<String>() }
    val selectedTransporter = remember { mutableStateListOf<String>() }

    // -------------------- DATE STATES --------------------
    val context = LocalContext.current
    var fromDate by remember { mutableStateOf("DD/MM/YYYY") }
    var toDate by remember { mutableStateOf("DD/MM/YYYY") }

    val calendar = Calendar.getInstance()
    LaunchedEffect(saleItems) {
        if (saleItems.isNotEmpty()) {
            fromDate = saleItems[0].DefaultStartDate ?: "DD/MM/YYYY"
            toDate = saleItems[0].DefaultEndDate ?: "DD/MM/YYYY"
        }
    }
    fun openFromDatePicker() {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                fromDate = "$day/${month + 1}/$year"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun openToDatePicker() {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                toDate = "$day/${month + 1}/$year"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color.White,
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // ---------- Header ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Icon(
                    painter = painterResource(R.drawable.ic_criss_cross),
                    contentDescription = "Close",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onDismiss() }
                )
            }

            Spacer(Modifier.height(16.dp))

            // ---------- MAIN CONTENT ----------
            Row(modifier = Modifier.fillMaxWidth()) {

                // LEFT SIDE (Options)
                Column(
                    Modifier
                        .weight(0.3f)
                        .padding(end = 5.dp)
                ) {
                    listOf("Date","Branch", "SubParty", "Supplier").forEachIndexed { index, title ->
                        FilterOptionStock (
                            title = title,
                            selected = selectedFilter == title,
                            onClick = { selectedFilter = title }
                        )
                        if (index != 4) androidx.compose.material.Divider()
                    }
                }

                // Vertical Divider
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(IntrinsicSize.Max)
                        .background(Color(0xFF1565C0))
                )




                // RIGHT SIDE
                Column(
                    modifier = Modifier
                        .weight(1.3f)
                        .padding(start = 12.dp)
                ) {

                    //------------------------------------------------------------
                    //                      DATE FILTER
                    //------------------------------------------------------------
                    if (selectedFilter == "Date") {

                        /*       Text("From date", fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                               Text("To date", fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                       */        Spacer(Modifier.height(12.dp))

                        Row {

                            // FROM DATE BOX
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .background(Color(0xFFFFEBEE), RoundedCornerShape(6.dp))
                                    .clickable { openFromDatePicker() }
                                    .padding(start = 10.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(fromDate, fontSize = 12.sp, color = Color.Black)
                            }

                            Spacer(Modifier.width(12.dp))

                            // TO DATE BOX
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .background(Color(0xFFFFEBEE), RoundedCornerShape(6.dp))
                                    .clickable { openToDatePicker() }
                                    .padding(start = 10.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(toDate, fontSize = 12.sp, color = Color.Black)
                            }
                        }
                    }

                    //------------------------------------------------------------
                    //                  BRANCH / SUBPARTY / SUPPLIER / TRANSPORT
                    //------------------------------------------------------------
                    if (selectedFilter == "Branch") {
                        LazyColumn {
                            items(branchList) { item ->
                                val name = item.BranchName ?: ""
                                val isChecked = name in selectedBranches
                                CheckBoxItemStock(name, isChecked) { checked ->
                                    if (checked) selectedBranches.add(name) else selectedBranches.remove(name)
                                }
                            }
                        }
                    }

                    if (selectedFilter == "SubParty") {
                        LazyColumn {
                            items(subPartyList) { item ->
                                val name = item.SubPartyName ?: ""
                                val isChecked = name in selectedSubParty
                                CheckBoxItemStock(name, isChecked) { checked ->
                                    if (checked) selectedSubParty.add(name) else selectedSubParty.remove(name)
                                }
                            }
                        }
                    }

                    if (selectedFilter == "Supplier") {
                        LazyColumn {
                            items(supplierList) { item ->
                                val name = item.BrandName ?: ""
                                val isChecked = name in selectedSupplier
                                CheckBoxItemStock(name, isChecked) { checked ->
                                    if (checked) selectedSupplier.add(name) else selectedSupplier.remove(name)
                                }
                            }
                        }
                    }

                    if (selectedFilter == "Transport") {
                        LazyColumn {
                            items(transporterList) { item ->
                                val name = item.TransporterName ?: ""
                                val isChecked = name in selectedTransporter
                                CheckBoxItemStock(name, isChecked) { checked ->
                                    if (checked) selectedTransporter.add(name) else selectedTransporter.remove(name)
                                }
                            }
                        }
                    }

                } // Column end
            }

            Spacer(Modifier.height(24.dp))

            // ---------- FOOTER BUTTONS ----------
            Row(modifier = Modifier.fillMaxWidth()) {

                // RESET
                Button(
                    onClick = {
                        fromDate = "DD/MM/YYYY"
                        toDate = "DD/MM/YYYY"
                        selectedBranches.clear()
                        selectedSubParty.clear()
                        selectedSupplier.clear()
                        selectedTransporter.clear()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Reset", color = Color.Black)
                }

                Spacer(Modifier.width(16.dp))

                // APPLY
                Button(
                    onClick = {
                        val jsonObject = JsonObject().apply {
                            addProperty("MOBILENO", AppSharedPreferences.getInstance(context).phoneNumber)
                            addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)

                            addProperty("FROMDATE", fromDate)
                            addProperty("TODATE", toDate)

                            addProperty("SUBPARTY", selectedSubParty.getOrNull(0) ?: "")
                            addProperty("SUPPLIERS", selectedSupplier.getOrNull(0) ?: "")
                            addProperty("BRANCH", selectedBranches.getOrNull(0) ?: "")
                            addProperty("TRANSPORT", selectedTransporter.getOrNull(0) ?: "")

                            addProperty("DBNAME", "")
                            addProperty("FilterType", "NEW")
                        }

                        viewModel.fetchStockInOffice(jsonObject)
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Apply", color = Color.Black)
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}


@Composable
fun CheckBoxItemStock(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 14.sp
        )
    }
}


@Composable
fun FilterOptionStock(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = if (selected) Color(0xFF2E7D32) else Color.Black,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}






