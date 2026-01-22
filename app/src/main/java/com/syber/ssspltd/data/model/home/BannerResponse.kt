package com.syber.ssspltd.data.model.home

data class BannerResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val StatusLock: Boolean,
    val SupplierOrderStatus: Boolean,
    val BlackListReportStatus: Boolean,
    val StayBookingStatus: Boolean,
    val BannerList: List<BannerItem>,
    val BrandInsertingRequestData: Any?,
    val Events: Any?,
    val EventName: String?,
    val EventLogo: String?,
    val Year: String?,
    val Allimage_list: Any?,
    val image_list: Any?
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

