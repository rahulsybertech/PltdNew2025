package com.syber.ssspltd.ui.view.debit_note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.syber.ssspltd.R
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.AppSharedPreferences


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebittNoteToSupplierScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {

    val hasFetched by viewModel1.hasFetched.collectAsState()

    val listState = rememberLazyListState()
    val ledgerEntries by viewModel1.ledgerReportWithBalance.collectAsState()
    val ledgerReportResult by viewModel1.ledgerReportResult.collectAsState()
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
                            Text("Ledger", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },

                    actions = {
                        if (ledgerReportResult.isNotEmpty()) {
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

                ledgerReportResult.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No Data Found")
                    }
                }

                ledgerEntries.isNotEmpty() -> {
                    // ✅ Opening Balance
                    Card(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Opening Balance", fontWeight = FontWeight.Bold)
                            ledgerEntries[0].OpeningBal?.let {
                                Text(it, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(ledgerEntries.flatMap {
                            it.LedgerReportResult ?: emptyList()
                        }) { item ->
                            LedgerCard(item)
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
                            Text("Closing Balance", color = Color.White, fontWeight = FontWeight.Bold)
                            ledgerEntries[0].ClosingBal?.let {
                                Text(it, color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                            }
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