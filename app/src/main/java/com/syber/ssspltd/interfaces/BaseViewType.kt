package com.syber.ssspltd.interfaces

interface BaseViewType {
    fun getID(): Int
    val id get() = getID()
}