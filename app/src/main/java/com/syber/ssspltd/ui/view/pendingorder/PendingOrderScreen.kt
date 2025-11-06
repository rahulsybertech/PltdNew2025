package com.syber.ssspltd.ui.view.pendingorder

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.syber.ssspltd.data.model.Order
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.ToolbarUtils
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.syber.ssspltd.data.model.OrderData


@Composable
fun PendingOrderList(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {
    val context = LocalContext.current
    val activity = (context as? Activity)

/*    val orders = listOf(
        Order(
            saleParty = "DL3331 WRONG TEST SYBER PARTY A/C",
            item = "BABY POTTY SEAT",
            type = "BORA",
            subParty = "MUM23915 SUB PARTY 2",
            orderNo = "24-25/DLO 28405",
            orderDate = "31/03/2025 03:46:18 PM",
            amount = 33.0,
            qty = 1
        ),
        Order(
            saleParty = "DL3331 WRONG TEST SYBER PARTY A/C",
            item = "A4 75GSM PAPER",
            type = "PETI",
            subParty = "SELF",
            orderNo = "24-25/DLO 27020",
            orderDate = "12/03/2025 10:24:49 AM",
            amount = 60000.0,
            qty = 1
        )
    )*/

    val listState = rememberLazyListState()
    val orderList by viewModel1.pendingOrder.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()

    // Call API when screen first opens
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {
            addProperty("AccountID", "DL3331")
            addProperty("OrderStatus", "HOLD")
        }
        viewModel1.fetchPendingOrder(jsonObject)
    }
    Scaffold(
        topBar = {
            ToolbarUtils.CustomToolbar(
                title = "Pending Orders",
                onBackClick = { navController.popBackStack() },
                themeColors = themeColors
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Confirm & Hold Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { /* Confirm logic */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Confirm")
                        Spacer(Modifier.width(6.dp))
                        Text("Confirm")
                    }
                    OutlinedButton(
                        onClick = { /* Hold logic */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Hold")
                        Spacer(Modifier.width(6.dp))
                        Text("Hold")
                    }
                }
            }

            items(orderList) { order ->
                OrderCard(
                    order = order, // ✅ pass the single item
                    onViewImage = { selectedOrder ->
                        Toast.makeText(
                            context,
                            "View image for ${selectedOrder.ItemName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

        }
    }
}
@Composable
fun OrderCard(order: OrderData, onViewImage: (OrderData) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9))
                    .padding(12.dp)
            ) {
                order.SaleParty?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            // Details
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Item: ${order.ItemName}", style = MaterialTheme.typography.bodyLarge)
                Text("Type: ${order.OrderType}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text("Sub Party: ${order.SubParty}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text("Date: ${order.OrderDate}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text("Qty: ${order.Qty}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AssistChip(
                        onClick = {},
                        label = { order.OrderNo?.let { Text(it, color = Color.Red) } },
                        leadingIcon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) }
                    )

                /*    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Date", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        order.OrderDate?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
                    }*/

               /*     Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Qty", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        AssistChip(
                            onClick = {},
                            label = { Text("${order.Qty}") },
                            leadingIcon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) }
                        )
                    }*/
                }

                Spacer(Modifier.height(12.dp))

                TextButton(
                    onClick = { onViewImage(order) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "View Image")
                    Spacer(Modifier.width(6.dp))
                    Text("View Image")
                }
            }
        }
    }
}


