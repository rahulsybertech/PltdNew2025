package com.syber.ssspltd.data.model.home

data class MenuPermissionResponse(
    val data: MenuPermissionData,
    val message: String?,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String?
)

data class MenuPermissionData(
    val statusLock: Boolean,
    val supplierOrderStatus: Boolean,
    val blackListReportStatus: Boolean,
    val stayBookingStatus: Boolean,
    val responseMessage: String?,
    val appMenuPermission: List<AppMenuPermission>
)

data class AppMenuPermission(
    val id: String?,
    val menuID: String,
    val mobileNumber: String?,
    val accountID: String?,
    val permission: Boolean,
    val status: String?,
    val menuType: String,
    val iconImage: String,
    val menuOrder: Int,
    val userType: String
)
