
package com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SaleReportPojo {

    @SerializedName("AccountType")
    private List<Object> mAccountType;
    @SerializedName("AccountTypeCount")
    private String mAccountTypeCount;
    @SerializedName("AdjustmentType")
    private List<Object> mAdjustmentType;
    @SerializedName("AdjustmentTypeCount")
    private String mAdjustmentTypeCount;
    @SerializedName("Branch")
    private List<Branch> mBranch;
    @SerializedName("Brand")
    private List<Brand> mBrand;
    @SerializedName("Courier")
    private List<Object> mCourier;
    @SerializedName("CourierNo")
    private List<Object> mCourierNo;
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
    private List<Object> mSalebill;
    @SerializedName("SubParty")
    private List<SubParty> mSubParty;
    @SerializedName("Transporter")
    private List<Transporter> mTransporter;

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

    public List<Branch> getBranch() {
        return mBranch;
    }

    public void setBranch(List<Branch> branch) {
        mBranch = branch;
    }

    public List<Brand> getBrand() {
        return mBrand;
    }

    public void setBrand(List<Brand> brand) {
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

    public List<Object> getSalebill() {
        return mSalebill;
    }

    public void setSalebill(List<Object> salebill) {
        mSalebill = salebill;
    }

    public List<SubParty> getSubParty() {
        return mSubParty;
    }

    public void setSubParty(List<SubParty> subParty) {
        mSubParty = subParty;
    }

    public List<Transporter> getTransporter() {
        return mTransporter;
    }

    public void setTransporter(List<Transporter> transporter) {
        mTransporter = transporter;
    }

}
