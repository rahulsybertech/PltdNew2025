package com.syber.ssspltd.data.model.addorder

data class PartyDetailsResponse(
    val data: PartyDetailsData? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val error: Boolean? = null,
    val responsecode: String? = null
)

data class PartyDetailsData(
    val responseMessage: String? = null,
    val avlLimit: Int? = null,
    val avgDays: Int? = null,
    val emailId: String? = null,
    val mobileNo: String? = null,
    val subPartyList: List<SubParty>? = null
)

data class SubParty(
    val SubPartyId: String? = null,
    val SubPartyName: String? = null,
    val AccountCode: String? = null,
    val TransportList: List<Transport>? = null
)



data class Transport(
    val TransportId: String? = null,
    val TransportName: String? = null,
    val DefaultStatus: Boolean? = null,
    val StationList: List<Station>? = null
)


data class Station(
    val StationId: String? = null,
    val StationName: String? = null
)
