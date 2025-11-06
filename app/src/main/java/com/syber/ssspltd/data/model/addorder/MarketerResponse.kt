package com.syber.ssspltd.data.model.addorder

data class MarketerResponse(
    val data: MarketerData?,
    val message: String?,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String?
)

data class MarketerData(
    val marketerlist: List<Marketer>?
)

data class Marketer(
    val ID: String,
    val MarketerName: String,
    val MCode: String
)


