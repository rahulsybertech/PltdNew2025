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
import androidx.compose.material.Divider
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.syber.ssspltd.data.model.debitNoteToCustomer.ItemsDetailsDatum
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
            addProperty("DBNAME", "")
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
                     /*   if (ledgerEntries.isNotEmpty()) {
                            IconButton(onClick = {         showFilterSheet = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.filter),
                                    contentDescription = "Filter",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }*/

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

        /*    if (showFilterSheet) {
                FilterBottomSheet1(
                    viewModel = viewModel1,
                    sheetState = sheetState,
                    onDismiss = { showFilterSheet = false },
                    onApply = { showFilterSheet = false }
                )
            }*/
        }

    }
}


@Composable
fun SaleServiceCard(entry: SaleServiceReportItem) {
    val context = LocalContext.current
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ---------- Header Info ----------
            entry?.let { it1 -> pdfView1(it1, context) }


          //  Text("Sale Bill No : ${entry.BillNo ?: "-"}")
            Text("Date : ${entry.Date ?: "-"}")
            Text("Sub-party : ${entry.SubParty ?: "-"}")
            Text("Customer name : ${entry.CustomerName ?: "-"}")
            Text("Sub-party : ${entry.SubParty ?: "-"}")

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Net Amount : ₹ ${entry.NetAmt ?: "0.00"}",
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Medium
            )
            // ---------- Expand / Collapse Header ----------
        /*    Row(
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
            }*/

       /*     // ---------- Nested List ----------
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(8.dp))

                    entry.itemsDetailsData?.forEach { item ->
                        ItemRow(item)
                    }
                }
            }*/
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
fun pdfView1(
    it1: SaleServiceReportItem,
    context: Context
) {
    val valueStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )

    val isPdfAvailable = !it1.PDFPath.isNullOrEmpty()
    //  Text("Invoice No : ${entry.billNo ?: "-"}")
    Text(
        text = "Sale Bill No : ${it1.BillNo ?: "-"}",
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
                    setDataAndType(Uri.parse(it1.PDFPath), "application/pdf")
                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                }
                context.startActivity(intent)
            }
        } else {
            Modifier // no click
        }
    )
}
