package com.syber.ssspltd.ui.view.galleryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.JsonObject
import com.syber.ssspltd.data.model.gallery.EventMediaItem
import com.syber.ssspltd.out.AuthViewModel
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1
import com.syber.ssspltd.utils.toSentenceCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernGalleryScreen(navController: NavController, viewModel1: AuthViewModel,
                        themeColors: ThemeColors
) {
    val eventList by viewModel1.eventList.collectAsState()
    val context = LocalContext.current

    // Call API when screen first opens
    LaunchedEffect(Unit) {
        val jsonObject = JsonObject().apply {

         /*   addProperty("PARTYCODE", AppSharedPreferences.getInstance(context).isPartyCode)
            addProperty("FROMDATE", "")
            addProperty("TODATE", "")
            addProperty("Status", "")
            add("AVGDATE", JsonNull.INSTANCE)
            add("TICK", JsonNull.INSTANCE)
            addProperty("DBNAME", "2025-2026")*/
            addProperty("BranchID", "1")
        }
        viewModel1.fetchEventList(jsonObject)
    }


    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight() // take full height of the TopAppBar
                            .wrapContentHeight(Alignment.CenterVertically) // center content vertically
                    ) {
                        Text(
                            text = "Video Gallery",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .shadow(8.dp, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF008080), Color(0xFF00BFA5))
                        ),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                items(eventList) { section ->

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {

                        Text(section.EventName.toSentenceCase(),  fontSize = 16.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            items(section.image_list) { media ->

                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .size(width = 200.dp, height = 200.dp)
                                        .clickable {
                                            // open image / video
                                        },
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    EventMediaItemView(media)
                                }
                            }
                        }
                    }
                }
            }
        }




    )
}

@Composable
fun EventMediaItemView(item: EventMediaItem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
    ) {

        when (item.linktype.lowercase()) {

            "image" -> {
                AsyncImage(
                    model = item.source_url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            "videolink" -> {
                // 🔹 Show thumbnail (YouTube or fallback image)
                AsyncImage(
                    model = getVideoThumbnail(item.source_url),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // 🔹 Play Icon overlay
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Video",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = CircleShape
                        )
                        .padding(6.dp)
                )
            }
        }
    }
}
fun getVideoThumbnail(url: String): String {
    return if (url.contains("youtube") || url.contains("youtu.be")) {
        val videoId = url.substringAfter("v=").substringBefore("&")
        "https://img.youtube.com/vi/$videoId/0.jpg"
    } else {
        ""
    }
}







