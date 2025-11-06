package com.syber.ssspltd.data.model.login

data class CheckMobileResponse(
    val data: CheckMobileData,
    val message: String,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String
)

data class CheckMobileData(
    val responseOTP: String,
    val userStatus: String,
    val startDate: String?,        // Nullable
    val endDate: String?,          // Nullable
    val name: String?,             // Nullable
    val accountDetail: List<AccountDetail1>,
    val accessToken: String?       // Nullable
)

data class AccountDetail1(
    val accountName: String,
    val mobileNo: String
)
