package com.syber.ssspltd.data.model.userType

data class UserTypeResponse(
    val data: UserTypeData,
    val message: String,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String
)

data class UserTypeData(
    val statusLock: Boolean,
    val supplierOrderStatus: Boolean,
    val blackListReportStatus: Boolean,
    val stayBookingStatus: Boolean,
    val responseMessage: String?, // Nullable
    val usersTypeListResult: List<UserTypeItem>
)

data class UserTypeItem(
    val SRNO: String,
    val ID: String,
    val Name: String,
    val UserType: String,
    val PartyCode: String,
    val GroupCode: String
)
