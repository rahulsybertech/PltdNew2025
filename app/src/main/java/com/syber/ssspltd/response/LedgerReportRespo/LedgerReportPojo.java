
package com.syber.ssspltd.response.LedgerReportRespo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LedgerReportPojo {

    @SerializedName("LedgerReportResult")
    private List<LedgerReportResult> mLedgerReportResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;

    public List<LedgerReportResult> getLedgerReportResult() {
        return mLedgerReportResult;
    }

    public void setLedgerReportResult(List<LedgerReportResult> ledgerReportResult) {
        mLedgerReportResult = ledgerReportResult;
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
