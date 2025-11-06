package com.syber.ssspltd.ui.view.staybooking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.syber.ssspltd.data.model.staybooking.StayBookingResult
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.AppSharedPreferences


@Composable
fun StayBookingListScreen( navController: NavController,
                viewModel1: AuthViewModel,
                themeColors: ThemeColors) {
    val listState = rememberLazyListState()
    val bookings by viewModel1.stayBooking.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()
    val context = LocalContext.current  // ✅ Get the context

    // Call API when screen first opens
    LaunchedEffect(Unit) {

        AppSharedPreferences.getInstance(context).isPartyCode?.let { viewModel1.fetchStayBooking(it) }
    }
   /* val bookings = remember {
        listOf(
            Booking("1083", "DL", "14-09-2025 10:39 PM", "15-09-2025 12:00 AM", 2),
            Booking("1084", "Mumbai", "15-09-2025 09:00 AM", "16-09-2025 11:00 AM", 4),
            Booking("1085", "Delhi", "16-09-2025 08:00 AM", "17-09-2025 10:00 AM", 3)
        )
    }*/
    BookingListScreen(navController,bookings)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingListScreen(navController: NavController, bookings: List<StayBookingResult>) {
    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp), // rounded bottom corners
                shadowElevation = 8.dp, // optional shadow
                color = Color(0xFF008080) // teal color
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "Booking Details",
                            color = Color.White
                        )
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
                        containerColor = Color.Transparent, // make transparent since Surface gives the color
                        titleContentColor = Color.White
                    )
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(onClick = {    //   navController.navigate(Screen.BookingRequestScreen.route)
                navController.navigate(Screen.BookingRequestScreen.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Visit")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(bookings) { booking ->
                BookingCard(booking)
            }
        }
    }
}

@Composable
fun BookingCard(booking: StayBookingResult) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Visit ID: ${booking.id}", style = MaterialTheme.typography.titleMedium)
                Row {
                    IconButton(onClick = { /* edit */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { /* delete */ }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
            Text("Branch: ${booking.branchName}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("Check In: ${booking.checkInDate}", style = MaterialTheme.typography.bodySmall)
            Text("Check Out: ${booking.checkoutDate}", style = MaterialTheme.typography.bodySmall)
            Text("Persons: ${booking.noOfPerson}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

