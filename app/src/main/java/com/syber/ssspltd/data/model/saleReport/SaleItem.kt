package com.syber.ssspltd.data.model.saleReport

data class SaleItem(
    val billNo: String,
    val biltyNo: String,
    val subParty: String,
    val transport: String,
    val netAmount: String,
    val date: String,
    val purchases: List<PurchaseDetail>
)

data class PurchaseDetail(
    val purchaseNo: String,
    val supplier: String,
    val pcs: String,
    val amount: String,
    val pSlip: String = ""
)
