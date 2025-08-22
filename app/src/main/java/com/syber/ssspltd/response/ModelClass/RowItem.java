package com.syber.ssspltd.response.ModelClass;

public class RowItem {
    private String mName;
    private String ID;
    private String mPartyCode;
    private String mSRNO;
    private String mUserType;
    private String permissionType;

    public RowItem(String mName, String mPartyCode, String mSRNO, String mUserType,String ID,String permissionType) {
        this.mName = mName;
        this.mPartyCode = mPartyCode;
        this.mSRNO = mSRNO;
        this.mUserType = mUserType;
        this.ID = ID;
        this.permissionType = permissionType;
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

    public String getID() {
        return ID;
    }

    public void setID(String mUserType) {
        this.ID = ID;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }
}
