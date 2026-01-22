package com.syber.ssspltd.data.model.saleReport

data class SaleReportFilterResponse(
    val ResponseCode: Int?,
    val ResponseStatus: Boolean?,
    val ResponseMessage: String?,
    val StatusLock: Boolean?,
    val SupplierOrderStatus: Boolean?,
    val BlackListReportStatus: Boolean?,
    val StayBookingStatus: Boolean?,

    val AdjustmentType: List<AdjustmentType>?,
    val AdjustmentTypeCount: String?,

    val EntryType: List<EntryType>?,
    val EntryTypeCount: String?,

    val AccountType: List<AccountType1>?,
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
data class AdjustmentType(
    val AdjustmentName: String?
)
data class EntryType(
    val EntryTypeName: String?
)
data class AccountType1(
    val AccountTypeName: String?
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
