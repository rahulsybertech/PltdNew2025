package com.syber.ssspltd.data.model

import com.google.gson.annotations.SerializedName

data class BannerPojo(
    @SerializedName("BannerList")
    val bannerList: List<BannerList>? = null,

    @SerializedName("ResponseCode")
    val responseCode: Long? = null,

    @SerializedName("ResponseMessage")
    val responseMessage: String? = null,

    @SerializedName("ResponseStatus")
    val responseStatus: Boolean? = null
)
