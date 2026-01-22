package com.syber.ssspltd.ui.view.staybooking

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.JsonParser
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.utils.AppSharedPreferences
import com.syber.ssspltd.utils.ImageUtils
import org.json.JSONObject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGuestScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    onSave: () -> Unit
) {
    val context = LocalContext.current

    // State to store selected images
    val frontImageUri = remember { mutableStateOf<Uri?>(null) }
    var guestId by remember { mutableStateOf(null) }
    val backImageUri = remember { mutableStateOf<Uri?>(null) }
    // ✅ Track which card user clicked ("Front" or "Back")
    val currentPicker = remember { mutableStateOf("") }
    var guestName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val addGuestResult by viewModel.addGuestResult.collectAsState()

    // Launcher for picking image from gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (currentPicker.value == "Front") frontImageUri.value = it
            else backImageUri.value = it
        }
    }

    // Launcher for taking photo with camera
    val cameraUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            cameraUri.value?.let {
                if (currentPicker.value == "Front") frontImageUri.value = it
                else backImageUri.value = it
            }
        }
    }



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
                            "Add Guest",
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

        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text("ID Proof", style = MaterialTheme.typography.titleMedium)

            // ID Proof Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ImagePickerCard("Front Side", imageUri = frontImageUri.value) {
                    currentPicker.value = "Front"
                    showImageSourceDialog(context,
                        onCamera = {
                            val uri = createImageUri(context)
                            cameraUri.value = uri
                            cameraLauncher.launch(uri)
                        },
                        onGallery = { galleryLauncher.launch("image/*") }
                    )
                }
                ImagePickerCard("Back Side", imageUri = backImageUri.value) {
                    currentPicker.value = "Back"
                    showImageSourceDialog(context,
                        onCamera = {
                            val uri = createImageUri(context)
                            cameraUri.value = uri
                            cameraLauncher.launch(uri)
                        },
                        onGallery = { galleryLauncher.launch("image/*") }
                    )
                }
            }


            OutlinedTextField(
                value = guestName,
                onValueChange = {
                    guestName = it
                    isError = guestName.isBlank()
                },
                label = { Text("Guest Name") },
                isError = isError,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Save button
            Button(
                onClick = {
                    when {
                        frontImageUri.value == null -> {
                            Toast.makeText(context, "Please select the front image", Toast.LENGTH_SHORT).show()
                        }
                        backImageUri.value == null -> {
                            Toast.makeText(context, "Please select the back image", Toast.LENGTH_SHORT).show()
                        }
                        guestName.isBlank() -> {
                        Toast.makeText(context, "Please enter guest name", Toast.LENGTH_SHORT).show()
                    }
                        else->{
                            try {

                        val prefs = AppSharedPreferences.getInstance(context)
                        val mobileNumber = prefs.mobileNumber.orEmpty()
                        val partyId = prefs.partyId.orEmpty()
                        val partyCode = prefs.isPartyCode.orEmpty()

                        val jsonObject = JSONObject().apply {

                            // ---------- ID ----------
                            put("id", guestId ?: JSONObject.NULL)

                            // ---------- AccountID, MobileNo, isNewUser ----------
                            if (mobileNumber.isBlank()) {
                                put("accountID", if (partyId.isBlank()) JSONObject.NULL else partyId)
                                put("mobileNo", JSONObject.NULL)
                                put("isNewUser", false)
                            } else {
                                put("accountID", JSONObject.NULL)
                                put("mobileNo", mobileNumber)
                                put("isNewUser", true)
                            }

                            // ---------- Party Code ----------
                            put("partyCode", partyCode)

                            // ---------- Guest Name ----------
                            put("guestName", guestName)

                            // ---------- Front Document ----------
                            put(
                                "frontDocPath",
                                frontImageUri.value?.let { ImageUtils.uriToBase64(context, it) } ?: JSONObject.NULL
                            )
                            // ---------- Back Document ----------
                            put(
                                "backDocPath",
                                backImageUri.value?.let { ImageUtils.uriToBase64(context, it) } ?: JSONObject.NULL
                            )

                        }
                        Log.d("AddGuestReq",jsonObject.toString())
                                val gsonObject = JsonParser.parseString(jsonObject.toString()).asJsonObject
                            viewModel.addAndUpdateGuest(gsonObject)

                            }catch (e:Exception){
                                e.printStackTrace()
                            }
                    }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))
            ) {
                Text("Save", style = MaterialTheme.typography.titleMedium)
            }

            LaunchedEffect(addGuestResult) {
                addGuestResult?.let { response ->
                    if (response.ResponseStatus) {
                        Toast.makeText(context, response.ResponseMessage, Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Booking failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun ImagePickerCard(label: String, imageUri: Uri?, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .size(140.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        if (imageUri != null) {
            // Show selected image
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = label,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(label, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Helper function to create a file Uri for the camera
fun createImageUri(context: Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
}

// Function to show dialog for Camera or Gallery
fun showImageSourceDialog(
    context: Context,
    onCamera: () -> Unit,
    onGallery: () -> Unit
) {
    AlertDialog.Builder(context)
        .setTitle("Select Image Source")
        .setItems(arrayOf("Camera", "Gallery")) { _, which ->
            when (which) {
                0 -> onCamera()
                1 -> onGallery()
            }
        }
        .show()
}

