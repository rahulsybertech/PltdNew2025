package com.syber.ssspltd.data.model.ledger

data class LedgerResponse(
    val ResponseCode: Int? = null,
    val ResponseStatus: Boolean? = null,
    val ResponseMessage: String? = null,
    val StatusLock: Boolean? = null,
    val SupplierOrderStatus: Boolean? = null,
    val BlackListReportStatus: Boolean? = null,
    val StayBookingStatus: Boolean? = null,
    val StartDate: String? = null,
    val EndDate: String? = null,
    val OpeningBal: String? = null,
    val ClosingBal: String? = null,
    val DefaultStartDate: String? = null,
    val DefaultEndDate: String? = null,
    val LedgerReportResult: List<LedgerReportItem>? = null,
    val BrandInsertingRequestData: Any? = null,
    val Events: Any? = null,
    val EventName: String? = null,
    val EventLogo: String? = null,
    val Year: String? = null,
    val Allimage_list: Any? = null,
    val image_list: Any? = null
) {


    data class LedgerReportItem(
        val SRNO: String? = null,
        val BillDate: String? = null,
        val AccountID: String? = null,
        val Opening: String? = null,
        val DebitAmt: String? = null,
        val CreditAmt: String? = null,
        val Balance: String? = null,
        val BLDescription: String? = null,
        val Tick: String? = null,
        val SaleStatus: Int? = null,
        val AvgDays: String? = null,
        val PDFPath: String? = null,
        val TotalBalanceAmt: String? = null
    )
}
