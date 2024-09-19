
package com.syber.ssspltd.response.NewDashboadRespo.StockPending;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StockInOfficePojo {

    @SerializedName("BalanceTillDatePercent")
    private Double mBalanceTillDatePercent;
    @SerializedName("BalanceTillDateVisible")
    private Boolean mBalanceTillDateVisible;
    @SerializedName("DiscountPercent")
    private Double mDiscountPercent;
    @SerializedName("DiscountVisible")
    private Boolean mDiscountVisible;
    @SerializedName("InterestPercent")
    private Double mInterestPercent;
    @SerializedName("InterestVisible")
    private Boolean mInterestVisible;
    @SerializedName("PendingOrderPercent")
    private Double mPendingOrderPercent;
    @SerializedName("PendingOrderVisible")
    private Boolean mPendingOrderVisible;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("StockInOfficePercent")
    private Double mStockInOfficePercent;
    @SerializedName("StockInOfficeVisible")
    private Boolean mStockInOfficeVisible;
    @SerializedName("StockinOfficeDetail")
    private List<StockinOfficeDetail> mStockinOfficeDetail;
    @SerializedName("TotalLimitPercent")
    private Double mTotalLimitPercent;
    @SerializedName("TotalLimitVisible")
    private Boolean mTotalLimitVisible;

    public Double getBalanceTillDatePercent() {
        return mBalanceTillDatePercent;
    }

    public void setBalanceTillDatePercent(Double balanceTillDatePercent) {
        mBalanceTillDatePercent = balanceTillDatePercent;
    }

    public Boolean getBalanceTillDateVisible() {
        return mBalanceTillDateVisible;
    }

    public void setBalanceTillDateVisible(Boolean balanceTillDateVisible) {
        mBalanceTillDateVisible = balanceTillDateVisible;
    }

    public Double getDiscountPercent() {
        return mDiscountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        mDiscountPercent = discountPercent;
    }

    public Boolean getDiscountVisible() {
        return mDiscountVisible;
    }

    public void setDiscountVisible(Boolean discountVisible) {
        mDiscountVisible = discountVisible;
    }

    public Double getInterestPercent() {
        return mInterestPercent;
    }

    public void setInterestPercent(Double interestPercent) {
        mInterestPercent = interestPercent;
    }

    public Boolean getInterestVisible() {
        return mInterestVisible;
    }

    public void setInterestVisible(Boolean interestVisible) {
        mInterestVisible = interestVisible;
    }

    public Double getPendingOrderPercent() {
        return mPendingOrderPercent;
    }

    public void setPendingOrderPercent(Double pendingOrderPercent) {
        mPendingOrderPercent = pendingOrderPercent;
    }

    public Boolean getPendingOrderVisible() {
        return mPendingOrderVisible;
    }

    public void setPendingOrderVisible(Boolean pendingOrderVisible) {
        mPendingOrderVisible = pendingOrderVisible;
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

    public Double getStockInOfficePercent() {
        return mStockInOfficePercent;
    }

    public void setStockInOfficePercent(Double stockInOfficePercent) {
        mStockInOfficePercent = stockInOfficePercent;
    }

    public Boolean getStockInOfficeVisible() {
        return mStockInOfficeVisible;
    }

    public void setStockInOfficeVisible(Boolean stockInOfficeVisible) {
        mStockInOfficeVisible = stockInOfficeVisible;
    }

    public List<StockinOfficeDetail> getStockinOfficeDetail() {
        return mStockinOfficeDetail;
    }

    public void setStockinOfficeDetail(List<StockinOfficeDetail> stockinOfficeDetail) {
        mStockinOfficeDetail = stockinOfficeDetail;
    }

    public Double getTotalLimitPercent() {
        return mTotalLimitPercent;
    }

    public void setTotalLimitPercent(Double totalLimitPercent) {
        mTotalLimitPercent = totalLimitPercent;
    }

    public Boolean getTotalLimitVisible() {
        return mTotalLimitVisible;
    }

    public void setTotalLimitVisible(Boolean totalLimitVisible) {
        mTotalLimitVisible = totalLimitVisible;
    }

}
