
package com.syber.ssspltd.response.BannerResponse.Banner;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BannerList {

    @SerializedName("AppName")
    private String mAppName;
    @SerializedName("BannerID")
    private Long mBannerID;
    @SerializedName("BannerTitle")
    private String mBannerTitle;
    @SerializedName("Category")
    private String mCategory;
    @SerializedName("CurrentStatus")
    private String mCurrentStatus;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("EntryType")
    private String mEntryType;
    @SerializedName("ExpiryDate")
    private String mExpiryDate;
    @SerializedName("LinkPath")
    private String mLinkPath;
    @SerializedName("SRNO")
    private Long mSRNO;
    @SerializedName("StartDate")
    private String mStartDate;
    @SerializedName("Status")
    private String mStatus;
    @SerializedName("VisibleTo")
    private String mVisibleTo;

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public Long getBannerID() {
        return mBannerID;
    }

    public void setBannerID(Long bannerID) {
        mBannerID = bannerID;
    }

    public String getBannerTitle() {
        return mBannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        mBannerTitle = bannerTitle;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getCurrentStatus() {
        return mCurrentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        mCurrentStatus = currentStatus;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getEntryType() {
        return mEntryType;
    }

    public void setEntryType(String entryType) {
        mEntryType = entryType;
    }

    public String getExpiryDate() {
        return mExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        mExpiryDate = expiryDate;
    }

    public String getLinkPath() {
        return mLinkPath;
    }

    public void setLinkPath(String linkPath) {
        mLinkPath = linkPath;
    }

    public Long getSRNO() {
        return mSRNO;
    }

    public void setSRNO(Long sRNO) {
        mSRNO = sRNO;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getVisibleTo() {
        return mVisibleTo;
    }

    public void setVisibleTo(String visibleTo) {
        mVisibleTo = visibleTo;
    }

}
