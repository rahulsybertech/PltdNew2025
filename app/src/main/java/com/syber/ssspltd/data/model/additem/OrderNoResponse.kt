package com.syber.ssspltd.data.model.additem

data class OrderNoResponse(
    val data: OrderData? = null,
    val message: String? = null,
    val success: Boolean = false,
    val error: Boolean = false,
    val responsecode: String? = null
)

data class OrderData(
    val responseMessage: String? = null,
    val orderNo: String? = null,
    val traceIdentifier: String? = null,
    val allowedAllType: Boolean = false,
    val orderStatus: String? = null
)
