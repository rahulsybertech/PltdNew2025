package com.syber.ssspltd.ui.viewmodel

import com.syber.ssspltd.network.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService  // this should also be injectable
)
