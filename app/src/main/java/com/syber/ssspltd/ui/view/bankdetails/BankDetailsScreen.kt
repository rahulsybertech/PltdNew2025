package com.syber.ssspltd.ui.view.bankdetails


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.google.gson.JsonObject
import com.syber.ssspltd.R
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.view.profile.ProfileItem
import com.syber.ssspltd.utils.AppSharedPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankDetailsScreen(
    navController: NavController,
    viewModel1: AuthViewModel,
    themeColors: ThemeColors
) {

    val bank by viewModel1.bankResult.collectAsState()
    val isLoading by viewModel1.loading.collectAsState()
    val context = LocalContext.current

    // API call once
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {
            addProperty(
                "MOBILENO",
                AppSharedPreferences.getInstance(context).mobileNumber
            )
            addProperty(
                "PARTYCODE",
                AppSharedPreferences.getInstance(context).isPartyCode
            )
            addProperty("DBNAME", "")
        }
        viewModel1.fatchBankDetail(jsonObject)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bank Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008080),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF008080))
                }
            }

            bank.isNotEmpty() -> {

                val bankData = bank[0]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // 🔷 Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0xFF008080), Color(0xFF00BFA5))
                                )
                            )
                            .padding(bottom = 60.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.sss_icon),
                                contentDescription = "Bank",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .border(3.dp, Color.White, CircleShape)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = bankData.AccountName ?: "",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Text(
                                text = bankData.BankName ?: "",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(70.dp))

                    BankDetail(
                        icon = Icons.Default.Person,
                        label = "Account Name",
                        value = bankData.AccountName ?: ""
                    )

                    BankDetail(
                        icon = Icons.Default.AccountBox,
                        label = "Bank Name",
                        value = bankData.BankName ?: ""
                    )

                    BankDetail(
                        icon = Icons.Default.LocationOn,
                        label = "Branch Name",
                        value = bankData.BranchName ?: ""
                    )

                    BankDetail(
                        icon = Icons.Default.AccountBox,
                        label = "IFSC Code",
                        value = bankData.IFSC_Code ?: ""
                    )

                   /* BankDetail(
                        icon = Icons.Default.AccountBox,
                        label = "Account Number",
                        value = maskAccountNumber(bankData.BankAccountNo)
                    )*/
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No bank details found")
                }
            }
        }
    }
}


@Composable
fun BankDetail(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = label, tint = Color(0xFF1E88E5))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, fontSize = 12.sp, color = Color.Gray)
                Text(text = value, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        }
    }
}