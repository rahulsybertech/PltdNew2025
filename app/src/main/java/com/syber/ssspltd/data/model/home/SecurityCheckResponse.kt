package com.syber.ssspltd.data.model.home

data class SecurityCheckResponse(
    val data: SecurityCheckData,
    val message: String,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String
)

data class SecurityCheckData(
    val statusLock: Boolean,
    val supplierOrderStatus: Boolean,
    val blackListReportStatus: Boolean,
    val stayBookingStatus: Boolean,
    val responseMessage: String,
    val clubType: String,
    val taxType: String,
    val lockMsg: String,
    val securityCheckReportResult: List<SecurityCheckReport>
)

data class SecurityCheckReport(
    val SRNO: String,
    val Count: String,
    val CurrentBalance: String
)
