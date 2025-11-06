package com.syber.ssspltd.data.model.dasbord

import com.google.gson.annotations.SerializedName

data class PaymentDashboardResponse(
    @SerializedName("ResponseCode") val responseCode: Int,
    @SerializedName("ResponseStatus") val responseStatus: Boolean,
    @SerializedName("ResponseMessage") val responseMessage: String,
    @SerializedName("graphData") val graphData: List<GraphData> = emptyList(),

    @SerializedName("YourLimit") val yourLimit: String,
    @SerializedName("TemporaryLimit") val temporaryLimit: String,
    @SerializedName("VVPLimit") val vvpLimit: String,
    @SerializedName("BG") val bg: String,
    @SerializedName("TotalLimit") val totalLimit: String,
    @SerializedName("BalanceTillDate") val balanceTillDate: String,
    @SerializedName("Dr") val dr: String,
    @SerializedName("Cr") val cr: String,
    @SerializedName("Interest") val interest: String,
    @SerializedName("Discount") val discount: String,
    @SerializedName("StockinOffice") val stockInOffice: String,
    @SerializedName("Pendingorder") val pendingOrder: String,

    // Sometimes backend sends arrays for StockInOffice & PendingOrder
    @SerializedName("StockInOffice") val stockInOfficeList: List<Any> = emptyList(),
    @SerializedName("PendingOrder") val pendingOrderList: List<Any> = emptyList(),

    @SerializedName("AvaialbleLimit") val availableLimit: String,
    @SerializedName("AvgDays") val avgDays: String,

    @SerializedName("TotalLimitPercent") val totalLimitPercent: Double,
    @SerializedName("DiscountPercent") val discountPercent: Double,
    @SerializedName("InterestPercent") val interestPercent: Double,
    @SerializedName("BalanceTillDatePercent") val balanceTillDatePercent: Double,
    @SerializedName("StockInOfficePercent") val stockInOfficePercent: Double,
    @SerializedName("PendingOrderPercent") val pendingOrderPercent: Double,

    @SerializedName("TotalLimitVisible") val totalLimitVisible: Boolean,
    @SerializedName("DiscountVisible") val discountVisible: Boolean,
    @SerializedName("InterestVisible") val interestVisible: Boolean,
    @SerializedName("BalanceTillDateVisible") val balanceTillDateVisible: Boolean,
    @SerializedName("StockInOfficeVisible") val stockInOfficeVisible: Boolean,
    @SerializedName("PendingOrderVisible") val pendingOrderVisible: Boolean
)

data class GraphData(
    @SerializedName("title") val title: String,
    @SerializedName("value") val value: String,
    @SerializedName("color") val color: String
)
