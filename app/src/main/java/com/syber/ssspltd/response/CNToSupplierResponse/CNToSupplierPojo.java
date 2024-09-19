
package com.syber.ssspltd.response.CNToSupplierResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CNToSupplierPojo {

    @SerializedName("CreditNoteToSupplierReportResult")
    private List<CreditNoteToSupplierReportResult> mCreditNoteToSupplierReportResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;

    public List<CreditNoteToSupplierReportResult> getCreditNoteToSupplierReportResult() {
        return mCreditNoteToSupplierReportResult;
    }

    public void setCreditNoteToSupplierReportResult(List<CreditNoteToSupplierReportResult> creditNoteToSupplierReportResult) {
        mCreditNoteToSupplierReportResult = creditNoteToSupplierReportResult;
    }

    public Long getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(Long responseCode) {
        mResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        mResponseMessage = responseMessage;
    }

    public Boolean getResponseStatus() {
        return mResponseStatus;
    }

    public void setResponseStatus(Boolean responseStatus) {
        mResponseStatus = responseStatus;
    }

}
