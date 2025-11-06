package com.app.naturalhigh.out

import androidx.lifecycle.ViewModel

abstract class BaseViewModell(
    private val repository: BaseRepository) : ViewModel() {

    //suspend fun logout() = withContext(Dispatchers.IO) { repository.logout() }

}