
package com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CourierReportPojo {

    @SerializedName("AccountType")
    private List<Object> mAccountType;
    @SerializedName("AccountTypeCount")
    private String mAccountTypeCount;
    @SerializedName("AdjustmentType")
    private List<Object> mAdjustmentType;
    @SerializedName("AdjustmentTypeCount")
    private String mAdjustmentTypeCount;
    @SerializedName("Branch")
    private List<Object> mBranch;
    @SerializedName("Brand")
    private List<Object> mBrand;
    @SerializedName("Courier")
    private List<Courier> mCourier;
    @SerializedName("CourierNo")
    private List<CourierNo> mCourierNo;
    @SerializedName("EntryType")
    private List<Object> mEntryType;
    @SerializedName("EntryTypeCount")
    private String mEntryTypeCount;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("Salebill")
    private List<Salebill> mSalebill;
    @SerializedName("SubParty")
    private List<Object> mSubParty;
    @SerializedName("Transporter")
    private List<Object> mTransporter;

    public List<Object> getAccountType() {
        return mAccountType;
    }

    public void setAccountType(List<Object> accountType) {
        mAccountType = accountType;
    }

    public String getAccountTypeCount() {
        return mAccountTypeCount;
    }

    public void setAccountTypeCount(String accountTypeCount) {
        mAccountTypeCount = accountTypeCount;
    }

    public List<Object> getAdjustmentType() {
        return mAdjustmentType;
    }

    public void setAdjustmentType(List<Object> adjustmentType) {
        mAdjustmentType = adjustmentType;
    }

    public String getAdjustmentTypeCount() {
        return mAdjustmentTypeCount;
    }

    public void setAdjustmentTypeCount(String adjustmentTypeCount) {
        mAdjustmentTypeCount = adjustmentTypeCount;
    }

    public List<Object> getBranch() {
        return mBranch;
    }

    public void setBranch(List<Object> branch) {
        mBranch = branch;
    }

    public List<Object> getBrand() {
        return mBrand;
    }

    public void setBrand(List<Object> brand) {
        mBrand = brand;
    }

    public List<Courier> getCourier() {
        return mCourier;
    }

    public void setCourier(List<Courier> courier) {
        mCourier = courier;
    }

    public List<CourierNo> getCourierNo() {
        return mCourierNo;
    }

    public void setCourierNo(List<CourierNo> courierNo) {
        mCourierNo = courierNo;
    }

    public List<Object> getEntryType() {
        return mEntryType;
    }

    public void setEntryType(List<Object> entryType) {
        mEntryType = entryType;
    }

    public String getEntryTypeCount() {
        return mEntryTypeCount;
    }

    public void setEntryTypeCount(String entryTypeCount) {
        mEntryTypeCount = entryTypeCount;
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

    public List<Salebill> getSalebill() {
        return mSalebill;
    }

    public void setSalebill(List<Salebill> salebill) {
        mSalebill = salebill;
    }

    public List<Object> getSubParty() {
        return mSubParty;
    }

    public void setSubParty(List<Object> subParty) {
        mSubParty = subParty;
    }

    public List<Object> getTransporter() {
        return mTransporter;
    }

    public void setTransporter(List<Object> transporter) {
        mTransporter = transporter;
    }

}
