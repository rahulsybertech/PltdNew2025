
package com.syber.ssspltd.response.DashboardAllData.StockInOfficeBW;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StockInOfficeBWPojo {

    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("ResultDataKey")
    private String mResultDataKey;
    @SerializedName("StockInOfficeDetail")
    private List<StockInOfficeDetail> mStockInOfficeDetail;

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

    public List<StockInOfficeDetail> getStockInOfficeDetail() {
        return mStockInOfficeDetail;
    }

    public void setStockInOfficeDetail(List<StockInOfficeDetail> stockInOfficeDetail) {
        mStockInOfficeDetail = stockInOfficeDetail;
    }

}
