package com.syber.ssspltd.ui.view.pendingorder
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.syber.ssspltd.data.model.OrderData
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.AppSharedPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingOrderList(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {
    val context = LocalContext.current
    val orderList by viewModel1.pendingOrder.collectAsState()
    val selectedColor = Color(0xFF008080)
    var selectedStatus by remember { mutableStateOf("PENDING") }
    var searchQueries by remember { mutableStateOf("") }

    // Call API when screen first opens
    LaunchedEffect(Unit) {
        fetchOrders(viewModel1, "PENDING",context)
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
                            text = "Pending Orders",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            // AI SEARCH BAR
            OutlinedTextField(
                value = searchQueries,
                onValueChange = { searchQueries = it },
                label = { Text("AI Search (e.g. 'High qty orders')") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        Toast.makeText(context, "AI Filtering: $searchQueries", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "AI Search")
                    }
                }
            )

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // STATUS FILTER BUTTONS
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatusButton("PENDING", selectedStatus, selectedColor) {
                            selectedStatus = "PENDING"
                            fetchOrders(viewModel1, "PENDING",context)
                        }
                        StatusButton("HOLD", selectedStatus, selectedColor) {
                            selectedStatus = "HOLD"
                            fetchOrders(viewModel1, "HOLD",context)
                        }
                    }
                }

                // ORDER LIST
                items(orderList.filter {
                    it.ItemName?.contains(searchQueries, ignoreCase = true) == true || searchQueries.isEmpty()
                }) { order ->
                    OrderCard(order = order)
                }
            }
        }
    }
}

@Composable
fun StatusButton(status: String, currentStatus: String, color: Color, onClick: () -> Unit) {
    val isSelected = status == currentStatus
    Button(
        onClick = onClick,
      /*  modifier = Modifier.weight(1f),*/
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Icon(
            Icons.Default.ShoppingCart,
            contentDescription = null,
            tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.width(6.dp))
        Text(status, color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderCard(order: OrderData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9))
                    .padding(12.dp)
            ) {
                Text(
                    text = order.SupplierName ?: "Unknown Party",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF2E7D32)
                )
            }
            val context = LocalContext.current

            Column(modifier = Modifier.padding(10.dp)) {
                Text("Item: ${order.ItemName}", style = MaterialTheme.typography.bodyLarge)
                Text("Type: ${order.PcsType}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text("Sub Party: ${order.SubParty}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text("Order Date: ${order.OrderDate}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text("Qty: ${order.Qty}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                order.let { pdfPath ->
                    pdfView(pdfPath, context)
                }
                Spacer(Modifier.height(12.dp))
                // ✅ UPDATED ACTIONS WITH IMAGE PATH
                OrderActions(
                    pdfUrl = order.PdfPath.toString(),
                    imageUrl = order.PdfPath.toString() // Ensure your OrderData has ImgPath
                )
            }
        }
    }
}
@Composable
fun pdfView(
    it1: OrderData?,
    context: Context
) {
    val valueStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )

    val isPdfAvailable = !it1?.PdfPath.isNullOrEmpty()

    Text(
        text = "Order No : ${it1?.OrderNo ?: "-"}",
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
                    setDataAndType(Uri.parse(it1!!.PdfPath), "application/pdf")
                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY or
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                context.startActivity(intent)
            }
        } else {
            Modifier
        }
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderActions(pdfUrl: String, imageUrl: String) {
    val context = LocalContext.current

    FlowRow(
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.Center
    ) {
        // ✅ AI SCAN BUTTON
        TextButton(onClick = {
            if (imageUrl.isNotEmpty() && imageUrl != "null") {
                performOcrOnOrder(Uri.parse(imageUrl), context)
            } else {
                Toast.makeText(context, "No image available for AI scan", Toast.LENGTH_SHORT).show()
            }
        }) {
            Icon(Icons.Default.Search, null) // Search icon for AI Scan
            Spacer(Modifier.width(6.dp))
            Text("AI Scan Image")
        }

        TextButton(onClick = {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(pdfUrl)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "No app found to open PDF", Toast.LENGTH_SHORT).show()
            }
        }) {
            Icon(Icons.Default.Place, null)
            Spacer(Modifier.width(6.dp))
            Text("View PDF")
        }
    }
}

private fun fetchOrders(viewModel: AuthViewModel, status: String,context: Context) {
    val jsonObject = JsonObject().apply {
        addProperty("AccountID", AppSharedPreferences.getInstance(context).isPartyCode)
        addProperty("OrderStatus", status)
    }
    viewModel.fetchPendingOrder(jsonObject)
}

// ✅ AI ML METHOD
fun performOcrOnOrder(uri: Uri, context: android.content.Context) {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    // In a real app, you might need to download the image first if it's a URL
    // For local URIs or already cached images:
    try {
        val image = InputImage.fromFilePath(context, uri)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val resultText = visionText.text
                if (resultText.isNotBlank()) {
                    // Show AI result in a Toast or Dialog
                    Toast.makeText(context, "AI Scanned Details:\n$resultText", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "AI could not find text in image", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "AI Scan Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    } catch (e: Exception) {
        Toast.makeText(context, "Error loading image for AI", Toast.LENGTH_SHORT).show()
    }
}