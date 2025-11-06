package com.syber.ssspltd.ui.view.image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.syber.ssspltd.data.model.ImageModel
import com.syber.ssspltd.out.GalleryImageViewModel

@Composable
fun AddImageScreen(
    viewModel: GalleryImageViewModel = hiltViewModel(),
    onBackWithResult: (ArrayList<ImageModel>) -> Unit
) {
    var showGalleryPicker by remember { mutableStateOf(false) }

    val imageUris by viewModel.imageUris.collectAsState()

    if (showGalleryPicker) {
        GalleryImagePickerScreen(
            onDone = {
                showGalleryPicker = false
            }
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Button(onClick = { showGalleryPicker = true }) {
            Text("Pick Images")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🛠️ Give LazyVerticalGrid bounded height using weight
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // 👈 This fixes the crash
        ) {
            items(imageUris) { uri ->
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}
