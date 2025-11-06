package com.syber.ssspltd.data.model.login

data class CheckOtpResponse(
    val data: CheckOtpData,
    val message: String,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String
)

data class CheckOtpData(
    val responseOTP: String?,        // Nullable
    val userStatus: String?,         // Nullable
    val startDate: String?,          // Nullable
    val endDate: String?,            // Nullable
    val name: String?,               // Nullable
    val accountDetail: List<AccountDetail>?,  // Nullable
    val accessToken: String          // Required
)

