
package com.syber.ssspltd.response.ChooseCatagriesRespo;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SupplierListResult {

    @SerializedName("ID")
    private String mID;
    @SerializedName("Name")
    private String mName;
    @SerializedName("PartyCode")
    private String mPartyCode;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("UserMobileNo")
    private String mUserMobileNo;
    @SerializedName("UserType")
    private String mUserType;

    public SupplierListResult(String mID, String mName, String mPartyCode, String mSRNO, String mUserMobileNo, String mUserType) {
        this.mID = mID;
        this.mName = mName;
        this.mPartyCode = mPartyCode;
        this.mSRNO = mSRNO;
        this.mUserMobileNo = mUserMobileNo;
        this.mUserType = mUserType;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPartyCode() {
        return mPartyCode;
    }

    public void setPartyCode(String partyCode) {
        mPartyCode = partyCode;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

    public String getUserMobileNo() {
        return mUserMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        mUserMobileNo = userMobileNo;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}
