package com.syber.ssspltd.ui.view.honorlist



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

// ------------------- DATA MODEL -------------------
data class Customer(
    val firmName: String,
    val mobile: String,
    val gst: String,
    val owner: String,
    val station: String,
    val address: String,
    val images: List<String> // ✅ image gallery
)

// ------------------- MAIN SCREEN -------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HonorListScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf<List<String>>(emptyList()) }
    var showGallery by remember { mutableStateOf(false) }

    // 🔹 Demo customers
    val customers = listOf(
        Customer(
            "M/S MUKTA ENTERPRISES",
            "7017263676",
            "09BCIPG8383B1ZI",
            "KHUSHBOO GUPTA",
            "AGRA",
            "GROUND FLOOR, G 15, APARNA MALL VYAS MARKET, HOSPITAL ROAD, AGRA",
            listOf(
                "https://picsum.photos/id/1011/600/400",
                "https://picsum.photos/id/1016/600/400",
                "https://picsum.photos/id/1025/600/400"
            )
        ),
        Customer(
            "MAGIC FASHION",
            "9824208792",
            "24AQWPM2627F1ZN",
            "RAMESH RATIRAMJI MULEVA",
            "AHMEDABAD",
            "9, MANAV COMPLEX, OPP SBI BANK NR INDUCTOTHERM, BOPAL, AHMEDABAD",
            listOf(
                "https://picsum.photos/id/1027/600/400",
                "https://picsum.photos/id/1035/600/400",
                "https://picsum.photos/id/1040/600/400"
            )
        )
    )

    // 🔍 Filter
    val filteredList = customers.filter {
        it.firmName.contains(searchQuery, true) || it.owner.contains(searchQuery, true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            shadowElevation = 8.dp,
            color = Color(0xFF008080)
        ) {
            TopAppBar(
                title = { Text("Booking Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White // ✅ makes back icon white
                )
            )
        }


        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search customer...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredList) { customer ->
                CustomerCard(
                    customer = customer,
                    onImageClick = { imgs ->
                        selectedImages = imgs
                        showGallery = true
                    }
                )
            }
        }
    }

    // ✅ Full Gallery Dialog
    if (showGallery) {
        GalleryDialog(images = selectedImages, onDismiss = { showGallery = false })
    }
}

// ------------------- CUSTOMER CARD -------------------
@Composable
fun CustomerCard(customer: Customer, onImageClick: (List<String>) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Firm: ${customer.firmName}", style = MaterialTheme.typography.titleMedium)
            Text("Mobile: ${customer.mobile}", style = MaterialTheme.typography.bodyMedium)
            Text("GST: ${customer.gst}", style = MaterialTheme.typography.bodyMedium)
            Text("Owner: ${customer.owner}", style = MaterialTheme.typography.bodyMedium)
            Text("Station: ${customer.station}", style = MaterialTheme.typography.bodyMedium)
            Text("Address: ${customer.address}", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(8.dp))

            // 🔹 Thumbnails
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(customer.images) { img ->
                    AsyncImage(
                        model = img,
                        contentDescription = "Thumbnail",
                        modifier = Modifier
                            .size(80.dp)
                            .clickable { onImageClick(customer.images) }
                    )
                }
            }
        }
    }
}

// ------------------- FULLSCREEN GALLERY -------------------
@Composable
fun GalleryDialog(images: List<String>, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black.copy(alpha = 0.95f)
        ) {
            Box(Modifier.fillMaxSize()) {
                var page by remember { mutableStateOf(0) }

                // Simple pager-like effect
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { /* consume */ },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = images[page],
                        contentDescription = "Full Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { if (page > 0) page-- },
                            enabled = page > 0
                        ) { Text("Prev") }

                        Spacer(Modifier.width(16.dp))

                        Button(
                            onClick = { if (page < images.lastIndex) page++ },
                            enabled = page < images.lastIndex
                        ) { Text("Next") }
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }
        }
    }
}

