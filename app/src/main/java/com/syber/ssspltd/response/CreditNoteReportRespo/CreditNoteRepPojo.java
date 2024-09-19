
package com.syber.ssspltd.response.CreditNoteReportRespo;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class CreditNoteRepPojo {

    @SerializedName("CreditNoteReportResult")
    private List<CreditNoteReportResult> mCreditNoteReportResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;

    public List<CreditNoteReportResult> getCreditNoteReportResult() {
        return mCreditNoteReportResult;
    }

    public void setCreditNoteReportResult(List<CreditNoteReportResult> creditNoteReportResult) {
        mCreditNoteReportResult = creditNoteReportResult;
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
