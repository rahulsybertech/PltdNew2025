package com.syber.ssspltd.data.model.profile

import com.syber.ssspltd.data.model.bank_detail.BankDetailsResult
import com.syber.ssspltd.data.model.branch.BranchResult

data class CommonApiResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val StatusLock: Boolean,
    val SupplierOrderStatus: Boolean,
    val BlackListReportStatus: Boolean,
    val StayBookingStatus: Boolean,
    val ProfileDetailsResult: ProfileDetailsResult?,
    val BankDetailsResult: List<BankDetailsResult>?,
    val BranchesResult: List<BranchResult>?,
    val BrandInsertingRequestData: Any?,
    val Events: Any?,
    val EventName: String?,
    val EventLogo: String?,
    val Year: String?,
    val Allimage_list: Any?,
    val image_list: Any?
)
