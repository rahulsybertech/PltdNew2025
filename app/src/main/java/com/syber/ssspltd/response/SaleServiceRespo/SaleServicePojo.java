
package com.syber.ssspltd.response.SaleServiceRespo;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SaleServicePojo {

    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("SaleServiceReportResult")
    private List<SaleServiceReportResult> mSaleServiceReportResult;

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

    public List<SaleServiceReportResult> getSaleServiceReportResult() {
        return mSaleServiceReportResult;
    }

    public void setSaleServiceReportResult(List<SaleServiceReportResult> saleServiceReportResult) {
        mSaleServiceReportResult = saleServiceReportResult;
    }

}
