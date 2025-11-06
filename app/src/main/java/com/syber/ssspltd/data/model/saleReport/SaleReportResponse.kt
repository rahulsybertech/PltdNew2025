package com.syber.ssspltd.data.model.saleReport

data class SaleReportResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val StatusLock: Boolean,
    val SupplierOrderStatus: Boolean,
    val BlackListReportStatus: Boolean,
    val StayBookingStatus: Boolean,
    val StartDate: String,
    val EndDate: String,
    val DefaultStartDate: String,
    val DefaultEndDate: String,
    val SaleReportResult: List<SaleReportResult>?,
    val BrandInsertingRequestData: Any?,
    val Events: Any?,
    val EventName: Any?,
    val EventLogo: Any?,
    val Year: Any?,
    val Allimage_list: Any?,
    val image_list: Any?
)

data class SaleReportResult(
    val SRNO: String,
    val ID: String,
    val BillNo: String,
    val BillDate: String,
    val BiltyNo: String,
    val SubParty: String,
    val Transport: String,
    val LRDate: String,
    val SAmount: String,
    val PDFPath: String,
    val BranchName: String,
    val BiltyPDFPath: String,
    val PackingVideoURL: String,
    val SaleReportSecondaryData: List<SaleReportSecondaryData>?
)

data class SaleReportSecondaryData(
    val SRNO: String,
    val PurchaseNo: String,
    val Supplier: String,
    val Pcs: String,
    val PAmount: String,
    val PurPDFPath: String,
    val PackingSlipPath: String
)
