package com.syber.ssspltd.ui.view.debit_note

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.google.gson.JsonObject
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.debitNoteToCustomer.DebitNoteToCustomerReportResult
import com.syber.ssspltd.data.model.debitNoteToCustomer.ItemsDetailsDatum
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.AppSharedPreferences
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebitNoteToCustomerScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {

    val debitNoteToCustomerReportList by viewModel1.debitNoteToCustomerReportList.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showFilterSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    // Call API when screen first opens
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {
/*            {"MOBILENO":"8709536827","PARTYCODE":"DL17747","DBNAME":""}*/
            addProperty("MOBILENO", AppSharedPreferences.getInstance(context).mobileNumber)
            addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("DBNAME", "")

        }
        viewModel1.fatchdebitNoteToCustomerReportList(jsonObject)
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
                            Text("Debit Note to Customer", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            if (debitNoteToCustomerReportList.isNotEmpty()) {
                             /*   Text(
                                    text = "${ledgerEntries.get(0).DefaultStartDate ?: "--"} to ${ledgerEntries.get(0).DefaultEndDate ?: "--"}",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )*/
                            }

                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },

                    actions = {
                        if (debitNoteToCustomerReportList.isNotEmpty()) {
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
    ) { padding ->
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

                debitNoteToCustomerReportList.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No Data Found")
                    }
                }

                debitNoteToCustomerReportList.isNotEmpty() -> {
                    // ✅ Opening Balance
                    Card(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                     /*   Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Opening Balance", fontWeight = FontWeight.Bold)
                            debitNoteToCustomerReportList[0].netAmt?.let {
                                Text(it, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                            }
                        }*/
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = debitNoteToCustomerReportList,
                       /*     key = { it.id }*/ // use unique id if available
                        ) { item ->
                            LedgerCard11(item)
                        }
                    }

                    // ✅ Closing Balance
                    Card(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF008080))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                     /*       Text("Closing Balance", color = Color.White, fontWeight = FontWeight.Bold)
                            ledgerEntries[0].ClosingBal?.let {
                                Text(it, color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                            }*/
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
fun LedgerCard11(entry: DebitNoteToCustomerReportResult) {
    val context = LocalContext.current
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ---------- Header Info ----------
             entry?.let { it1 -> pdfView(it1, context) }


            Text("Bill Ref No : ${entry.saleBillNo ?: "-"}")
            Text("Date : ${entry.date ?: "-"}")
            Text("Supplier : ${entry.supplierName ?: "-"}")

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Net Amount : ₹ ${entry.netAmt ?: "0.00"}",
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(8.dp))
            Divider()
            Spacer(Modifier.height(8.dp))

            // ---------- Expand / Collapse Header ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Item Details",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Medium
                )

                Icon(
                    imageVector = if (expanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            // ---------- Nested List ----------
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(8.dp))

                    entry.itemsDetailsData?.forEach { item ->
                        ItemRow(item)
                    }
                }
            }
        }
    }
}


@Composable
fun ItemRow(item: ItemsDetailsDatum) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.item ?: "-",
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "₹ ${item.netAmt ?: "0.00"}",
            fontWeight = FontWeight.Medium
        )
    }
}



@Composable
fun pdfView(
    it1: DebitNoteToCustomerReportResult,
    context: Context
) {
    val valueStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )

    val isPdfAvailable = !it1.pdfPath.isNullOrEmpty()
  //  Text("Invoice No : ${entry.billNo ?: "-"}")
    Text(
        text = "Invoice No : ${it1.billNo ?: "-"}",
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
                    setDataAndType(Uri.parse(it1.pdfPath), "application/pdf")
                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                }
                context.startActivity(intent)
            }
        } else {
            Modifier // no click
        }
    )
}






