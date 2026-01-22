package com.syber.ssspltd.ui.view.productlist
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.syber.ssspltd.R
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.request.ImageRequest
import com.google.gson.JsonObject
import com.syber.ssspltd.data.model.brand.BrandItem
import com.syber.ssspltd.data.model.brand.ProductImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController,
                      viewModel1: AuthViewModel,branchId:String,
                      themeColors: ThemeColors
) {


    val brandMasterList by viewModel1.brandMasterList.collectAsStateWithLifecycle()
    val context = LocalContext.current
    /*   {"MOBILENO":"7290087642","DBNAME":"","accountid":""}*/

    LaunchedEffect(branchId) {
        if (branchId.isNotEmpty()) {
            val jsonObject = JsonObject().apply {
                addProperty("BranchID", branchId)
            }
            viewModel1.fatchBrandMasterList(jsonObject)
        }
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
                            Text("Brands", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)

                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
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
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFF8F9FA))
                .fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(brandMasterList) { product ->
                ProductCard(viewModel1,navController,product)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCard(
    viewModel1: AuthViewModel,
    navController: NavController,
    product: BrandItem
) {
    var showSheet by remember { mutableStateOf(false) }
    var selectedGuestImages by remember { mutableStateOf<List<ProductImage>>(emptyList()) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ✅ Logo (fixed size)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.BrandLogo)
                    .crossfade(true)
                    .build(),
                contentDescription = product.BrandName,
                placeholder = painterResource(R.drawable.image_one),
                error = painterResource(R.drawable.ic_criss_cross),
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFECEFF1)),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(16.dp))

            // ✅ Text area (remaining space)
            Column(
                modifier = Modifier.weight(1f)
            ) {

                // 🔥 BRAND NAME (NOW VISIBLE)
                Text(
                    text = product.BrandName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF008080)
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = product.BrandDescription,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                FlowRow {
                    product.BrandCategoryA.forEach { category ->
                        AssistChip(
                            onClick = {},
                            label = { Text(category.Brand_Category.trim()) }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        showSheet=true
                        selectedGuestImages = product.ArrayProductImageA

                        viewModel1.updateImages(product.ArrayProductImageA)

                        navController.navigate("full_image_screen")
                      //  navController.navigate(Screen.ViewProductScreen.route)
                    }
                ) {
                    Text("View Products", color = Color(0xFF008080))
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullScreenZoomImage(
    images: List<ProductImage>,
    startIndex: Int = 0,
    onBack: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(startIndex) }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 5f)
        offset += panChange
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // 🔝 FULL IMAGE
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = images[selectedIndex].ProductImageA,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .transformable(transformState)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
            )

            // 🔙 Back Button
            IconButton(
                onClick = {
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        // 🔽 THUMBNAILS
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(images) { index, item ->
                AsyncImage(
                    model = item.ProductImageA,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = if (index == selectedIndex) 2.dp else 0.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            selectedIndex = index
                            scale = 1f
                            offset = Offset.Zero
                        }
                )
            }
        }
    }
}


@Composable
fun FullImageScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val images = viewModel.images

    FullScreenZoomImage(
        images = viewModel.images,
        onBack = { navController.popBackStack() }
    )
}





