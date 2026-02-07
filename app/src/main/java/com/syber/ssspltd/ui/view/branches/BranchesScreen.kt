package com.syber.ssspltd.ui.view.branches
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.JsonObject
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.data.model.branch.BranchResult
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.AppSharedPreferences

data class Branch(
    val name: String,
    val type: String, // H.O., B.O., V.O.
    val image: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchesScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {

    val allBranches by viewModel1.branchListInProfilePage.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()
    val context = LocalContext.current

    var query by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("All") }

    val branchTypes = listOf("All", "H.O.", "B.O.", "V.O.")

    // API call once
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {
            addProperty(
                "MOBILENO",
                AppSharedPreferences.getInstance(context).mobileNumber
            )
            addProperty(
                "accountid",
                "e9906cde-7d9c-48ac-a2de-7594eb4f8fb9"
            )
            addProperty("DBNAME", "")
        }
        viewModel1.fatchBranchList(jsonObject)
    }

    // Filtering
    val filteredBranches = remember(
        allBranches,
        query,
        selectedType
    ) {
        allBranches.filter { branch ->
            val name = branch.BranchName ?: ""

            val matchesType =
                selectedType == "All" ||
                        name.contains(selectedType, ignoreCase = true)

            val matchesQuery =
                name.contains(query, ignoreCase = true)

            matchesType && matchesQuery
        }
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
                .fillMaxSize()
                .padding(padding)
        ) {

            // 🔍 Search Bar
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
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

            // 🟦 Content Area
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator()
                    }
                    filteredBranches.isEmpty() -> {
                        Text("No branches found")
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(filteredBranches) { branch ->
                                BranchCard(
                                    branch = branch,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable

fun BranchCard(
    branch: BranchResult,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // navigation if needed
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            // 🌄 Branch Image
            AsyncImage(
                model = branch.Branch_Images,
                contentDescription = branch.BranchName,
                /*contentScale = ContentScale.Crop,*/
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
      /*          placeholder = painterResource(R.drawble.),
                error = painterResource(R.drawable.placeholder)*/
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🏢 Branch Name
            Text(
                text = branch.BranchName ?: "N/A",
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 📍 Managed By
            if (!branch.BranchManagedBy.isNullOrEmpty()) {
                Text(
                    text = branch.BranchManagedBy,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


