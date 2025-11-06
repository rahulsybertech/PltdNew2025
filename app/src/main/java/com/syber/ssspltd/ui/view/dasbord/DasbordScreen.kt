package com.syber.ssspltd.ui.view.dasbord


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.syber.ssspltd.data.model.dasbord.Account
import com.syber.ssspltd.data.model.dasbord.InterestDiscountDetails
import com.syber.ssspltd.data.model.dasbord.PaymentDashboardResponse
import com.syber.ssspltd.data.model.dasbord.PendingOrderDetail
import com.syber.ssspltd.data.model.dasbord.ResponseModel
import com.syber.ssspltd.data.model.dasbord.StockinOfficeDetail
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.utils.AppSharedPreferences
import net.simplifiedcoding.data.network.Resource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDashboardScreen(navController: NavController,
                           viewModel1: AuthViewModel,

) {
    val dashboardDetailGraphResponse by viewModel1.dashboardDetailGraphResponse.observeAsState(Resource.Loading)
    val dashboardGraph by viewModel1.dashboardDetailGraphResponse.observeAsState()
    val balance by viewModel1.getDashboardDetailsBalanceTillDate.observeAsState()
    val discount by viewModel1.getDashboardDetailsInteresDiscount.observeAsState()
    val stock by viewModel1.getdashboardDetailsStockInOffice.observeAsState()
    val pendingOrder by viewModel1.dashboardDetailsPendingOrder.observeAsState()
    var apiCalled by remember { mutableStateOf(false) }
    var dashboardData by remember { mutableStateOf<PaymentDashboardResponse?>(null) }
    val context = LocalContext.current  // ✅ Get the context

    var expanded by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf<Account?>(null) }
    var accountList by remember { mutableStateOf(listOf<Account>()) }
    var balanceAmount by remember { mutableStateOf(0.0) }
    var isLoading by remember { mutableStateOf(false) }

   /* LaunchedEffect(dashboardDetailGraphResponse) {
        if (!apiCalled && dashboardDetailGraphResponse is Resource.Loading) {
            apiCalled = true
            val jsonObject = JsonObject().apply {
                addProperty("MOBILENO", AppSharedPreferences.getInstance(context).phoneNumber)
                addProperty("ACCOUNTID", AppSharedPreferences.getInstance(context).isPartyCode)
                addProperty("DBNAME", "")
            }
            viewModel1.dashboardDetailGraphReq(jsonObject)
        }
    }*/

    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {
            addProperty("MOBILENO", AppSharedPreferences.getInstance(context).phoneNumber)
            addProperty("ACCOUNTID", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("DBNAME", "2025-2026")
       //     addProperty("DBNAME", "discount")
        }
        val jsonObjectBal = JsonObject().apply {

            addProperty("MOBILENO", AppSharedPreferences.getInstance(context).phoneNumber)
            addProperty("ACCOUNTID", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("PartyID", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("DBNAME", "2025-2026")
        }
        viewModel1.fetchAllDashboardApis(jsonObject,jsonObjectBal)
    }

    // Observe each LiveData and update UI
    dashboardGraph?.let { resource ->
        when (resource) {
            is Resource.Loading -> Log.d("DashboardAPI", "Balance Loading...")
            is Resource.Success -> Log.d("DashboardAPI", "Balance Success: ${resource.value}")
            is Resource.Failure -> Log.e("DashboardAPI", "Balance Error: ${resource.errorBody}")
        }}
    balance?.let { resource ->
        when (resource) {
            is Resource.Loading -> Log.d("DashboardAPI", "balance Loading...")
            is Resource.Success -> Log.d("DashboardAPI", "balance Success: ${resource.value}")
            is Resource.Failure -> Log.e("DashboardAPI", "balance Error: ${resource.errorBody}")
        } }
    discount?.let {  resource ->
        when (resource) {
            is Resource.Loading -> Log.d("DashboardAPI", "Discount Loading...")
            is Resource.Success -> Log.d("DashboardAPI", "Discount Success: ${resource.value}")
            is Resource.Failure -> Log.e("DashboardAPI", "Discount Error: ${resource.errorBody}")
        } }
    stock?.let {  resource ->
        when (resource) {
            is Resource.Loading -> Log.d("DashboardAPI", "Stock Loading...")
            is Resource.Success -> Log.d("DashboardAPI", "Stock Success: ${resource.value}")
            is Resource.Failure -> Log.e("DashboardAPI", "Stock Error: ${resource.errorBody}")
        } }
    pendingOrder?.let {  resource ->
        when (resource) {
            is Resource.Loading -> Log.d("DashboardAPI", "Pending Order Loading...")
            is Resource.Success -> Log.d("DashboardAPI", "Pending Order Success: ${resource.value}")
            is Resource.Failure -> Log.e("DashboardAPI", "Pending Order Error: ${resource.errorBody}")
        }}

    when (val response = dashboardDetailGraphResponse) {
        is Resource.Success<*> -> {
            val jsonResponse: PaymentDashboardResponse = Gson().fromJson(response.value.toString(), PaymentDashboardResponse::class.java)
            dashboardData = jsonResponse
        //    Toast.makeText(LocalContext.current, "Rahul", Toast.LENGTH_SHORT).show()
        }
        is Resource.Failure -> {
       //     Toast.makeText(LocalContext.current, response.errorBody, Toast.LENGTH_SHORT).show()
        }
        Resource.Loading -> {
            // Optional: Keep showing progress bar while loading
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
                        Text(
                            text = "Payment Dashboard",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { padding ->

        dashboardData?.let { data ->   // 👈 Only draw when we have data

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // ✅ Stats
                Text("Avg Days : ${data.avgDays}", style = MaterialTheme.typography.titleMedium)
                Text("Total Limit : ${data.totalLimit}", style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(16.dp))

                // ✅ Pie Chart
                PaymentPieChart(
                    availableLimit = data.availableLimit,
                    stockInOffice = data.stockInOffice
                )

                Spacer(Modifier.height(16.dp))

                // ✅ List Details
                Text("List Details", color = Color.Blue, style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(8.dp))
                val gson = Gson()
                var pendingOrdersList: List<PendingOrderDetail> = emptyList()
                var pendingOrdersListOther: List<PendingOrderDetail> = emptyList()
                var interestDiscountList: List<InterestDiscountDetails> = emptyList()
                var stockinOfficeList: List<StockinOfficeDetail> = emptyList()

                if (stock is Resource.Success) {
                    val jsonObject = (stock as Resource.Success<JsonObject>).value // ✅ This is the JsonObject from API
                    val jsonString = jsonObject.toString()
                    val response = gson.fromJson(jsonString, ResponseModel::class.java)
                    stockinOfficeList = response.StockinOfficeDetail ?: emptyList()
                } else if (balance is Resource.Failure) {
                    Log.e("PaymentDetails", "Error fetching data: ${(balance as Resource.Failure).errorBody}")
                }

            /*    if (pendingOrder is Resource.Success) {
                    val jsonObject = (stock as Resource.Success<JsonObject>).value // ✅ This is the JsonObject from API
                    val jsonString = jsonObject.toString()
                    val response = gson.fromJson(jsonString, ResponseModel::class.java)
                    stockinOfficeList = response.StockinOfficeDetail ?: emptyList()
                } else if (balance is Resource.Failure) {
                    Log.e("PaymentDetails", "Error fetching data: ${(balance as Resource.Failure).errorBody}")
                }*/

                if (discount is Resource.Success) {
                    val jsonObject = (discount as Resource.Success<JsonObject>).value // ✅ This is the JsonObject from API
                    val jsonString = jsonObject.toString()
                    val response = gson.fromJson(jsonString, ResponseModel::class.java)
                    interestDiscountList = response.InterestDiscountDetails ?: emptyList()
                } else if (balance is Resource.Failure) {
                    Log.e("PaymentDetails", "Error fetching data: ${(balance as Resource.Failure).errorBody}")
                }

                if (balance is Resource.Success) {
                    val jsonObject = (balance as Resource.Success<JsonObject>).value // ✅ This is the JsonObject from API
                    val jsonString = jsonObject.toString()
                    val response = gson.fromJson(jsonString, ResponseModel::class.java)
                    pendingOrdersList = response.PendingOrderDetails ?: emptyList()
                } else if (balance is Resource.Failure) {
                    Log.e("PaymentDetails", "Error fetching data: ${(balance as Resource.Failure).errorBody}")
                }

                if (pendingOrder is Resource.Success) {
                    val jsonObject = (pendingOrder as Resource.Success<JsonObject>).value // ✅ This is the JsonObject from API
                    val jsonString = jsonObject.toString()
                    val response = gson.fromJson(jsonString, ResponseModel::class.java)
                    pendingOrdersListOther = response.PendingOrderDetails ?: emptyList()
                } else if (balance is Resource.Failure) {
                    Log.e("PaymentDetails", "Error fetching data: ${(balance as Resource.Failure).errorBody}")
                }



                PaymentDetailsTable(
                    totalLimit = data.totalLimit.replace(",", "").toDoubleOrNull() ?: 0.0,
                    balanceTillDate = data.balanceTillDate.replace(",", "").toDoubleOrNull() ?: 0.0,
                    discount = data.discount.replace(",", "").toDoubleOrNull() ?: 0.0,
                    stockInOffice = data.stockInOffice.replace(",", "").toDoubleOrNull() ?: 0.0,
                    pendingOrder = data.pendingOrder.replace(",", "").toDoubleOrNull() ?: 0.0
               ,pendingOrdersList = pendingOrdersList,interestList=interestDiscountList,stockList=stockinOfficeList,pendingOrderNew=pendingOrdersListOther
                )

                Spacer(Modifier.height(16.dp))


                // ✅ Footer
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF008080))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Available Limit", style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
                    Text(
                        text = data.availableLimit,
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                }
            }
        } ?: run {
            // Optional: show a progress indicator or placeholder while loading
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

}







@Composable
fun PaymentPieChart(
    availableLimit: String,
    stockInOffice: String
) {
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                val entries = listOf(
                    PieEntry(availableLimit.toSafeFloat(), "Available Limit"),
                    PieEntry(stockInOffice.toSafeFloat(), "Stock in Office")
                )

                val dataSet = PieDataSet(entries, "").apply {
                    colors = listOf(
                        androidx.compose.ui.graphics.Color(0xFF008080).toArgb(), // Green
                        androidx.compose.ui.graphics.Color.Gray.toArgb()         // Gray
                    )
                    valueTextSize = 14f
                    valueTextColor = android.graphics.Color.RED
                }

                data = PieData(dataSet)

                description.isEnabled = false
                legend.isEnabled = true
                setUsePercentValues(true)

                invalidate()
            }
        },
        modifier = Modifier.size(250.dp)
    )
}



fun String.toSafeFloat(): Float {
    return this.replace(",", "").toFloatOrNull() ?: 0f
}




@Composable
fun PaymentDetailsTable(
    totalLimit: Double,
    balanceTillDate: Double,
    discount: Double,
    stockInOffice: Double,
    pendingOrder: Double,
    pendingOrdersList: List<PendingOrderDetail> = emptyList(),
    interestList: List<InterestDiscountDetails> = emptyList(),
    stockList: List<StockinOfficeDetail> = emptyList(),
    pendingOrderNew: List<PendingOrderDetail> = emptyList(),
) {
    Column(Modifier.fillMaxWidth()) {
        PaymentDetailRow("Total Limit", totalLimit, Color(0xFF2E7D32))
        PaymentDetailRow("Balance Till Date (DR)", balanceTillDate, Color.Red)
        pendingOrdersList.forEach{
                order ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    /* .border(1.dp, Color.LightGray)*/
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = order.CompanyCode,
                    style = TextStyle(
                        color = Color(0xFF008080),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )

                Text(
                    text = order.Amount.format(12.1),
                    style = TextStyle(
                        color = Color(0xFF008080),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )  )//
            }
        }


        PaymentDetailRow("Discount (CR)", discount, Color(0xFF2E7D32))



        interestList.forEach{interest->
            Row(modifier = Modifier.fillMaxWidth()/* .border(1.dp, Color.LightGray)*/.padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = interest.CompanyCode,
                    style = TextStyle(
                        color = Color(0xFF008080),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )

                Text(
                    text = interest.Amount.format(12.1),
                    style = TextStyle(
                        color = Color(0xFF008080),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )  )//
            }

        }
        PaymentDetailRow("Stock in Office", stockInOffice, Color.Red)
        stockList.forEach{
            stock->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    /* .border(1.dp, Color.LightGray)*/
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stock.CompanyCode,
                    style = TextStyle(
                        color = Color(0xFF008080),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )

                Text(
                    text = stock.Amount.format(12.1),
                    style = TextStyle(
                        color = Color(0xFF008080),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )  )//
            }
        }


        PaymentDetailRow("Pending Order", pendingOrder, Color.Red)
        pendingOrderNew.forEach{
                order->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    /* .border(1.dp, Color.LightGray)*/
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = order.CompanyCode,
                    style = TextStyle(
                        color = Color(0xFF008080),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )

                Text(
                    text = order.Amount.format(12.1),
                    style = TextStyle(
                        color = Color(0xFF008080),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )  )//
            }
        }
    }
}


@Composable
fun PaymentDetailRow(label: String, amount: Double, amountColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(
            text = "%,.2f".format(amount),
            style = MaterialTheme.typography.bodyMedium.copy(color = amountColor)
        )
    }
}

