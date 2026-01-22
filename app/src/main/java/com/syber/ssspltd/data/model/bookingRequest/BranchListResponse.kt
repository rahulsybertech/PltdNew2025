package com.syber.ssspltd.data.model.bookingRequest

import com.google.gson.annotations.SerializedName

data class BranchListResponse(
    @SerializedName("BranchDetailList")
    val data: List<BranchList>? = null,

    @SerializedName("ResponseMessage")
    val message: String? = null,

    @SerializedName("ResponseStatus")
    val success: Boolean = false,

    @SerializedName("Error")
    val error: Boolean = false,

    @SerializedName("ResponseCode")
    val responseCode: String? = null
)
