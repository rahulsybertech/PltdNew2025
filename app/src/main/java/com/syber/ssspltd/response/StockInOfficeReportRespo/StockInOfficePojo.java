
package com.syber.ssspltd.response.StockInOfficeReportRespo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StockInOfficePojo {

    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("StockInOfficeReportResult")
    private List<StockInOfficeReportResult> mStockInOfficeReportResult;
    @SerializedName("StartDate")
    private String mStartDate;
    @SerializedName("Enddate")
    private String mEnddate;
    @SerializedName("DefaultStartDate")
    private String mDefaultStartDate;
    @SerializedName("DefaultEndDate")
    private String mDefaultEndDate;


    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmEnddate() {
        return mEnddate;
    }

    public void setmEnddate(String mEnddate) {
        this.mEnddate = mEnddate;
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

    public List<StockInOfficeReportResult> getStockInOfficeReportResult() {
        return mStockInOfficeReportResult;
    }

    public void setStockInOfficeReportResult(List<StockInOfficeReportResult> stockInOfficeReportResult) {
        mStockInOfficeReportResult = stockInOfficeReportResult;
    }

}
