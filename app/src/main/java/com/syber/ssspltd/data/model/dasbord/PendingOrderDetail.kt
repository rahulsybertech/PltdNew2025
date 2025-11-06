package com.syber.ssspltd.data.model.dasbord

data class PendingOrderDetail(
    val CompanyCode: String,
    val Amount: String
)
data class InterestDiscountDetails(
    val CompanyCode: String,
    val Amount: String
)
data class StockinOfficeDetail(
    val CompanyCode: String,
    val Amount: String
)

data class ResponseModel(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val PendingOrderDetails: List<PendingOrderDetail>?,
    val InterestDiscountDetails: List<InterestDiscountDetails>?,
    val StockinOfficeDetail: List<StockinOfficeDetail>?
)
