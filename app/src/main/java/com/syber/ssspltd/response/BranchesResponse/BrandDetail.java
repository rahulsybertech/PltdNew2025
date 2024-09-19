
package com.syber.ssspltd.response.BranchesResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BrandDetail {

    @SerializedName("BrandImage")
    private String mBrandImage;
    @SerializedName("BrandName")
    private String mBrandName;
    @SerializedName("ID")
    private String mID;

    public String getBrandImage() {
        return mBrandImage;
    }

    public void setBrandImage(String brandImage) {
        mBrandImage = brandImage;
    }

    public String getBrandName() {
        return mBrandName;
    }

    public void setBrandName(String brandName) {
        mBrandName = brandName;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

}
