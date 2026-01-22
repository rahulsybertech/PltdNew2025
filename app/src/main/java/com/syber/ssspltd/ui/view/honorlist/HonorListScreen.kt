package com.syber.ssspltd.ui.view.honorlist
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.syber.ssspltd.data.model.honar.BlackListedName
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import com.syber.ssspltd.utils.toSentenceCase

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
fun HonorListScreen(navController: NavController, viewModel1: AuthViewModel, themeColors: ThemeColors) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf<List<String>>(emptyList()) }
    var showGallery by remember { mutableStateOf(false) }
    val honarList by viewModel1.honarList.collectAsState()

    // 🔹 Demo customers

    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {




        //    addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("FROMDATE", "")
            addProperty("FROMDATE", "")
            addProperty("TODATE", "")
            addProperty("Status", "")
            add("AVGDATE", JsonNull.INSTANCE)
            add("TICK", JsonNull.INSTANCE)
            addProperty("DBNAME", "2025-2026")
            add("LEDGERTYPE", JsonNull.INSTANCE)
        }
        viewModel1.fetchHonarList(jsonObject)
    }

    // 🔍 Filter
    val filteredList = honarList.filter {
        it.Name!!.contains(searchQuery, true) || it.GSTNo!!.contains(searchQuery, true)
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
fun CustomerCard(customer: BlackListedName, onImageClick: (List<String>) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(12.dp)) {
            Text("Firm: ${customer.OwnerName?.toSentenceCase()}", fontSize = 16.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.SemiBold)
            Text("Mobile: ${customer.MobileNo?.toSentenceCase()}", fontSize = 16.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.SemiBold)
            Text("GST: ${customer.GSTNo?.toSentenceCase()}", fontSize = 16.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.SemiBold)
            Text("Owner: ${customer.OwnerName?.toSentenceCase()}",fontSize = 16.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.SemiBold)
            Text("Station: ${customer.Station?.toSentenceCase()}",fontSize = 16.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.SemiBold)
            Text("Address: ${customer.Address?.toSentenceCase()}", fontSize = 16.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(8.dp))

     /*       // 🔹 Thumbnails
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
            }*/
        }
    }
}

// ------------------- FULLSCREEN GALLERY -------------------
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryDialog(
    images: List<String>,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {

            var isZoomed by remember { mutableStateOf(false) }

            val pagerState = rememberPagerState { images.size }

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = !isZoomed, // 🔥 IMPORTANT
                modifier = Modifier.fillMaxSize()
            ) { page ->
                ZoomableImage1(
                    imageUrl = images[page],
                    onZoomChanged = { zoomed ->
                        isZoomed = zoomed
                    }
                )
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Close, null, tint = Color.White)
            }
        }
    }
}


@Composable
fun ZoomableImage1(
    imageUrl: String,
    onZoomChanged: (Boolean) -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 5f)
        offset += panChange
        onZoomChanged(scale > 1f)
    }

    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offset.x
                translationY = offset.y
            }
            .transformable(state)
    )
}




