
package com.syber.ssspltd.response.brand;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BranchCode {

    @SerializedName("BranchCodeA")
    private String mBranchCodeA;
    @SerializedName("ID")
    private Long mID;

    public String getBranchCodeA() {
        return mBranchCodeA;
    }

    public void setBranchCodeA(String branchCodeA) {
        mBranchCodeA = branchCodeA;
    }

    public Long getID() {
        return mID;
    }

    public void setID(Long iD) {
        mID = iD;
    }

}
