package com.syber.ssspltd.data.model.bookingRequest

import com.google.gson.annotations.SerializedName

data class BranchList(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("dbPrefix")
    val branchName: String? = null ,

    @SerializedName("stayfacility")
    val stayfacility: String? = null
)
