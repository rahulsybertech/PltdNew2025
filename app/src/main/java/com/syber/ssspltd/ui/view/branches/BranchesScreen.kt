package com.syber.ssspltd.ui.view.branches
import com.syber.ssspltd.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen

data class Branch(
    val name: String,
    val type: String, // H.O., B.O., V.O.
    val image: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchesScreen(navController: NavController) {
    val allBranches = listOf(
        Branch("Delhi Gandhinagar", "H.O.", R.drawable.sss_icon),
        Branch("Delhi Chandni Chowk", "B.O.", R.drawable.sss_icon),
        Branch("Ludhiana", "B.O.", R.drawable.sss_icon),
        Branch("Mumbai", "B.O.", R.drawable.sss_icon),
        Branch("Surat", "B.O.", R.drawable.sss_icon),
        Branch("Jaipur", "B.O.", R.drawable.sss_icon),
        Branch("Bangalore", "B.O.", R.drawable.sss_icon),
        Branch("Nagpur", "B.O.", R.drawable.sss_icon),
        Branch("Pilkhuwa", "V.O.", R.drawable.sss_icon),
        Branch("Pali", "V.O.", R.drawable.sss_icon),
        Branch("Panipat", "V.O.", R.drawable.sss_icon),
    )

    var query by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("All") }

    val branchTypes = listOf("All", "H.O.", "B.O.", "V.O.")

    val filteredBranches = allBranches.filter {
        (selectedType == "All" || it.type == selectedType) &&
                it.name.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Branches", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // 🔍 Search Bar
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                placeholder = { Text("Search branches...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(50)
            )

            // 🔘 Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                branchTypes.forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🟦 Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredBranches) { branch ->
                    BranchCard(branch = branch,navController)
                }
            }
        }
    }
}

@Composable
fun BranchCard(branch: Branch, navController: NavController) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
                // This is triggered when the card is clicked
              //  navController.currentBackStackEntry?.arguments?.putParcelable("branch", branch)
                navController.navigate(Screen.BranchDetailScreen.route)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                    )
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(12.dp)
            ) {
                Image(
                    painter = painterResource(branch.image),
                    contentDescription = branch.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.6f))
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${branch.name} (${branch.type})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

