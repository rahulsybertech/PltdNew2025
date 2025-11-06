package com.syber.ssspltd.out

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GalleryImageViewModel @Inject constructor() : ViewModel() {

    private val _imageUris = MutableStateFlow<List<Uri>>(emptyList())
    val imageUris: StateFlow<List<Uri>> = _imageUris

    fun addImages(uris: List<Uri>) {
        // Optional: Limit to 5 and filter >2MB in real use
        _imageUris.value = (_imageUris.value + uris).distinct()
    }

    fun removeImage(uri: Uri) {
        _imageUris.value = _imageUris.value.filterNot { it == uri }
    }

    fun clearImages() {
        _imageUris.value = emptyList()
    }
}
