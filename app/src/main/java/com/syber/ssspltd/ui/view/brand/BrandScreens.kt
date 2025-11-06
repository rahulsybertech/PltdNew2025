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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandsScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {
    val locations = listOf(
        LocationBrands(
            "Delhi Gandhinagar (H.O.)",
            R.drawable.bill, // sample icon
            listOf(R.drawable.ssslogopng, R.drawable.image_one, R.drawable.image_one)
        ),
        LocationBrands(
            "Delhi Chandni Chowk (B.O.)",
            R.drawable.sss_icon,
            listOf(R.drawable.sss_icon, R.drawable.sss_icon, R.drawable.sss_icon)
        ),
        // Add more...
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Brands", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008080),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFF5F5F5))
        ) {
            items(locations) { location ->
                LocationCard(navController,location) {
                    // Handle location click
                }
            }
        }
    }
}

@Composable
fun LocationCard(navController:NavController,location: LocationBrands, onClick: () -> Unit) {
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
            Image(
                painter = painterResource(id = location.icon),
                contentDescription = location.name,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFECEFF1))
                    .padding(8.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(location.name, color = Color(0xFF008080), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

                // Horizontal scroll of brand logos
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(location.brands) { brandLogo ->
                        Card(
                            modifier = Modifier
                                .size(80.dp, 60.dp),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Image(
                                painter = painterResource(id = brandLogo),
                                contentDescription = "Brand Logo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize().padding(6.dp)
                            )
                        }
                    }
                }
            }

            // Right Arrow
            IconButton(   onClick = {
                navController.navigate(Screen.ProductListScreen.route)
            }) {
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
