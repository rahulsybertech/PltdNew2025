package com.syber.ssspltd.data.model.CreditNotTo

import com.google.gson.annotations.SerializedName
import com.syber.ssspltd.data.model.debitNoteToCustomer.ItemsDetailsDatum

data class CreditNoteReportResult(

    @SerializedName("BillNo")
    val billNo: String? = null,

    @SerializedName("Date")
    val date: String? = null,

    @SerializedName("ItemsDetailsData")
    val itemsDetailsData: List<ItemsDetailsDatum>? = emptyList(),

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

