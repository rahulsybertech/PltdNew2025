
package com.syber.ssspltd.response.BranchBillingResponse;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BranchEmployeesResult {

    @SerializedName("BranchID")
    private String mBranchID;
    @SerializedName("ID")
    private String mID;
    @SerializedName("Image_Path")
    private String mImagePath;
    @SerializedName("MobileNo")
    private String mMobileNo;
    @SerializedName("PersonName")
    private String mPersonName;
    @SerializedName("SRNO")
    private String mSRNO;

    public String getBranchID() {
        return mBranchID;
    }

    public void setBranchID(String branchID) {
        mBranchID = branchID;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getMobileNo() {
        return mMobileNo;
    }

    public void setMobileNo(String mobileNo) {
        mMobileNo = mobileNo;
    }

    public String getPersonName() {
        return mPersonName;
    }

    public void setPersonName(String personName) {
        mPersonName = personName;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

}
