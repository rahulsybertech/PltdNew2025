package com.syber.ssspltd.ui.view.courier_report

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.ledger.LedgerResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.AppSharedPreferences
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourierReportScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {

    val hasFetched by viewModel1.hasFetched.collectAsState()

    val listState = rememberLazyListState()
    val ledgerEntries by viewModel1.ledgerReportWithBalance.collectAsState()
    val ledgerReportResult by viewModel1.ledgerReportResult.collectAsState()
    val courierReportList by viewModel1.courierReportList.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showFilterSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    // Call API when screen first opens
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {

            addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("FROMDATE", "")
            addProperty("TODATE", "")
            addProperty("Status", "")
            add("AVGDATE", JsonNull.INSTANCE)
            add("TICK", JsonNull.INSTANCE)
            addProperty("DBNAME", "2025-2026")
            add("LEDGERTYPE", JsonNull.INSTANCE)
        }
        val jsonObject1 = JsonObject().apply {

            add("Branch", JsonArray())       // []
            add("Brand", JsonArray())        // []

            addProperty("DBNAME", "")
            addProperty("EndDate", "31/03/2026")
            addProperty("FILTERTYPE", "LEDGERREPORT")
            addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("StartDate", "01/04/2025")

            add("SubParty", JsonArray())     // []
            add("Transporter", JsonArray())  // []
        }
        viewModel1.fatchSaleReportFilter(jsonObject1)
        viewModel1.fetchCourierReport(jsonObject)
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
                        Column {
                            Text("Courier Report", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            if (ledgerReportResult.isNotEmpty()) {
                                Text(
                                    text = "${ledgerEntries.get(0).DefaultStartDate ?: "--"} to ${ledgerEntries.get(0).DefaultEndDate ?: "--"}",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }

                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },

                    actions = {
                        if (courierReportList.isNotEmpty()) {
                            IconButton(onClick = { showFilterSheet = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.filter),
                                    contentDescription = "Filter",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
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
    )
    {
        padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFF5F5F5))
                .fillMaxSize()
        ) {

            when {
                isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF008080))
                    }
                }

                courierReportList.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No Data Found")
                    }
                }

                courierReportList.isNotEmpty() -> {
                    // ✅ Opening Balance
                    Card(
                        modifier = Modifier.padding(5.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                    }

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                            items(courierReportList) { item ->
                                LedgerCard(item)
                            }
                    }
                }
            }

            if (showFilterSheet) {
                FilterBottomSheet1(
                    viewModel = viewModel1,
                    sheetState = sheetState,
                    onDismiss = { showFilterSheet = false },
                    onApply = { showFilterSheet = false }
                )
            }
        }

    }
}

@Composable
fun LedgerCard(entry: LedgerResponse.CourierReportItem) {
    val context = LocalContext.current
    val valueStyle = MaterialTheme.typography.bodySmall
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("BillDate  : ${entry.CourierName ?: "-"}", style = valueStyle)
            Text("AccountID : ${entry.Station ?: "-"}", style = valueStyle)
            Text("Date : ${entry.Date ?: "-"}", style = valueStyle)
            Text("Sale Bill No. : ${entry.SaleBillNumber ?: "-"}", style = valueStyle)
            Text("Courier No. : ${entry.CourierNo ?: "-"}", style = valueStyle)
            //  Text("BLDes     : ${entry.BLDescription ?: "-"}", style = valueStyle)
        //    entry.BLDescription?.let { entry.PDFPath?.let { it1 -> BLDesText(it , it1,context) } }
        /*    Column( modifier = Modifier.fillMaxWidth()) {
                if(entry.CreditAmt?.isNotEmpty() == true){
                    Text(
                        "Credit: ${entry.CreditAmt}",
                        color = Color(0xFF2E7D32), // green for credit
                        fontWeight = FontWeight.Medium
                    )
                }
                if(entry.DebitAmt?.isNotEmpty() == true){
                    Text(
                        "Debit: ${entry.DebitAmt}",
                        color = Color(0xFFD32F2F), // red for debit
                        fontWeight = FontWeight.Medium
                    )
                }

            }
            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Balance: ${entry.Balance}", color = Color(0xFF1A237E), fontWeight = FontWeight.Medium)
            }*/
        }
    }
}
@Composable
fun BLDesText(
    blDescription: String?,
    pdfUrl: String?,
    context: Context
) {
    val valueStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )

    val isPdfAvailable = !pdfUrl.isNullOrEmpty()

    Text(
        text = "BLDes     : ${blDescription ?: "-"}",
        style = valueStyle.copy(
            textDecoration = if (isPdfAvailable)
                TextDecoration.Underline
            else
                TextDecoration.None,
            color = if (isPdfAvailable)
                Color.Blue
            else
                Color.Black
        ),
        modifier = if (isPdfAvailable) {
            Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(Uri.parse(pdfUrl), "application/pdf")
                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                }
                context.startActivity(intent)
            }
        } else {
            Modifier // no click
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet1(
    viewModel: AuthViewModel,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {

    var selectedFilter by remember { mutableStateOf("Date") }

    val adjustmentType by viewModel.adjustmentType.collectAsState()
    val accountType by viewModel.accountType.collectAsState()
    val entryType by viewModel.entryType.collectAsState()
    val branchList by viewModel.branch.collectAsState()
    val subPartyList by viewModel.subParty.collectAsState()
    val supplierList by viewModel.supplier.collectAsState()
    val transporterList by viewModel.transporter.collectAsState()

    val selectedBranches = remember { mutableStateListOf<String>() }
    val adjustment = remember { mutableStateListOf<String>() }
    val account = remember { mutableStateListOf<String>() }
    val entry = remember { mutableStateListOf<String>() }
    val selectedSubParty = remember { mutableStateListOf<String>() }
    val selectedSupplier = remember { mutableStateListOf<String>() }
    val selectedTransporter = remember { mutableStateListOf<String>() }

    // -------------------- DATE STATES --------------------
    val context = LocalContext.current
    var fromDate by remember { mutableStateOf("DD/MM/YYYY") }
    var toDate by remember { mutableStateOf("DD/MM/YYYY") }

    val financialYearStart = remember {
        Calendar.getInstance().apply {
            set(2025, Calendar.APRIL, 1)
        }
    }

    val financialYearEnd = remember {
        Calendar.getInstance().apply {
            set(2026, Calendar.MARCH, 31)
        }
    }

    fun formatDate(cal: Calendar): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun parseDate(date: String): Calendar {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return Calendar.getInstance().apply {
            time = sdf.parse(date)!!
        }
    }



    val calendar = Calendar.getInstance()

    fun openFromDatePicker() {
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedCal = Calendar.getInstance().apply {
                    set(year, month, day)
                }

                fromDate = formatDate(selectedCal)

                // Reset ToDate if invalid
                if (toDate != "DD/MM/YYYY") {
                    val toCal = parseDate(toDate)
                    if (toCal.before(selectedCal)) {
                        toDate = "DD/MM/YYYY"
                    }
                }
            },
            financialYearStart.get(Calendar.YEAR),
            financialYearStart.get(Calendar.MONTH),
            financialYearStart.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.datePicker.minDate = financialYearStart.timeInMillis
        datePicker.datePicker.maxDate = financialYearEnd.timeInMillis

        datePicker.show()
    }


    fun openToDatePicker() {
        if (fromDate == "DD/MM/YYYY") {
            Toast.makeText(context, "Please select From Date first", Toast.LENGTH_SHORT).show()
            return
        }

        val fromCal = parseDate(fromDate)

        val datePicker = DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedCal = Calendar.getInstance().apply {
                    set(year, month, day)
                }
                toDate = formatDate(selectedCal)
            },
            fromCal.get(Calendar.YEAR),
            fromCal.get(Calendar.MONTH),
            fromCal.get(Calendar.DAY_OF_MONTH)
        )

        // 🔒 IMPORTANT RULES
        datePicker.datePicker.minDate = fromCal.timeInMillis
        datePicker.datePicker.maxDate = financialYearEnd.timeInMillis

        datePicker.show()
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
                        .weight(1f)
                        .padding(end = 12.dp)
                ) {
                    listOf("Date", "Adjustment", "Entry", "Account",).forEachIndexed { index, title ->
                        FilterOption(
                            title = title,
                            selected = selectedFilter == title,
                            onClick = { selectedFilter = title }
                        )
                        if (index != 4) Divider()
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
                        .weight(1.5f)
                        .padding(start = 12.dp)
                ) {

                    //------------------------------------------------------------
                    //                      DATE FILTER
                    //------------------------------------------------------------
                    if (selectedFilter == "Date") {

                        Text("From date", fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                        Text("To date", fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                        Spacer(Modifier.height(12.dp))

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
                                Text(fromDate, fontSize = 12.sp, color = Color.Green)
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
                                Text(toDate, fontSize = 12.sp, color = Color.Green)
                            }
                        }
                    }

                    //------------------------------------------------------------
                    //                  BRANCH / SUBPARTY / SUPPLIER / TRANSPORT
                    //------------------------------------------------------------
                    if (selectedFilter == "Adjustment") {
                        LazyColumn {
                            items(adjustmentType) { item ->
                                val name = item.AdjustmentName ?: ""
                                val isChecked = name in selectedBranches
                                CheckBoxItem(name, isChecked) { checked ->
                                    if (checked) selectedBranches.add(name) else selectedBranches.remove(name)
                                }
                            }
                        }
                    }

                    if (selectedFilter == "Entry") {
                        LazyColumn {
                            items(entryType) { item ->
                                val name = item.EntryTypeName ?: ""
                                val isChecked = name in selectedSubParty
                                CheckBoxItem(name, isChecked) { checked ->
                                    if (checked) selectedSubParty.add(name) else selectedSubParty.remove(name)
                                }
                            }
                        }
                    }

                    if (selectedFilter == "Account") {
                        LazyColumn {
                            items(accountType) { item ->
                                val name = item.AccountTypeName ?: ""
                                val isChecked = name in selectedSupplier
                                CheckBoxItem(name, isChecked) { checked ->
                                    if (checked) selectedSupplier.add(name) else selectedSupplier.remove(name)
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
                        /*          {"PARTYCODE":"DL3827","FROMDATE":"01/09/2025","TODATE":"05/09/2025","Status":"DEBIT","AVGDATE":"null","TICK":"1","DBNAME":"","LEDGERTYPE":"JOURNAL,"}*/

                        val jsonObject = JsonObject().apply {
                            addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
                            addProperty("FROMDATE", fromDate)
                            addProperty("TODATE", toDate)
                            addProperty("Status", entry.getOrNull(0) ?: "")
                            addProperty("LEDGERTYPE", account.getOrNull(0) ?: "")
                            addProperty("DBNAME", "")
                            addProperty("TICK", account.getOrNull(0) ?: "null")
                        }

                        viewModel.fetchLedgerReport(jsonObject)
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
fun CheckBoxItem(
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
fun FilterOption(
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