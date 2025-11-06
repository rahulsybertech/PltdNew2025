package com.syber.ssspltd.interfaces

interface BaseWidget {
    val viewType: BaseViewType?
    fun getUniqueId(): String
    override fun equals(other: Any?): Boolean // Auto Implemented by data class
}