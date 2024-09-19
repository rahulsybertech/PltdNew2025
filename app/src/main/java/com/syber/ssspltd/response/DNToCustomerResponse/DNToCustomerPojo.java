
package com.syber.ssspltd.response.DNToCustomerResponse;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DNToCustomerPojo {
    @SerializedName("DebitNoteToCustomerReportResult")
    private List<DebitNoteToCustomerReportResult> mDebitNoteToCustomerReportResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;

    public List<DebitNoteToCustomerReportResult> getDebitNoteToCustomerReportResult() {
        return mDebitNoteToCustomerReportResult;
    }

    public void setDebitNoteToCustomerReportResult(List<DebitNoteToCustomerReportResult> debitNoteToCustomerReportResult) {
        mDebitNoteToCustomerReportResult = debitNoteToCustomerReportResult;
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
