package com.syber.ssspltd.data.model.debitNoteToCustomer

import com.google.gson.annotations.SerializedName

data class DebitNoteToCustomerReportResult(

    @SerializedName("BillNo")
    val billNo: String? = null,

    @SerializedName("Date")
    val date: String? = null,

    @SerializedName("ItemsDetailsData")
    val itemsDetailsData: List<ItemsDetailsDatum>? = null,

    @SerializedName("PDFPath")
    val pdfPath: String? = null,

    @SerializedName("SRNO")
    val srno: String? = null,

    @SerializedName("SaleBillDate")
    val saleBillDate: String? = null,

    @SerializedName("SaleBillNo")
    val saleBillNo: String? = null,

    @SerializedName("SupplierName")
    val supplierName: String? = null,

    @SerializedName("NetAmt")
    val netAmt: String? = null
)
