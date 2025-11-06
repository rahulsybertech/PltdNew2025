package com.syber.ssspltd.ui.view.ledger

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.ledger.LedgerResponse
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {
    val listState = rememberLazyListState()
    val ledgerEntries by viewModel1.ledgerReportWithBalance.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()

    // Call API when screen first opens
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {
            addProperty("PARTYCODE", "DL3331")
            addProperty("FROMDATE", "")
            addProperty("TODATE", "")
            addProperty("Status", "")
            add("AVGDATE", JsonNull.INSTANCE)
            add("TICK", JsonNull.INSTANCE)
            addProperty("DBNAME", "2025-2026")
            add("LEDGERTYPE", JsonNull.INSTANCE)
        }
        viewModel1.fetchLedgerReport(jsonObject)
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
                            if (ledgerEntries.isNotEmpty()) {
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
                        IconButton(onClick = { navController.navigate(Screen.FilterScreen.route) }) {
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
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF008080))
                }
            } else {
                if (ledgerEntries.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No Data Found")
                    }
                } else {
                    // Opening Balance Card
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
                            ledgerEntries.get(0).OpeningBal?.let { Text(it, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold) }
                        }
                    }

                    // Ledger List
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(ledgerEntries.flatMap { it.LedgerReportResult ?: emptyList() }) { item ->
                            LedgerCard(item)
                        }
                    }

                    // Closing Balance Card
                    Card(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF008080))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Closing Balance", color = Color.White, fontWeight = FontWeight.Bold)
                            ledgerEntries.get(0).ClosingBal?.let { Text(it, color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LedgerCard(entry: LedgerResponse.LedgerReportItem) {
    val valueStyle = MaterialTheme.typography.bodySmall
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("BillDate  : ${entry.BillDate ?: "-"}", style = valueStyle)
            Text("AccountID : ${entry.AccountID ?: "-"}", style = valueStyle)
            Text("BLDes     : ${entry.BLDescription ?: "-"}", style = valueStyle)
            Column( modifier = Modifier.fillMaxWidth()) {
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
            }
        }
    }
}


