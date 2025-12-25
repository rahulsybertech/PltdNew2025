package com.syber.ssspltd.data.model.saleReport

data class SaleReportFilterResponse(
    val ResponseCode: Int?,
    val ResponseStatus: Boolean?,
    val ResponseMessage: String?,
    val StatusLock: Boolean?,
    val SupplierOrderStatus: Boolean?,
    val BlackListReportStatus: Boolean?,
    val StayBookingStatus: Boolean?,

    val AdjustmentType: List<String>?,
    val AdjustmentTypeCount: String?,

    val EntryType: List<String>?,
    val EntryTypeCount: String?,

    val AccountType: List<String>?,
    val AccountTypeCount: String?,

    val Courier: List<String>?,
    val CourierNo: List<String>?,
    val Salebill: List<String>?,

    val Branch: List<BranchItem>?,
    val SubParty: List<SubPartyItem>?,
    val Brand: List<BrandItem>?,
    val Transporter: List<TransporterItem>?,

    val BrandInsertingRequestData: Any?,
    val Events: Any?,
    val EventName: Any?,
    val EventLogo: Any?,
    val Year: Any?,
    val Allimage_list: Any?,
    val image_list: Any?
)

data class BranchItem(
    val BranchName: String?
)

data class SubPartyItem(
    val SubPartyName: String?
)

data class BrandItem(
    val BrandName: String?
)

data class TransporterItem(
    val TransporterName: String?
)
