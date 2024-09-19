
package com.syber.ssspltd.response.DashboardAllData.StockInOfficeBW;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StockInOfficeDetail {

    @SerializedName("BranchName")
    private String mBranchName;
    @SerializedName("OfficeStockAmt")
    private String mOfficeStockAmt;
    @SerializedName("SrNo")
    private String mSrNo;

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public String getOfficeStockAmt() {
        return mOfficeStockAmt;
    }

    public void setOfficeStockAmt(String officeStockAmt) {
        mOfficeStockAmt = officeStockAmt;
    }

    public String getSrNo() {
        return mSrNo;
    }

    public void setSrNo(String srNo) {
        mSrNo = srNo;
    }

}
