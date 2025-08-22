
package com.syber.ssspltd.SaleReportResponse;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SaleReportSecondaryDatum {

    @SerializedName("PackingSlipPath")
    private String PackingSlipPath;
    @SerializedName("PAmount")
    private String mPAmount;
    @SerializedName("Pcs")
    private String mPcs;
    @SerializedName("PurPDFPath")
    private String mPurPDFPath;
    @SerializedName("PurchaseNo")
    private String mPurchaseNo;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("Supplier")
    private String mSupplier;

    public String getPAmount() {
        return mPAmount;
    }

    public void setPAmount(String pAmount) {
        mPAmount = pAmount;
    }

    public String getPackingSlipPath() {
        return PackingSlipPath;
    }

    public void setPackingSlipPatht(String pAmount) {
        PackingSlipPath = pAmount;
    }

    public String getPcs() {
        return mPcs;
    }

    public void setPcs(String pcs) {
        mPcs = pcs;
    }

    public String getPurPDFPath() {
        return mPurPDFPath;
    }

    public void setPurPDFPath(String purPDFPath) {
        mPurPDFPath = purPDFPath;
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

    public String getSupplier() {
        return mSupplier;
    }

    public void setSupplier(String supplier) {
        mSupplier = supplier;
    }

}
