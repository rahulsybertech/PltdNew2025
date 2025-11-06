package com.syber.ssspltd.ui.view.staybooking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syber.ssspltd.data.model.staybooking.GuestMasterDetail
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.utils.AppSharedPreferences
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingRequestScreen(
    modifier: Modifier = Modifier,
    viewModel1: AuthViewModel,
    onSaveClick: () -> Unit = {}
) {
    var tripType by remember { mutableStateOf("Round trip") }

    var fromBranch by remember { mutableStateOf("Yogyakarta (YIA)") }
    var toBranch by remember { mutableStateOf("Lombok (LOP)") }
    val guestList by viewModel1.guestList.collectAsState()

    // Track whether check-in time is selected
    var isCheckInTimeSelected by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var checkInDate by remember { mutableStateOf<LocalDate?>(null) }
    var checkOutDate by remember { mutableStateOf<LocalDate?>(null) }

    var checkInTime by remember { mutableStateOf<LocalTime?>(null) }
    var checkOutTime by remember { mutableStateOf<LocalTime?>(null) }

    // Call API when screen first opens
    LaunchedEffect(Unit) {
        AppSharedPreferences.getInstance(context).isPartyCode?.let {
            viewModel1.fetchGuestList(
                "",
                it
            )
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Request") },
                navigationIcon = {
                    IconButton(onClick = { /* Back */ }) {
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
        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Spacer(Modifier.height(20.dp))


// 🗓️ Dates Row
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 🟩 Check-In Date
                DateCard("Check In Date", checkInDate?.toString() ?: "Select Date") {
                    openCalendar(context, calendar) { picked ->
                        checkInDate = picked

                        // 🧹 Reset all dependent fields
                        checkInTime = null
                        checkOutDate = null
                        checkOutTime = null
                        isCheckInTimeSelected = false

                        Toast.makeText(
                            context,
                            "Please select Check-in time next",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // 🟩 Check-Out Date
                DateCard("Check Out Date", checkOutDate?.toString() ?: "Select Date") {
                    // 🔒 Prevent selecting Check-Out Date until Check-In Time is selected
                    if (!isCheckInTimeSelected) {
                        Toast.makeText(
                            context,
                            "Please select Check-in time first",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@DateCard
                    }

                    openCalendar(context, calendar) { picked ->
                        if (checkInDate != null && picked < checkInDate!!) {
                            Toast.makeText(
                                context,
                                "Check-out date must be after Check-in date",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            checkOutDate = picked
                            checkOutTime = null // reset time on new date
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

// ⏰ Times Row
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 🟩 Check-In Time
                DateCard("Check In Time", checkInTime?.toString() ?: "Select Time") {
                    if (checkInDate == null) {
                        Toast.makeText(
                            context,
                            "Please select Check-in date first",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@DateCard
                    }

                    openTimePicker(context) { pickedTime ->
                        checkInTime = pickedTime
                        isCheckInTimeSelected = true

                        // Reset invalid check-out
                        checkOutDate = null
                        checkOutTime = null
                    }
                }

                // 🟩 Check-Out Time
                DateCard("Check Out Time", checkOutTime?.toString() ?: "Select Time") {
                    if (checkOutDate == null) {
                        Toast.makeText(
                            context,
                            "Please select Check-out date first",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@DateCard
                    }

                    openTimePicker(context) { pickedTime ->
                        when {
                            checkInDate == null -> {
                                Toast.makeText(
                                    context,
                                    "Please select Check-in date first",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            checkInTime == null -> {
                                Toast.makeText(
                                    context,
                                    "Please select Check-in time first",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            checkOutDate!! < checkInDate!! -> {
                                Toast.makeText(
                                    context,
                                    "Check-out date must be after Check-in date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            checkOutDate == checkInDate && pickedTime <= checkInTime!! -> {
                                Toast.makeText(
                                    context,
                                    "Check-out time must be after Check-in time",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                checkOutTime = pickedTime
                            }
                        }
                    }
                }
            }








            Spacer(Modifier.height(20.dp))

            // Search button
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF008080)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Submit", color = Color.White, fontSize = 18.sp)
            }

            Spacer(Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Guest List", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(10.dp))
                GuestSelectionList(guestList)

            }
        }



    }
}
@Composable
fun GuestSelectionList(guestList: List<GuestMasterDetail>) {
    val selectedGuests = remember { mutableStateListOf<GuestMasterDetail>() }

    Column(modifier = Modifier.padding(2.dp)) {
        guestList.forEach { guest ->
            val isChecked = selectedGuests.contains(guest)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (isChecked) selectedGuests.remove(guest)
                        else selectedGuests.add(guest)
                    }
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        if (checked) selectedGuests.add(guest)
                        else selectedGuests.remove(guest)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF008080), // ✅ Teal
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.White
                    )
                )

                Spacer(Modifier.width(2.dp))

                Text(
                    text = guest.guestName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }

        // 🧾 Show selected guests (for debug or preview)
        if (selectedGuests.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            Text(
                "Selected Guests:",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF008080)
            )
            selectedGuests.forEach {
                Text("- ${it.guestName}", fontSize = 14.sp)
            }
        }
    }
}




@Composable
fun DateCard(label: String, date: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = date,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color(0xFF008080),
                    modifier = Modifier.clickable { onClick() }
                )
            },
            modifier = Modifier.width(160.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateCardTime(label: String, date: LocalDateTime?, onClick: () -> Unit) {
    val display = date?.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")) ?: "Select"
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label)
            Spacer(Modifier.height(6.dp))
            Text(display)
        }
    }
}
@Composable
fun TripTypeChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        color = if (selected) Color(0xFFFFC107) else Color.LightGray.copy(alpha = 0.3f),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Color.Black else Color.DarkGray
        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun isCheckOutValid(checkIn: LocalDateTime?, checkOut: LocalDateTime?): Boolean {
    if (checkIn == null || checkOut == null) return true // nothing to compare yet
    return checkOut.isAfter(checkIn) // ✅ must be strictly later
}

@RequiresApi(Build.VERSION_CODES.O)
fun openTimePicker(context: Context, onTimePicked: (LocalTime) -> Unit) {
    val now = LocalTime.now()
    TimePickerDialog(
        context,
        { _, hour, minute ->
            onTimePicked(LocalTime.of(hour, minute))
        },
        now.hour,
        now.minute,
        true // 24-hour format
    ).show()
}



/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingRequestScreen(
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit = {}
) {
    var selectedBranch by remember { mutableStateOf("") }
    val branches = listOf("Branch A", "Branch B", "Branch C")

    var checkInDate by remember { mutableStateOf("12-09-2025") }
    var checkOutDate by remember { mutableStateOf("12-09-2025") }

    var checkInTime by remember { mutableStateOf("10:48 AM") }
    var checkOutTime by remember { mutableStateOf("11:48 AM") }

    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val guests = listOf(
        "Vipin Sharma" to "View Img",
        "Chandra Sir" to "View Img"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Request") },
                navigationIcon = {
                    IconButton(onClick = { *//* Back *//* }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF7070),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Text("Save", color = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Branch Dropdown
            var expanded by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = selectedBranch,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Branch") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, null,
                        Modifier.clickable { expanded = true })
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                branches.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            selectedBranch = it
                            expanded = false
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Date Section
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DateCard("Check in date", checkInDate) {
                    openCalendar(context, calendar) { picked ->
                        checkInDate = picked
                    }
                }
                DateCard("Check out date", checkOutDate) {
                    openCalendar(context, calendar) { picked ->
                        checkOutDate = picked
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Time Section
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TimeCard("Check in time", checkInTime)
                TimeCard("Check out time", checkOutTime)
            }


            Spacer(Modifier.height(16.dp))

            // Guest Table
            Row(
                Modifier.fillMaxWidth().background(Color.LightGray),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Guest Name", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("Aadhar Card", Modifier.weight(1f), fontWeight = FontWeight.Bold)
            }

            guests.forEach { (name, aadhar) ->
                Row(
                    Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = false, onCheckedChange = {})
                    Text(name, Modifier.weight(1f).padding(start = 8.dp))
                    TextButton(onClick = { *//* View Img *//* }) {
                        Icon(Icons.Default.CheckCircle, null, tint = Color.Red)
                        Spacer(Modifier.width(4.dp))
                        Text(aadhar, color = Color.Red)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Add New Guest
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Blue)
                Spacer(Modifier.width(4.dp))
                Text(
                    "Add New Guest",
                    color = Color.Blue,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { *//* Add Guest *//* }
                )
            }
        }
    }
}*/

/*@Composable
fun DateCard(label: String, date: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = date,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.clickable { onClick() }
                )
            },
            modifier = Modifier.width(160.dp)
        )
    }
}


@Composable
fun TimeCard(label: String, time: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = time,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Red)
            },
            modifier = Modifier.width(160.dp)
        )
    }
}
fun openCalendar(
    context: Context,
    calendar: Calendar,
    onDateSelected: (String) -> Unit
) {
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val picked = "$dayOfMonth-${month + 1}-$year"
            onDateSelected(picked)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}*/

@RequiresApi(Build.VERSION_CODES.O)
fun openCalendar(
    context: Context,
    calendar: Calendar,
    onDateSelected: (LocalDate) -> Unit
) {
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val picked = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(picked)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun openCalendarTime(context: Context, calendar: Calendar, onDatePicked: (LocalDate) -> Unit) {
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, y, m, d ->
            val pickedDate = LocalDate.of(y, m + 1, d) // ✅ LocalDate, not String
            onDatePicked(pickedDate)
        },
        year, month, day
    ).show()
}



