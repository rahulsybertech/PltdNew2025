
package com.syber.ssspltd.SaleReportResponse;

import java.util.List;

import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class SaleReportResult {

    @SerializedName("BillDate")
    private String mBillDate;
    @SerializedName("BillNo")
    private String mBillNo;
    @SerializedName("BiltyNo")
    private String mBiltyNo;
    @SerializedName("BiltyPDFPath")
    private String mBiltyPDFPath;
    @SerializedName("BranchName")
    private String mBranchName;
    @SerializedName("ID")
    private String mID;
    @SerializedName("LRDate")
    private String mLRDate;
    @SerializedName("PDFPath")
    private String mPDFPath;
    @SerializedName("SAmount")
    private String mSAmount;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("SaleReportSecondaryData")
    private List<SaleReportSecondaryDatum> mSaleReportSecondaryData;
    @SerializedName("SubParty")
    private String mSubParty;
    @SerializedName("Transport")
    private String mTransport;

    @SerializedName("NetAmt")
    private String mNetAmt;
    @SerializedName("PackingVideoURL")
    private String PackingVideoURL;

    private boolean isOpenItem=false;

    public boolean isOpenItem() {
        return isOpenItem;
    }

    public void setOpenItem(boolean openItem) {
        isOpenItem = openItem;
    }

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

    public String getBiltyNo() {
        return mBiltyNo;
    }

    public void setBiltyNo(String biltyNo) {
        mBiltyNo = biltyNo;
    }

    public String getBiltyPDFPath() {
        return mBiltyPDFPath;
    }

    public void setBiltyPDFPath(String biltyPDFPath) {
        mBiltyPDFPath = biltyPDFPath;
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

    public String getLRDate() {
        return mLRDate;
    }

    public void setLRDate(String lRDate) {
        mLRDate = lRDate;
    }

    public String getPDFPath() {
        return mPDFPath;
    }

    public void setPDFPath(String pDFPath) {
        mPDFPath = pDFPath;
    }

    public String getSAmount() {
        return mSAmount;
    }

    public void setSAmount(String sAmount) {
        mSAmount = sAmount;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

    public List<SaleReportSecondaryDatum> getSaleReportSecondaryData() {
        return mSaleReportSecondaryData;
    }

    public void setSaleReportSecondaryData(List<SaleReportSecondaryDatum> saleReportSecondaryData) {
        mSaleReportSecondaryData = saleReportSecondaryData;
    }

    public String getSubParty() {
        return mSubParty;
    }

    public void setSubParty(String subParty) {
        mSubParty = subParty;
    }

    public String getTransport() {
        return mTransport;
    }

    public void setTransport(String transport) {
        mTransport = transport;
    }
    public String getNetAmt() {
        return mNetAmt;
    }

    public void setNetAmt(String mNetAmt) {
        this.mNetAmt = mNetAmt;
    }

    public String getPackingVideoURL() {
        return PackingVideoURL;
    }

    public void setPackingVideoURL(String packingVideoURL) {
        PackingVideoURL = packingVideoURL;
    }
}
