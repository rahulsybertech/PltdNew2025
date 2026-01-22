package com.syber.ssspltd.ui.view.brand

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.brand.Branch
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.AppSharedPreferences
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandsScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {


    val brandlistByBranch by viewModel1.brandlistByBranch.collectAsStateWithLifecycle()
    val context = LocalContext.current
 /*   {"MOBILENO":"7290087642","DBNAME":"","accountid":""}*/

    LaunchedEffect(Unit) {
                val jsonObject = JsonObject().apply {
                    addProperty("MOBILENO", AppSharedPreferences.getInstance(context).phoneNumber)
                    addProperty("DBNAME", "")
                    addProperty("accountid", "")
                }
                viewModel1.fatchBrandListByBranch(jsonObject)
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
                .background(Color(0xFFF5F5F5))
        ) {
            items(brandlistByBranch) { brandlistByBranch ->
                LocationCard(viewModel1,navController,brandlistByBranch) {
                    // Handle location click
                }
            }
        }
    }
}

@Composable
fun LocationCard(  viewModel1: AuthViewModel,navController:NavController, location: Branch, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(

            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Location Icon
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(location.Branch_Images)
                    .crossfade(true)
                    .build(),
                contentDescription = location.BranchName,
                placeholder = painterResource(R.drawable.image_one),
                error = painterResource(R.drawable.ic_criss_cross),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFECEFF1)),
                contentScale = ContentScale.Crop
            )


            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(location.BranchName, color = Color(0xFF008080), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

                // Horizontal scroll of brand logos
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(location.BrandDetail) { brandLogo ->
                        Card(
                            modifier = Modifier
                                .size(80.dp, 80.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(brandLogo.BrandImage)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = brandLogo.BrandImage,
                               /* placeholder = painterResource(R.drawable.image_one),
                                error = painterResource(R.drawable.ic_criss_cross),*/
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFFECEFF1)),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                }
            }

            // Right Arrow
            IconButton(
                onClick = {
                    navController.navigate(
                        Screen.ProductListScreen.route + "/${location.ID}"
                    )
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Next",
                    tint = Color(0xFF008080)
                )
            }
        }
    }
}

data class LocationBrands(
    val name: String,
    val icon: Int,
    val brands: List<Int>
)
