package com.syber.ssspltd.data.model.addorder

data class DispatchTypeResponse(
    val data: DispatchTypeData? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val error: Boolean? = null,
    val responsecode: String? = null
)

data class DispatchTypeData(
    val responseMessage: String? = null,
    val allowedAllType: Boolean? = null,
    val dispatchTypeList: List<DispatchTypeItem>? = null
)

data class DispatchTypeItem(
    val id: String? = null,
    val value: String? = null
)
