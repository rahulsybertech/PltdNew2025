
package com.syber.ssspltd.Responses.GetUserListResponse;

import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class EmployeeListResult {

    @SerializedName("ID")
    private String mID;
    @SerializedName("Name")
    private String mName;
    @SerializedName("PartyCode")
    private String mPartyCode;
    @SerializedName("SRNO")
    private String mSRNO;

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

}
