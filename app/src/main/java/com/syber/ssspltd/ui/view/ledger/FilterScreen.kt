package com.syber.ssspltd.ui.view.ledger
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.R
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(navController: NavController) {
    var fromDate by remember { mutableStateOf("01/09/2025") }
    var toDate by remember { mutableStateOf("10/09/2025") }

    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    var showFromDatePicker by remember { mutableStateOf(false) }
    var showToDatePicker by remember { mutableStateOf(false) }

    val filterTypes = listOf("Adjustment", "Entry", "Account")
    val selectedFilters = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 8.dp, // Add shadow
                color = Color(0xFF008080) // Same as AppBar color
            ) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                "Filter",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "01/08/2025 to 20/08/2025",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent, // Surface gives the color
                        titleContentColor = Color.White
                    )
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        fromDate = ""
                        toDate = ""
                        selectedFilters.clear()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA8072))
                ) {
                    Text("Reset")
                }

                Button(
                    onClick = {
                        navController.navigate(Screen.FilterScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Apply")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Filter by Type", style = MaterialTheme.typography.titleMedium)



        /*    Spacer(Modifier.height(8.dp))

            filterTypes.forEach { type ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (selectedFilters.contains(type)) {
                                selectedFilters.remove(type)
                            } else {
                                selectedFilters.add(type)
                            }
                        }
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = selectedFilters.contains(type),
                        onCheckedChange = {
                            if (it) selectedFilters.add(type) else selectedFilters.remove(type)
                        }
                    )
                    Text(type)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("From Date", style = MaterialTheme.typography.titleSmall)
            OutlinedButton(onClick = { showFromDatePicker = true }) {
                Text(fromDate)
            }

            Spacer(Modifier.height(8.dp))

            Text("To Date", style = MaterialTheme.typography.titleSmall)
            OutlinedButton(onClick = { showToDatePicker = true }) {
                Text(toDate)
            }*/
        }

       /* // Date Pickers
        if (showFromDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showFromDatePicker = false },
                onDateChange = { selectedDate ->
                    fromDate = selectedDate.format(dateFormatter)
                    showFromDatePicker = false
                }
            )
        }

        if (showToDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showToDatePicker = false },
                onDateChange = { selectedDate ->
                    toDate = selectedDate.format(dateFormatter)
                    showToDatePicker = false
                }
            )
        }*/
    }

}
@Composable
fun FilterScreen(
    onCloseClick: () -> Unit = {},
    onApplyClick: () -> Unit = {},
    onResetClick: () -> Unit = {}
) {
    val fromDate = remember { mutableStateOf("01/05/2025") }
    val toDate = remember { mutableStateOf("02/05/2025") }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ---------- Left Panel ----------
        Column(
            modifier = Modifier
                .width(150.dp)
                .background(Color(0xFFFFC1C1)) // light red
                .padding(8.dp)
        ) {
            Text(
                text = "Date",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val items = listOf("Adjustment", "Entry", "Account")
            items.forEach { label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = label, fontSize = 14.sp)
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .border(
                                1.dp,
                                Color(0xFFFF6B6B),
                                shape = RoundedCornerShape(6.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("0", color = Color(0xFFFF6B6B))
                    }
                }
            }
        }

        // ---------- Right Panel ----------
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                IconButton(onClick = { onCloseClick() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // From and To Date
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DateBox("From date", fromDate.value) { fromDate.value = it }
                DateBox("To date", toDate.value) { toDate.value = it }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onResetClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6B6B)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Reset", color = Color.White)
                }

                Button(
                    onClick = onApplyClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Apply", color = Color.White)
                }
            }
        }
    }
}
@Composable
fun DateBox(label: String, date: String, onClick: (String) -> Unit) {
    Column(
        modifier = Modifier
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFFFFC1C1), shape = RoundedCornerShape(6.dp))
                .clickable { onClick(date) }
                .padding(10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = date,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
