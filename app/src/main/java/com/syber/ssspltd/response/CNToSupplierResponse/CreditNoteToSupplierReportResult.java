
package com.syber.ssspltd.response.CNToSupplierResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CreditNoteToSupplierReportResult {

    @SerializedName("BillNo")
    private String mBillNo;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("ItemsDetailsData")
    private List<ItemsDetailsDatum> mItemsDetailsData;
    @SerializedName("PDFPath")
    private String mPDFPath;
    @SerializedName("PurchaseBillDate")
    private String mPurchaseBillDate;
    @SerializedName("PurchaseBillNo")
    private String mPurchaseBillNo;
    @SerializedName("SRNO")
    private String mSRNO;

    public String getBillNo() {
        return mBillNo;
    }

    public void setBillNo(String billNo) {
        mBillNo = billNo;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
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

    public String getPurchaseBillDate() {
        return mPurchaseBillDate;
    }

    public void setPurchaseBillDate(String purchaseBillDate) {
        mPurchaseBillDate = purchaseBillDate;
    }

    public String getPurchaseBillNo() {
        return mPurchaseBillNo;
    }

    public void setPurchaseBillNo(String purchaseBillNo) {
        mPurchaseBillNo = purchaseBillNo;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

}
