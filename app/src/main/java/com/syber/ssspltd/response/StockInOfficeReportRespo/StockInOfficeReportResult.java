
package com.syber.ssspltd.response.StockInOfficeReportRespo;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StockInOfficeReportResult {

    @SerializedName("BillDate")
    private String mBillDate;
    @SerializedName("BillNo")
    private String mBillNo;
    @SerializedName("BranchName")
    private String mBranchName;
    @SerializedName("ID")
    private String mID;
    @SerializedName("PAmount")
    private String mPAmount;
    @SerializedName("Pcs")
    private String mPcs;
    @SerializedName("PurchaseNo")
    private String mPurchaseNo;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("SubParty")
    private String mSubParty;
    @SerializedName("Supplier")
    private String mSupplier;
    @SerializedName("BillStatus")
    private String mBillStatus;
    @SerializedName("StockPDFPath")
    private String mStockPDFPath;
    @SerializedName("Marketer")
    private String mMarketer;

    public String getBillDate() {
        return mBillDate;
    }

    public void setBillDate(String billDate) {
        mBillDate = billDate;
    }

    public String getBillNo() {
        return mBillNo;
    }

    public void setBillNo(String billNo) {
        mBillNo = billNo;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getPAmount() {
        return mPAmount;
    }

    public void setPAmount(String pAmount) {
        mPAmount = pAmount;
    }

    public String getPcs() {
        return mPcs;
    }

    public void setPcs(String pcs) {
        mPcs = pcs;
    }

    public String getPurchaseNo() {
        return mPurchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        mPurchaseNo = purchaseNo;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

    public String getSubParty() {
        return mSubParty;
    }

    public void setSubParty(String subParty) {
        mSubParty = subParty;
    }

    public String getSupplier() {
        return mSupplier;
    }

    public void setSupplier(String supplier) {
        mSupplier = supplier;
    }

    public String getBillStatus() {
        return mBillStatus;
    }

    public void setBillStatus(String BillStatus) {
        mBillStatus = BillStatus;
    }
 public String getStockPDFPath() {
        return mStockPDFPath;
    }

    public void setStockPDFPath(String StockPDFPath) {
        mStockPDFPath = StockPDFPath;
    }
    public String getmMarketer() {
        return mMarketer;
    }

    public void setmMarketer(String mMarketer) {
        this.mMarketer = mMarketer;
    }

}
