package com.syber.ssspltd.data.model.saleservice

data class SaleServiceReportResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val StatusLock: Boolean,
    val SupplierOrderStatus: Boolean,
    val BlackListReportStatus: Boolean,
    val StayBookingStatus: Boolean,
    val SaleServiceReportResult: List<SaleServiceReportItem>,
    val BrandInsertingRequestData: Any?,
    val Events: Any?,
    val EventName: String?,
    val EventLogo: String?,
    val Year: String?,
    val Allimage_list: Any?,
    val image_list: Any?
)
data class SaleServiceReportItem(
    val SRNO: String,
    val BillNo: String,
    val Date: String,
    val CustomerName: String,
    val SubParty: String,
    val NetAmt: String,
    val StationName: String,
    val TransportName: String,
    val PDFPath: String
)

