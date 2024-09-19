package com.syber.ssspltd.response.ModelClass;

public class RowItem {
    private String mName;
    private String mPartyCode;
    private String mSRNO;
    private String mUserType;

    public RowItem(String mName, String mPartyCode, String mSRNO, String mUserType) {
        this.mName = mName;
        this.mPartyCode = mPartyCode;
        this.mSRNO = mSRNO;
        this.mUserType = mUserType;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPartyCode() {
        return mPartyCode;
    }

    public void setmPartyCode(String mPartyCode) {
        this.mPartyCode = mPartyCode;
    }

    public String getmSRNO() {
        return mSRNO;
    }

    public void setmSRNO(String mSRNO) {
        this.mSRNO = mSRNO;
    }

    public String getmUserType() {
        return mUserType;
    }

    public void setmUserType(String mUserType) {
        this.mUserType = mUserType;
    }
}
