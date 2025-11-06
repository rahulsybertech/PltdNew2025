@file:OptIn(ExperimentalMaterial3Api::class)

package com.syber.ssspltd.ui.view.addorder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.Marketer
import com.syber.ssspltd.data.model.SaleParty
import com.syber.ssspltd.data.model.Transporter
import com.syber.ssspltd.out.GalleryImageViewModel
import com.syber.ssspltd.ui.view.image.AddImageScreen
import com.syber.ssspltd.ui.view.image.GalleryImagePickerScreen
import com.syber.ssspltd.ui.view.image.ImagePickerBottomSheet
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import com.syber.ssspltd.utils.RoundedTopAppBar
import com.syber.ssspltd.utils.ToolbarUtils.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var orderNo by remember { mutableStateOf("SSS-ORD123") }
    var partyName by remember { mutableStateOf("Dharmender Textile") }
    var marketer by remember { mutableStateOf("Ramkrishna Murti") }
    var saleParty by remember { mutableStateOf("Pushparaj Thakur") }
    var mobileNo by remember { mutableStateOf("8894213201") }
    var email by remember { mutableStateOf("dharmendra@gmail.com") }
    var transport by remember { mutableStateOf("Delhi Cargo Metters") }
    var station by remember { mutableStateOf("Delhi Cargo Metters") }
    var scheme by remember { mutableStateOf("Mewar Farm") }
    var showSheet by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("PENDING") }
    var dispatchType by remember { mutableStateOf("Delhi Cargo Metters") }

    var salePartyType by remember { mutableStateOf("Sale Party") }
    var showGalleryPicker by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val viewModel: GalleryImageViewModel = hiltViewModel()
    val imageUris by viewModel.imageUris.collectAsState()



    Scaffold(
        topBar = {
            RoundedTopAppBar(
                title = "Add Order",
                onBackClick = {        navController.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            CustomTextField(
                label = "Order N",
                value = orderNo,
                onValueChange = { orderNo = it },
                enabled = false // makes it non-editable and applies gray background
            )
            val marketers = listOf(Marketer(1, "Ram"), Marketer(2, "Shyam"))
            val saleParties = listOf(SaleParty("SP01", "Pushparaj"), SaleParty("SP02", "Sunil"))
            val transporters = listOf(Transporter("T1", "Delhi Cargo"), Transporter("T2", "SpeedX"))

            var selectedMarketer by remember { mutableStateOf<Marketer?>(null) }
            var selectedSaleParty by remember { mutableStateOf<SaleParty?>(null) }
            var selectedTransporter by remember { mutableStateOf<Transporter?>(null) }

            Column {
                CommonSearchableDropdown(
                    label = "Marketer",
                    items = marketers,
                    selectedItem = selectedMarketer,
                    onItemSelected = { selectedMarketer = it },
                    getLabel = { it.name }
                )
                var selectedOption by remember { mutableStateOf<String?>(null) }

                val subPartyOptions = listOf("Party A", "Party B", "Party C")
/*
                SubPartyDropdown(
                    label = "Sub Party ATS",
                    options = subPartyOptions,
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it }
                )*/

                Spacer(modifier = Modifier.height(8.dp))

                CommonSearchableDropdown(
                    label = "Sale Party",
                    items = saleParties,
                    selectedItem = selectedSaleParty,
                    onItemSelected = { selectedSaleParty = it },
                    getLabel = { it.partyName }
                )

                Spacer(Modifier.height(8.dp))

                CustomTextField(
                    label = "Sale Party Mobile No",
                    value = mobileNo,
                    onValueChange = { mobileNo = it },
                    enabled = false // makes it non-editable and applies gray background
                )
                Spacer(Modifier.height(8.dp))
                CustomTextField(
                    label = "Sale Party Email",
                    value = email,
                    onValueChange = { email = it },
                    enabled = false // makes it non-editable and applies gray background
                )
                Spacer(Modifier.height(8.dp))
                Text("Sale Party Type *", fontSize = 13.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold)
                Row {
                    RadioButton(
                        selected = salePartyType == "Sale Party",
                        onClick = { salePartyType = "Sale Party" }
                    )
                    Text("Sale Party",fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium)
                    Spacer(Modifier.width(16.dp))
                    RadioButton(
                        selected = salePartyType == "Sub Party Remark",
                        onClick = { salePartyType = "Sub Party Remark" }
                    )
                    Text("Sub Party Remark",fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(8.dp))
                CommonSearchableDropdown(
                    label = "Transport",
                    items = transporters,
                    selectedItem = selectedTransporter,
                    onItemSelected = { selectedTransporter = it },
                    getLabel = { it.company }
                )
                Spacer(Modifier.height(8.dp))
                CommonSearchableDropdown(
                    label = "B Station",
                    items = transporters,
                    selectedItem = selectedTransporter,
                    onItemSelected = { selectedTransporter = it },
                    getLabel = { it.company }
                )
                Spacer(Modifier.height(8.dp))
                CustomTextField("Scheme", scheme) { scheme = it }

                Spacer(Modifier.height(8.dp))
                CustomDropdown(label = "Status", items = listOf("PENDING", "APPROVED", "REJECTED"), selectedItem = status) {
                    status = it
                }
                Spacer(Modifier.height(8.dp))
                CustomDropdown(label = "Dispatch Type", items = listOf("PENDING", "APPROVED", "REJECTED"), selectedItem = status) {
                    status = it
                }
                Spacer(Modifier.height(8.dp))
                CustomTextField("Discription", scheme) { scheme = it }
                Spacer(Modifier.height(8.dp))
                DispatchSection(
                    fromDate = "2024-05-01",
                    toDate = "2024-05-10",
                    onFromDateClick = { /* show from date picker */ },
                    onToDateClick = { /* show to date picker */ },
                    onAddItemClick = { showSheet = true  },
                    onAddImageClick = {showBottomSheet = true  },
                    onSubmitClick = { /* perform submit */ }
                )
                if (showGalleryPicker) {
                    GalleryImagePickerScreen(
                        viewModel = viewModel,
                        onDone = { showGalleryPicker = false }
                    )
                }

                if (showBottomSheet) {
                    ImagePickerBottomSheet(
                        images = imageUris,
                        onAddImageClick = { showGalleryPicker = true },
                        onRemoveImage = { viewModel.removeImage(it) },
                        onDismiss = { showBottomSheet = false }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF6200EE), Color(0xFF6200EE))
                            )
                        )
                        .clickable { /*onSubmitClick()*/ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SUBMIT",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
                if (showSheet) {
                    BottomInputSheet(
                        onCancel = { showSheet = false },
                        onSave = {
                            // Handle Save
                            showSheet = false
                        }
                    )
                }



            }

        }
    }
}
@Composable
fun SubPartyDropdown(
    label: String = "Sub Party ATS",
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = selectedOption ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(45.dp), // Match 45dp height from XML
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onOptionSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun DispatchSection(
    fromDate: String,
    toDate: String,
    onFromDateClick: () -> Unit,
    onToDateClick: () -> Unit,
    onAddItemClick: () -> Unit,
    onAddImageClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(1.dp)
    ) {

        Text(
            text = "Dispatch Date*",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = fromDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("From Date") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "From Date",
                        modifier = Modifier.clickable { onFromDateClick() }
                    )
                },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = toDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("To Date") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "To Date",
                        modifier = Modifier.clickable { onToDateClick() }
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onAddItemClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Add Item")
                Spacer(Modifier.width(8.dp))
                Text("Add Item^")
            }

            Button(
                onClick = onAddImageClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Icon(imageVector = Icons.Default.Face, contentDescription = "Add Image")
                Spacer(Modifier.width(8.dp))
                Text("Add Image")
            }
        }


    }
}


@Composable
fun ReadOnlyField(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
                .padding(12.dp)
        ) {
            Text(text = value)
        }
    }
}

@Composable
fun CustomTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    )
}
@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    val backgroundColor = if (enabled) Color.White else Color(0xFFF0F0F0)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = backgroundColor, // ✅ Correct usage
            disabledTextColor = Color.Black,
            disabledBorderColor = Color.Gray,
            disabledLabelColor = Color.DarkGray
        )
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(label: String, items: List<String>, selectedItem: String, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedItem,
            onValueChange = {},
            label = { Text(label,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CommonSearchableDropdown(
    label: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    getLabel: (T) -> String
) {
    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredItems by remember(searchQuery, items) {
        derivedStateOf {
            if (searchQuery.isBlank()) items
            else items.filter { getLabel(it).contains(searchQuery, ignoreCase = true) }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = selectedItem?.let(getLabel) ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            enabled = false,
                    shape = RoundedCornerShape(8.dp), // Rounded corners
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = Color.Black, // Black border when disabled
                disabledTextColor = Color.Black,
                disabledLabelColor = Color.Gray,
                disabledTrailingIconColor = Color.Gray
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                searchQuery = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Search Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // Dropdown items
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(getLabel(item),fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                        searchQuery = ""
                    }
                )
            }

            if (filteredItems.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No match found", color = Color.Gray,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
                    onClick = {}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomInputSheet(
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // Item and Quantity row list
    val itemList = remember { mutableStateListOf(ItemEntry()) }

    ModalBottomSheet(
        onDismissRequest = { onCancel() },
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Pack Type, Qty, Amount Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = "BORA",
                    onValueChange = {},
                    label = { Text("Pack Type*",fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Qty*",fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Amount*",fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dynamic Item-Quantity Rows
            itemList.forEachIndexed { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = item.item,
                        onValueChange = { itemList[index] = item.copy(item = it) },
                        label = { Text("Item*",fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = item.quantity,
                        onValueChange = { itemList[index] = item.copy(quantity = it) },
                        label = { Text("Quantity*",fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Medium) },
                        modifier = Modifier.weight(1f)
                    )

                    if (index == itemList.lastIndex) {
                        IconButton(onClick = {
                            itemList.add(ItemEntry())
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Red)
                        }
                    } else {
                        IconButton(onClick = {
                            itemList.removeAt(index)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cancel and Save buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.align(Alignment.End)
            ) {
                Button(
                    onClick = { onCancel() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("CANCEL", color = Color.Black,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onSave() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text("SAVE", color = Color.White,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

data class ItemEntry(
    var item: String = "",
    var quantity: String = ""
)





