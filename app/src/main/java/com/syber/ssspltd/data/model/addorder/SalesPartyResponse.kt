package com.syber.ssspltd.data.model.addorder

data class SalesPartyResponse(
    val data: SalesPartyData? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val error: Boolean? = null,
    val responsecode: String? = null
)

data class SalesPartyData(
    val responseMessage: String? = null,
    val salesPartyNames: ArrayList<SalesParty>? = null,
    val allowedAllType: Boolean? = null
)

data class SalesParty(
    val ID: String? = null,
    val AccountCode: String? = null,
    val Name: String? = null,
    val NickName: String? = null
)

