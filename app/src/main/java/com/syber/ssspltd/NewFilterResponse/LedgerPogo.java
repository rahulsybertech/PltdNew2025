
package com.syber.ssspltd.NewFilterResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LedgerPogo {

    @SerializedName("AccountType")
    private List<AccountType> mAccountType;
    @SerializedName("AdjustmentType")
    private List<AdjustmentType> mAdjustmentType;
    @SerializedName("Branch")
    private List<Object> mBranch;
    @SerializedName("Brand")
    private List<Object> mBrand;
    @SerializedName("Courier")
    private List<Object> mCourier;
    @SerializedName("CourierNo")
    private List<Object> mCourierNo;
    @SerializedName("EntryType")
    private List<EntryType> mEntryType;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("AdjustmentTypeCount")
    private String mAdjustmentTypeCount;
    @SerializedName("EntryTypeCount")
    private String mEntryTypeCount;
    @SerializedName("AccountTypeCount")
    private String mAccountTypeCount;
    @SerializedName("Salebill")
    private List<Object> mSalebill;
    @SerializedName("SubParty")
    private List<Object> mSubParty;
    @SerializedName("Transporter")
    private List<Object> mTransporter;

    public LedgerPogo() {
    }

    public List<AccountType> getAccountType() {
        return mAccountType;
    }

    public void setAccountType(List<AccountType> accountType) {
        mAccountType = accountType;
    }

    public List<AdjustmentType> getAdjustmentType() {
        return mAdjustmentType;
    }

    public void setAdjustmentType(List<AdjustmentType> adjustmentType) {
        mAdjustmentType = adjustmentType;
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

    public List<Object> getCourier() {
        return mCourier;
    }

    public void setCourier(List<Object> courier) {
        mCourier = courier;
    }

    public List<Object> getCourierNo() {
        return mCourierNo;
    }

    public void setCourierNo(List<Object> courierNo) {
        mCourierNo = courierNo;
    }

    public List<EntryType> getEntryType() {
        return mEntryType;
    }

    public void setEntryType(List<EntryType> entryType) {
        mEntryType = entryType;
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

    public List<Object> getSalebill() {
        return mSalebill;
    }

    public void setSalebill(List<Object> salebill) {
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

    public String getmAdjustmentTypeCount() {
        return mAdjustmentTypeCount;
    }

    public void setmAdjustmentTypeCount(String mAdjustmentTypeCount) {
        this.mAdjustmentTypeCount = mAdjustmentTypeCount;
    }

    public String getmEntryTypeCount() {
        return mEntryTypeCount;
    }

    public void setmEntryTypeCount(String mEntryTypeCount) {
        this.mEntryTypeCount = mEntryTypeCount;
    }

    public String getmAccountTypeCount() {
        return mAccountTypeCount;
    }

    public void setmAccountTypeCount(String mAccountTypeCount) {
        this.mAccountTypeCount = mAccountTypeCount;
    }


    @Override
    public String toString() {
        return "LedgerPogo{" +
                "mAccountType=" + mAccountType +
                ", mAdjustmentType=" + mAdjustmentType +
                ", mBranch=" + mBranch +
                ", mBrand=" + mBrand +
                ", mCourier=" + mCourier +
                ", mCourierNo=" + mCourierNo +
                ", mEntryType=" + mEntryType +
                ", mResponseCode=" + mResponseCode +
                ", mResponseMessage='" + mResponseMessage + '\'' +
                ", mResponseStatus=" + mResponseStatus +
                ", mSalebill=" + mSalebill +
                ", mSubParty=" + mSubParty +
                ", mTransporter=" + mTransporter +
                '}';
    }
}
