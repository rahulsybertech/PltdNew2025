package com.syber.ssspltd.ui.view.saleservice

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.saleservice.SaleServiceReportItem
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.ledger.FilterBottomSheet1
import com.syber.ssspltd.utils.AppSharedPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleServicesScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {

    val hasFetched by viewModel1.hasFetched.collectAsState()

    val listState = rememberLazyListState()
    val ledgerEntries by viewModel1.saleServices.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showFilterSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    // Call API when screen first opens
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {
            addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("MOBILENO", AppSharedPreferences.getInstance(context).mobileNumber)
            addProperty("DBNAME", "2025-2026")
        }
        viewModel1.fatchSaleServices(jsonObject)
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
                            Text("Sale Services", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                          /*  if (ledgerEntries.isNotEmpty()) {
                                Text(
                                    text = "${ledgerEntries.get(0).DefaultStartDate ?: "--"} to ${ledgerEntries.get(0).DefaultEndDate ?: "--"}",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }*/

                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        if (ledgerEntries.isNotEmpty()) {
                            IconButton(onClick = {         showFilterSheet = true }) {
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

                hasFetched && ledgerEntries.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No Data Found")
                    }
                }

                ledgerEntries.isNotEmpty() -> {
                    // ✅ Opening Balance
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ){
                        items(ledgerEntries) { item ->
                            SaleServiceCard(item)
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
fun SaleServiceCard(entry: SaleServiceReportItem) {
    val context = LocalContext.current
    val valueStyle = MaterialTheme.typography.bodySmall
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("BillDate  : ${entry.Date ?: "-"}", style = valueStyle)
            Text("Sub party : ${entry.SubParty ?: "-"}", style = valueStyle)
            //  Text("BLDes     : ${entry.BLDescription ?: "-"}", style = valueStyle)
            Spacer(Modifier.height(8.dp))

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