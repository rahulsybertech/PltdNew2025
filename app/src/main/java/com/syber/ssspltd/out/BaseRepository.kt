package com.app.naturalhigh.out

import com.syber.ssspltd.network.ApiService
import net.simplifiedcoding.data.network.SafeApiCall


abstract class BaseRepository(private val api: ApiService) : SafeApiCall {

    /*suspend fun logout() = safeApiCall {
        api.logout()
    }*/
}