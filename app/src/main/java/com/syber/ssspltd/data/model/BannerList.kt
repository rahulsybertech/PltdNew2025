package com.syber.ssspltd.data.model

import com.google.gson.annotations.SerializedName

data class BannerList(
    @SerializedName("AppName")
    val appName: String? = null,

    @SerializedName("BannerID")
    val bannerID: Long? = null,

    @SerializedName("BannerTitle")
    val bannerTitle: String? = null,

    @SerializedName("Category")
    val category: String? = null,

    @SerializedName("CurrentStatus")
    val currentStatus: String? = null,

    @SerializedName("Date")
    val date: String? = null,

    @SerializedName("EntryType")
    val entryType: String? = null,

    @SerializedName("ExpiryDate")
    val expiryDate: String? = null,

    @SerializedName("LinkPath")
    val linkPath: String? = null,

    @SerializedName("SRNO")
    val srno: Long? = null,

    @SerializedName("StartDate")
    val startDate: String? = null,

    @SerializedName("Status")
    val status: String? = null,

    @SerializedName("VisibleTo")
    val visibleTo: String? = null
)
