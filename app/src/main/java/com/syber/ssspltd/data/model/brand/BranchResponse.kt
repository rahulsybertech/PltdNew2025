package com.syber.ssspltd.data.model.brand

data class BranchResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val StatusLock: Boolean,
    val SupplierOrderStatus: Boolean,
    val BlackListReportStatus: Boolean,
    val StayBookingStatus: Boolean,
    val BranchesResult: List<Branch>,
    val BrandInsertingRequestData: Any?,
    val Events: Any?,
    val EventName: Any?,
    val EventLogo: Any?,
    val Year: Any?,
    val Allimage_list: Any?,
    val image_list: Any?
)

data class Branch(
    val SRNO: String,
    val ID: String,
    val BranchName: String,
    val BranchAddress: String?,
    val GSTINNO: String?,
    val ManagerName: String?,
    val Stayfacility: String?,
    val MobileNo: String?,
    val EmailId: String?,
    val ContactNo: String?,
    val StateName: String?,
    val BranchStatus: String?,
    val Image_Path: String?,
    val Latitude: String?,
    val Longitude: String?,
    val Branch_Images: String?,
    val Branch_weeklyOff: String?,
    val Branch_Description: String?,
    val BranchCode: String?,
    val BranchManagedBy: String,
    val BrandDetail: List<BrandDetail>
)
data class BrandDetail(
    val ID: String,
    val BrandName: String,
    val BrandImage: String
)

