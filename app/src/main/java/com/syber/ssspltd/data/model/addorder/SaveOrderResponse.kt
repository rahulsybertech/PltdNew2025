package com.syber.ssspltd.data.model.addorder

data class SaveOrderResponse(
    val data: ResponseData? = null,
    val message: String? = null,
    val success: Boolean = false,
    val error: Boolean = false,
    val responsecode: String? = null
)

data class ResponseData(
    val responseMessage: String? = null,
    val allowedAllType: Boolean = false,
    val orderStatus: String? = null
)
