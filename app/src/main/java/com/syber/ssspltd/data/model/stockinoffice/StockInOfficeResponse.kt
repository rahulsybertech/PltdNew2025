package com.syber.ssspltd.data.model.stockinoffice


data class StockInOfficeResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val StatusLock: Boolean,
    val SupplierOrderStatus: Boolean,
    val BlackListReportStatus: Boolean,
    val StayBookingStatus: Boolean,
    val StartDate: String? = null,
    val EndDate: String? = null,
    val DefaultStartDate: String? = null,
    val DefaultEndDate: String? = null,
    val StockInOfficeReportResult: List<StockInOfficeReportResult>? = null,
    val BrandInsertingRequestData: Any? = null,
    val Events: Any? = null,
    val EventName: String? = null,
    val EventLogo: String? = null,
    val Year: String? = null,
    val Allimage_list: Any? = null,
    val image_list: Any? = null
)

data class StockInOfficeReportResult(
    val SRNO: String? = null,
    val ID: String? = null,
    val BillNo: String? = null,
    val BillDate: String? = null,
    val SubParty: String? = null,
    val PurchaseNo: String? = null,
    val Supplier: String? = null,
    val Pcs: String? = null,
    val PAmount: String? = null,
    val BranchName: String? = null,
    val BillStatus: String? = null,
    val StockPDFPath: String? = null,
    val Marketer: String? = null,
    val FilterName: String? = null // keeping your old field
)


