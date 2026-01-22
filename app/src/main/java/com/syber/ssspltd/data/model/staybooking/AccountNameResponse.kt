package com.syber.ssspltd.data.model.staybooking

data class AccountNameResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val BookingTime: Int,
    val AccountNameList: List<AccountNameItem>
)

data class AccountNameItem(
    val id: String,
    val name: String,
    val partyType: String?,
    val nickNameID: String?,
    val nickName: String?
)

