package com.syber.ssspltd.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syber.ssspltd.R
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1


// Utility function for a custom toolbar
object ToolbarUtils {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomToolbar(
        title: String,
        onBackClick: () -> Unit,
     themeColors: ThemeColors
    ) {
        TopAppBar(
            title = { Text(text = title,  fontSize = 16.sp,  color = Color.Black,  fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold ) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = themeColors.primary,       // Background color
                titleContentColor = themeColors.background // Text color
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
    @Composable
    fun CustomToolbarWithCalendar(
        showUserRow: Boolean,
        userName: String,
        onBackClick: () -> Unit,
        onCalendarClick: () -> Unit,
        expanded: Boolean
    ) {
        Column {
            // Toolbar Surface with rounded bottom
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = Color(0xFF008080), // Teal AppBar background
                shadowElevation = 4.dp,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Back icon
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onBackClick() },
                        tint = Color.White
                    )

                    // Center logo
                    Image(
                        painter = painterResource(id = R.drawable.sss_icon),
                        contentDescription = "App Icon",
                        modifier = Modifier.size(36.dp)
                    )

                    // Right-side calendar card
                    Card(
                        modifier = Modifier
                            .height(36.dp)
                            .width(120.dp)
                            .clickable { onCalendarClick() },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (userName.isEmpty()) "2024-2025" else userName,
                                color = if (userName.isEmpty()) Color.Gray else Color.Black,
                                fontSize = 13.sp,
                                fontFamily = poppinsFontFamily1,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }

            // Optional dropdown row below toolbar
            if (showUserRow) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = userName,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = poppinsFontFamily1,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = Modifier.clickable { onBackClick() }
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomTopAppBar(
        title: String,
        onBackClick: () -> Unit,
        imagePainter: Painter // e.g., painterResource(id = R.drawable.profile)
    ) {
        TopAppBar(
            title = {
                Text(text = title, fontSize = 19.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            )
        )
    }




}
