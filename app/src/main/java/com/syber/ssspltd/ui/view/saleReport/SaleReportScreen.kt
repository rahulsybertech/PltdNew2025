package com.syber.ssspltd.ui.view.saleReport

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.syber.ssspltd.R
import com.syber.ssspltd.out.AuthViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleReportScreen(navController: NavController, viewModel1: AuthViewModel) {

    val listState = rememberLazyListState()
    val saleItems by viewModel1.saleItems.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()
    var showFilterDialog by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Date") }




    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex >= saleItems.lastIndex - 2 && !isLoading) {


                    val jsonObject = JsonObject().apply {
                        addProperty("MOBILENO", "7290087642")
                        addProperty("PARTYCODE", "DL3331")
                        add("FROMDATE", JsonNull.INSTANCE)
                        add("TODATE", JsonNull.INSTANCE)
                        add("SUBPARTY", JsonNull.INSTANCE)
                        add("SUPPLIERS", JsonNull.INSTANCE)
                        add("BRANCH", JsonNull.INSTANCE)
                        add("TRANSPORT", JsonNull.INSTANCE)
                        addProperty("DBNAME", "")
                        addProperty("FilterType", "NEW")
                    }
                    viewModel1.fetchSaleReport(jsonObject)
                }
            }
    }

    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                shadowElevation = 8.dp,
                color = Color(0xFF008080)
            ) {
                TopAppBar(
                    title = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "SALE REPORT",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White
                            )
                            if (saleItems.isNotEmpty()) {
                                Text(
                                    text = "${saleItems[0].DefaultStartDate ?: "--"} to ${saleItems[0].DefaultEndDate ?: "--"}",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }
                    },

                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },

                    // 👉 Add this block for filter icon
                    actions = {
                        IconButton(onClick = {         showFilterSheet = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.filter),
                                contentDescription = "Filter",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )

            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            state = listState
        ) {

            // Show list items
            items(saleItems) { sale ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Show header once
                        SaleHeader(sale.SaleReportResult)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Loop through each SaleReportResult
                        sale.SaleReportResult?.forEach { report ->
                            PurchaseTable(report.SaleReportSecondaryData)

                            Spacer(modifier = Modifier.height(12.dp)) // space between tables
                        }
                    }

                }
            }

            // Loading indicator
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // Empty list fallback
            if (saleItems.isEmpty() && !isLoading) {
                item {
                    Text(
                        text = "No sales found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        if (showFilterSheet) {
            FilterBottomSheet(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("Date") }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }, // modern styling
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
                    text = "Filter",fontSize = 12.sp,
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
                    Modifier.weight(1f).padding(end = 12.dp)
                ) {
                    FilterOption(
                        title = "Date",
                        selected = selectedFilter == "Date",
                        onClick = { selectedFilter = "Date" }
                    )
                    Divider()

                    FilterOption(
                        title = "Adjustment",
                        selected = selectedFilter == "Adjustment",
                        onClick = { selectedFilter = "Adjustment" }
                    )
                    Divider()

                    FilterOption(
                        title = "Entry",
                        selected = selectedFilter == "Entry",
                        onClick = { selectedFilter = "Entry" }
                    )
                    Divider()

                    FilterOption(
                        title = "Account",
                        selected = selectedFilter == "Account",
                        onClick = { selectedFilter = "Account" }
                    )
                    Divider()
                }


                // Vertical Divider
                Box(
                    modifier = Modifier
                        .width(2.dp)
                    //    .fillMaxHeight()
                        .height(IntrinsicSize.Max)
                        .background(Color(0xFF1565C0))
                )
                // RIGHT SIDE (Dynamic content based on selectedFilter)
                Column(
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(start = 12.dp)
                ) {
                    when (selectedFilter) {
                        "Date" -> {
                            Text(
                                "From date",
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "To date",
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                            Spacer(Modifier.height(12.dp))

                            Row {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .background(Color(0xFFFFEBEE), RoundedCornerShape(6.dp))
                                        .padding(start = 10.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) { Text("DD/MM/YYYY", fontSize = 12.sp, color = Color.Green) } // default date in green

                                Spacer(Modifier.width(12.dp))

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .background(Color(0xFFFFEBEE), RoundedCornerShape(6.dp))
                                        .padding(start = 10.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) { Text("DD/MM/YYYY", fontSize = 12.sp, color = Color.Green) } // default date in green
                            }
                        }

                        "Adjustment" -> {
                            Text("Adjustment List", modifier = Modifier.padding(8.dp))
                            // TODO: Show your dynamic adjustment items here
                        }

                        "Entry" -> {
                            Text("Entry List", modifier = Modifier.padding(8.dp))
                            // TODO: Show entry items dynamically
                        }

                        "Account" -> {
                            Text("Account Filters", modifier = Modifier.padding(8.dp))
                            // TODO: Show account list dynamically
                        }
                    }
                }

            }

            Spacer(Modifier.height(24.dp))

            // ---------- FOOTER BUTTONS ----------
            Row(modifier = Modifier.fillMaxWidth()) {

                // RESET
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Reset", color = Color.Black)
                }

                Spacer(Modifier.width(16.dp))

                // APPLY
                Button(
                    onClick = onApply,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
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
@Composable
fun FilterOptionWithCount(title: String, count: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 6.dp)
        )

        Box(
            modifier = Modifier
                .size(25.dp)
                .background(Color.Gray, CircleShape)
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            Text("$count")
        }
    }
}


@Composable
fun FilterOptionChip(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}








