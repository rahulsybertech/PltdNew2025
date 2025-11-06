package com.syber.ssspltd.data.model.staybooking

data class Booking(
    val visitId: String,
    val branchName: String,
    val checkIn: String,
    val checkOut: String,
    val noOfPerson: Int
)
