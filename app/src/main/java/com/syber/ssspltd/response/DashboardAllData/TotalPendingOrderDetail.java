
package com.syber.ssspltd.response.DashboardAllData;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TotalPendingOrderDetail {

    @SerializedName("BranchName")
    private String mBranchName;
    @SerializedName("PendingAmt")
    private String mPendingAmt;
    @SerializedName("SrNo")
    private String mSrNo;

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public String getPendingAmt() {
        return mPendingAmt;
    }

    public void setPendingAmt(String pendingAmt) {
        mPendingAmt = pendingAmt;
    }

    public String getSrNo() {
        return mSrNo;
    }

    public void setSrNo(String srNo) {
        mSrNo = srNo;
    }

}
