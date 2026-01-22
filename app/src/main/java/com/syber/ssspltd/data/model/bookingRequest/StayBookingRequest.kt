package com.syber.ssspltd.data.model.bookingRequest

data class StayBookingRequest(
    val id: String? = null,
    val accountID: String? = null,
    val branchID: String?,
    val isNewUser: Boolean = false,
    val mobileNo: String? = null,
    val firmName: String? = null,
    val companyID: String? = null,
    val partyCode: String,
    val checkInDate: String,
    val checkInTime: String,
    val checkoutDate: String,
    val checkoutTime: String,
    val isStay: Boolean,
    val guestIds: List<String>?,
    val noOfPerson: String
)
