package com.syber.ssspltd.data.model.staybooking

data class NickNameResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val BookingTime: Int,
    val NickNameList: List<NickNameItem>
)

data class NickNameItem(
    val id: String,
    val name: String,
    val partyType: String?,
    val nickNameID: String?,
    val nickName: String?
)

