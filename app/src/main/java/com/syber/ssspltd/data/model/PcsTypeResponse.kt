package com.syber.ssspltd.data.model

import com.syber.ssspltd.data.model.addorder.Item

data class PcsTypeResponse(
    val data: Data?,
    val message: String?,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String?
)

data class Data(
    val responseMessage: String?,
    val pcsType: List<PcsType>?,
    val allowedAllType: Boolean,
    val orderStatus: String?
)

data class PcsType(
    val ID: String? = null,
    val PcsType: String? = null
)


