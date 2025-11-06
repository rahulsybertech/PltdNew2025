package com.syber.ssspltd.data.model.userType

data class ApiResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val StatusLock: Boolean,
    val SupplierOrderStatus: Boolean,
    val BlackListReportStatus: Boolean,
    val StayBookingStatus: Boolean,
    val UsersTypeListResult: List<UserType>,
    val BrandInsertingRequestData: Any?,
    val Events: Any?,
    val EventName: Any?,
    val EventLogo: Any?,
    val Year: Any?,
    val Allimage_list: Any?,
    val image_list: Any?
)

data class UserType(
    val SRNO: String,
    val Name: String,
    val UserType: String,
    val PartyCode: String
)
