
package com.syber.ssspltd.response.DashboardAllData;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PendingOrderBWPojo {

    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("ResultDataKey")
    private String mResultDataKey;
    @SerializedName("TotalPendingOrderDetails")
    private List<TotalPendingOrderDetail> mTotalPendingOrderDetails;

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

    public String getResultDataKey() {
        return mResultDataKey;
    }

    public void setResultDataKey(String resultDataKey) {
        mResultDataKey = resultDataKey;
    }

    public List<TotalPendingOrderDetail> getTotalPendingOrderDetails() {
        return mTotalPendingOrderDetails;
    }

    public void setTotalPendingOrderDetails(List<TotalPendingOrderDetail> totalPendingOrderDetails) {
        mTotalPendingOrderDetails = totalPendingOrderDetails;
    }

}
