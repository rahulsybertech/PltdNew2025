package com.syber.ssspltd.data.model.debitNoteToCustomer

import com.google.gson.annotations.SerializedName

data class ItemsDetailsDatum(

    @SerializedName("Item")
    val item: String? = null,

    @SerializedName("NetAmt")
    val netAmt: String? = null,

    @SerializedName("Qty")
    val qty: String? = null,

    @SerializedName("SRNO")
    val srno: String? = null
)
