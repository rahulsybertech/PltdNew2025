package com.syber.ssspltd.network
sealed class ResourceNew<out T> {
    object Loading : ResourceNew<Nothing>()
    data class Success<T>(val value: T) : ResourceNew<T>()
    data class Failure(val errorMessage: String) : ResourceNew<Nothing>()
}
