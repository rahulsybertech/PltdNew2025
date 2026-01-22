package com.syber.ssspltd.data.model.brand

data class BrandResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val StatusLock: Boolean,
    val SupplierOrderStatus: Boolean,
    val BlackListReportStatus: Boolean,
    val StayBookingStatus: Boolean,
    val BrandInsertingRequestData: List<BrandItem>,
    val Events: Any?,
    val EventName: Any?,
    val EventLogo: Any?,
    val Year: Any?,
    val Allimage_list: Any?,
    val image_list: Any?
)

data class BrandItem(
    val SNO: Int,
    val BrandID: Int,
    val BRANCHID: Int?,
    val BrandName: String,
    val BrandDescription: String,
    val BrandLogo: String,
    val BrandLogoType: String,
    val BrandDate: String,
    val Branch_Code: String,
    val BranchCode: List<BranchCode>,
    val DeletedBranchCode: List<Any>,
    val ArrayProductImageA: List<ProductImage>,
    val DeletedArrayProductImageA: List<Any>,
    val BrandCategoryA: List<BrandCategory>,
    val DeletedBrandCategoryA: List<Any>
)
data class BranchCode(
    val ID: Int,
    val BranchCodeA: String
)
data class ProductImage(
    val ID: Int,
    val ProductImageA: String,
    val BrandProductImageA_Type: String
)
data class BrandCategory(
    val ID: Int,
    val Brand_Category: String
)

