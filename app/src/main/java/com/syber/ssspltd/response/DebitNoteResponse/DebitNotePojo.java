
package com.syber.ssspltd.response.DebitNoteResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DebitNotePojo {

    @SerializedName("DebitNoteReportResult")
    private List<DebitNoteReportResult> mDebitNoteReportResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;

    public List<DebitNoteReportResult> getDebitNoteReportResult() {
        return mDebitNoteReportResult;
    }

    public void setDebitNoteReportResult(List<DebitNoteReportResult> debitNoteReportResult) {
        mDebitNoteReportResult = debitNoteReportResult;
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
