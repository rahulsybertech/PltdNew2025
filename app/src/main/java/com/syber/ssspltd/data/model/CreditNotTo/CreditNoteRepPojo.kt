package com.syber.ssspltd.data.model.CreditNotTo

import com.google.gson.annotations.SerializedName

data class CreditNoteRepPojo(
    @SerializedName("CreditNoteReportResult")
    val creditNoteReportResult: List<CreditNoteReportResult>? = emptyList(),

    @SerializedName("ResponseCode")
    val responseCode: Long? = null,

    @SerializedName("ResponseMessage")
    val responseMessage: String? = null,

    @SerializedName("ResponseStatus")
    val responseStatus: Boolean? = null
)
