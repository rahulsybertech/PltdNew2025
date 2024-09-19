
package com.syber.ssspltd.response.BannerResponse;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BannerListResult {

    @SerializedName("BigImage")
    private String mBigImage;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("Name")
    private String mName;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("SmallImage")
    private String mSmallImage;

    public String getBigImage() {
        return mBigImage;
    }

    public void setBigImage(String bigImage) {
        mBigImage = bigImage;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

    public String getSmallImage() {
        return mSmallImage;
    }

    public void setSmallImage(String smallImage) {
        mSmallImage = smallImage;
    }

}
