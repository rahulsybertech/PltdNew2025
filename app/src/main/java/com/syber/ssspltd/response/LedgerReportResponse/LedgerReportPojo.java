
package com.syber.ssspltd.response.LedgerReportResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class LedgerReportPojo {

    @SerializedName("ClosingBal")
    private String mClosingBal;
    @SerializedName("LedgerReportResult")
    private List<LedgerReportResult> mLedgerReportResult;
    @SerializedName("OpeningBal")
    private String mOpeningBal;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("StartDate")
    private String mStartDate;
    @SerializedName("Enddate")
    private String mEnddate;
    @SerializedName("DefaultStartDate")
    private String mDefaultStartDate;
    @SerializedName("DefaultEndDate")
    private String mDefaultEndDate;


    public String getEnddate() {
        return mEnddate;
    }

    public void setEnddate(String enddate) {
        mEnddate = enddate;
    }


    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDateBal) {
        mStartDate = startDateBal;
    }


    public String getClosingBal() {
        return mClosingBal;
    }

    public void setClosingBal(String closingBal) {
        mClosingBal = closingBal;
    }

    public List<LedgerReportResult> getLedgerReportResult() {
        return mLedgerReportResult;
    }

    public void setLedgerReportResult(List<LedgerReportResult> ledgerReportResult) {
        mLedgerReportResult = ledgerReportResult;
    }

    public String getOpeningBal() {
        return mOpeningBal;
    }

    public void setOpeningBal(String openingBal) {
        mOpeningBal = openingBal;
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

    public String getmDefaultStartDate() {
        return mDefaultStartDate;
    }

    public void setmDefaultStartDate(String mDefaultStartDate) {
        this.mDefaultStartDate = mDefaultStartDate;
    }

    public String getmDefaultEndDate() {
        return mDefaultEndDate;
    }

    public void setmDefaultEndDate(String mDefaultEndDate) {
        this.mDefaultEndDate = mDefaultEndDate;
    }

}
