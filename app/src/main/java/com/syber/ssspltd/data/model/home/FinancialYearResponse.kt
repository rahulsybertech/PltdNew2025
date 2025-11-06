package com.syber.ssspltd.data.model.home

data class FinancialYearResponse(
    val data: FinancialYearData?,
    val message: String?,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String?
)

data class FinancialYearData(
    val statusLock: Boolean,
    val supplierOrderStatus: Boolean,
    val blackListReportStatus: Boolean,
    val stayBookingStatus: Boolean,
    val responseMessage: String?,
    val fYearListResult: List<FinancialYearItem>?
)

data class FinancialYearItem(
    val srno: String?,
    val id: String?,
    val fyear: String?,
    val fY_Name: String?,
    val fY_DBName: String?,
    val startDate: String?,
    val endDate: String?,
    val dbname: String?,
    val defaultDB: Boolean
)
