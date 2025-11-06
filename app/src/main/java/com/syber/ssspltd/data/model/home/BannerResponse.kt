package com.syber.ssspltd.data.model.home

data class BannerResponse(
    val data: BannerData,
    val message: String,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String
)

data class BannerData(
    val statusLock: Boolean,
    val supplierOrderStatus: Boolean,
    val blackListReportStatus: Boolean,
    val stayBookingStatus: Boolean,
    val responseMessage: String?,
    val bannerList: List<BannerItem>
)

data class BannerItem(
    val SRNO: Int,
    val ID: Int,
    val BannerID: Int,
    val BannerTitle: String,
    val Category: String,
    val LinkPath: String,
    val StartDate: String,
    val ExpiryDate: String,
    val VisibleTo: String,
    val Date: String,
    val EntryType: String,
    val Status: String,
    val CurrentStatus: String,
    val AppName: String
)
