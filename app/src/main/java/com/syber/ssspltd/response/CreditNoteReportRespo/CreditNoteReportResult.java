
package com.syber.ssspltd.response.CreditNoteReportRespo;

import java.util.List;

import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class CreditNoteReportResult {

    @SerializedName("BillNo")
    private String mBillNo;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("ItemsDetailsData")
    private List<ItemsDetailsDatum> mItemsDetailsData;
    @SerializedName("PDFPath")
    private String mPDFPath;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("SaleBillDate")
    private String mSaleBillDate;
    @SerializedName("SaleBillNo")
    private String mSaleBillNo;
    @SerializedName("SupplierName")
    private String mSupplierName;

    @SerializedName("NetAmt")
    private String mNetAmt;



    public String getBillNo() {
        return mBillNo;
    }

    public void setBillNo(String billNo) {
        mBillNo = billNo;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public List<ItemsDetailsDatum> getItemsDetailsData() {
        return mItemsDetailsData;
    }

    public void setItemsDetailsData(List<ItemsDetailsDatum> itemsDetailsData) {
        mItemsDetailsData = itemsDetailsData;
    }

    public String getPDFPath() {
        return mPDFPath;
    }

    public void setPDFPath(String pDFPath) {
        mPDFPath = pDFPath;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

    public String getSaleBillDate() {
        return mSaleBillDate;
    }

    public void setSaleBillDate(String saleBillDate) {
        mSaleBillDate = saleBillDate;
    }

    public String getSaleBillNo() {
        return mSaleBillNo;
    }

    public void setSaleBillNo(String saleBillNo) {
        mSaleBillNo = saleBillNo;
    }

    public String getSupplierName() {
        return mSupplierName;
    }

    public void setSupplierName(String supplierName) {
        mSupplierName = supplierName;
    }
    public String getNetAmt() {
        return mNetAmt;
    }

    public void setNetAmt(String mNetAmt) {
        this.mNetAmt = mNetAmt;
    }

}
