package com.syber.ssspltd.data.model.pendingOrder

data class OrderResponse(
    val ResponseCode: Int?,
    val ResponseStatus: Boolean?,
    val ResponseMessage: String?,
    val orderDetails: List<OrderDetail>?,
    val AllowedAllType: Boolean?,
    val OrderStatus: String?
)

data class OrderDetail(
    val RecordId: String?,
    val SaleParty: String?,
    val SupplierName: String?,
    val ItemName: String?,
    val PcsType: String?,
    val SubParty: String?,
    val OrderNo: String?,
    val OrderDate: String?,
    val Amount: Int?,
    val Qty: Int?,
    val PdfPath: String?,
    val ImageList: List<String>?, // or List<ImageModel>? if Image objects
    val OrderType: String?,
    val OrderStatus: String?
)
