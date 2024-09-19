
package com.syber.ssspltd.SaleReportResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SaleReportPoojo {

    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("SaleReportResult")
    private List<SaleReportResult> mSaleReportResult;
    @SerializedName("StartDate")
    private String mStartDate;
    @SerializedName("Enddate")
    private String mEnddate;
    @SerializedName("DefaultStartDate")
    private String mDefaultStartDate;
    @SerializedName("DefaultEndDate")
    private String mDefaultEndDate;

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

    public List<SaleReportResult> getSaleReportResult() {
        return mSaleReportResult;
    }

    public void setSaleReportResult(List<SaleReportResult> saleReportResult) {
        mSaleReportResult = saleReportResult;
    }

}
