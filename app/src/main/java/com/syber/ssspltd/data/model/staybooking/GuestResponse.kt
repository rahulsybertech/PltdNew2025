package com.syber.ssspltd.data.model.staybooking

data class GuestResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val BookingTime: Int,
    val GuestMasterDetailList: List<GuestMasterDetail>
)

data class GuestMasterDetail(
    val id: String,
    val companyID: String,
    val accountID: String,
    val customerName: String,
    val guestName: String,
    val frontDocPath: String,
    val backDocPath: String,
    val date: String,
    val updatedDate: String,
    val activeStatus: Boolean,
    val deletedStatus: Boolean,
    val partyCode: String?,
    val nameCount: String?,
    val mobileNo: String?,
    val isNewUser: Boolean
)
