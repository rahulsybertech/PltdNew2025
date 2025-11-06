package com.syber.ssspltd.data.model.staybooking

data class StayBookingResponse(
    val ResponseCode: Int,
    val ResponseStatus: Boolean,
    val ResponseMessage: String,
    val BookingTime: Int,
    val StayBookingList: List<StayBookingResult>?
)

data class StayBookingResult(
    val id: String?,
    val companyID: String?,
    val branchID: String?,
    val branchName: String?,
    val accountID: String?,
    val accountName: String?,
    val nickNameID: String?,
    val nickName: String?,
    val messageSent1: String?,
    val messageSent2: String?,
    val messageSent3: String?,
    val bookingID: Int?,
    val date: String?,
    val checkInDate: String?,
    val checkInTime: String?,
    val checkoutDate: String?,
    val checkoutTime: String?,
    val noOfPerson: Int?,
    val aadhaarNo: String?,
    val contactNo: String?,
    val inTime: String?,
    val outTime: String?,
    val updatedDate: String?,
    val deletedStatus: Boolean?,
    val createdBy: String?,
    val partyCode: String?,
    val updateBooking: Boolean?,
    val guestId: String?,
    val actualCheckInDate: String?,
    val actualCheckoutDate: String?,
    val mobileNo: String?,
    val firmName: String?,
    val isNewUser: Boolean?,
    val isStay: Boolean?,
    val messageId: String?,
    val guestIds: String?
)
