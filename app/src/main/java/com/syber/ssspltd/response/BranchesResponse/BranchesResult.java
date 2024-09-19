
package com.syber.ssspltd.response.BranchesResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BranchesResult {

    @SerializedName("Branch_Images")
    private String mBranchImages;
    @SerializedName("BranchName")
    private String mBranchName;
    @SerializedName("BrandDetail")
    private List<BrandDetail> mBrandDetail;
    @SerializedName("ID")
    private String mID;
    @SerializedName("SRNO")
    private String mSRNO;

    public String getBranchImages() {
        return mBranchImages;
    }

    public void setBranchImages(String branchImages) {
        mBranchImages = branchImages;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public List<BrandDetail> getBrandDetail() {
        return mBrandDetail;
    }

    public void setBrandDetail(List<BrandDetail> brandDetail) {
        mBrandDetail = brandDetail;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

}
