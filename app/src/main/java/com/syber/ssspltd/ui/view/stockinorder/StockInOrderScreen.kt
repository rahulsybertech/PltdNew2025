package com.syber.ssspltd.ui.view.stockinorder

import android.app.Activity
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
                FilterBottomSheet(viewModel=viewModel1,
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






