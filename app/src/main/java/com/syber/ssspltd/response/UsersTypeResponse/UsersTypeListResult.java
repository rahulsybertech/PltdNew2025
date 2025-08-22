
package com.syber.ssspltd.response.UsersTypeResponse;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UsersTypeListResult {

    @SerializedName("Name")
    private String mName;
    @SerializedName("ID")
    private String mID;
    @SerializedName("PartyCode")
    private String mPartyCode;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("UserType")
    private String mUserType;

    @SerializedName("PermissionType")
    private String permissionType;

    private  boolean isSelected=false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }
    public String getmID() {
        return mID;
    }
    public void setmID(String mID) {
        mID = mID;
    }

    public String getPermissionType() {
        return permissionType;
    }
    public void setPermissionType(String permissionType) {
        permissionType = permissionType;
    }

}
