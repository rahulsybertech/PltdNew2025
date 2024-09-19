
package com.syber.ssspltd.response.PendingOrderReport;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PendingOrderPoojo {

    @SerializedName("DefaultEndDate")
    private String mDefaultEndDate;
    @SerializedName("DefaultStartDate")
    private String mDefaultStartDate;
    @SerializedName("Enddate")
    private String mEnddate;
    @SerializedName("PendingOrderReportResult")
    private List<PendingOrderReportResult> mPendingOrderReportResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("StartDate")
    private String mStartDate;
    @SerializedName("StatusLock")
    private Boolean mStatusLock;

    public String getDefaultEndDate() {
        return mDefaultEndDate;
    }

    public void setDefaultEndDate(String defaultEndDate) {
        mDefaultEndDate = defaultEndDate;
    }

    public String getDefaultStartDate() {
        return mDefaultStartDate;
    }

    public void setDefaultStartDate(String defaultStartDate) {
        mDefaultStartDate = defaultStartDate;
    }

    public String getEnddate() {
        return mEnddate;
    }

    public void setEnddate(String enddate) {
        mEnddate = enddate;
    }

    public List<PendingOrderReportResult> getPendingOrderReportResult() {
        return mPendingOrderReportResult;
    }

    public void setPendingOrderReportResult(List<PendingOrderReportResult> pendingOrderReportResult) {
        mPendingOrderReportResult = pendingOrderReportResult;
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

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public Boolean getStatusLock() {
        return mStatusLock;
    }

    public void setStatusLock(Boolean statusLock) {
        mStatusLock = statusLock;
    }

}
