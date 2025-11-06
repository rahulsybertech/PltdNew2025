package com.syber.ssspltd.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("ResponseCode") val responseCode: Int,
    @SerializedName("ResponseStatus") val responseStatus: Boolean,
    @SerializedName("success") val responseMessage: Boolean,
    @SerializedName("ResponseOTP") val responseOTP: String?,
    @SerializedName("userStatus") val userStatus: String?,
    @SerializedName("StartDate") val startDate: String?, // nullable date
    @SerializedName("EndDate") val endDate: String?,
    @SerializedName("Name") val name: String?,
    @SerializedName("AccountDetail") val accountDetail: List<AccountDetail>?, // nested list
    @SerializedName("AccessToken") val accessToken: String?
)

data class AccountDetail(
    @SerializedName("accountName") val accountName: String,
    @SerializedName("mobileNo") val mobileNo: String
)
