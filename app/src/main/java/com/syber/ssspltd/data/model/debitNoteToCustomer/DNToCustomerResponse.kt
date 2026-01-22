package com.syber.ssspltd.data.model.debitNoteToCustomer

import com.google.gson.annotations.SerializedName

data class DNToCustomerResponse(

    @SerializedName("DebitNoteToCustomerReportResult")
    val debitNoteToCustomerReportResult: List<DebitNoteToCustomerReportResult>? = null,

    @SerializedName("ResponseCode")
    val responseCode: Long? = null,

    @SerializedName("ResponseMessage")
    val responseMessage: String? = null,

    @SerializedName("ResponseStatus")
    val responseStatus: Boolean? = null
)


