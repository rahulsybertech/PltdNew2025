
package com.syber.ssspltd.response.LedgerReportResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class LedgerReportResult {

    @SerializedName("AccountID")
    private String mAccountID;
    @SerializedName("AvgDays")
    private String mAvgDays;
    @SerializedName("BLDescription")
    private String mBLDescription;
    @SerializedName("Balance")
    private String mBalance;
    @SerializedName("BillDate")
    private String mBillDate;
    @SerializedName("CreditAmt")
    private String mCreditAmt;
    @SerializedName("DebitAmt")
    private String mDebitAmt;
    @SerializedName("PDFPath")
    private String mPDFPath;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("SaleStatus")
    private Long mSaleStatus;
    @SerializedName("Tick")
    private String mTick;

    public String getAccountID() {
        return mAccountID;
    }

    public void setAccountID(String accountID) {
        mAccountID = accountID;
    }

    public String getAvgDays() {
        return mAvgDays;
    }

    public void setAvgDays(String avgDays) {
        mAvgDays = avgDays;
    }

    public String getBLDescription() {
        return mBLDescription;
    }

    public void setBLDescription(String bLDescription) {
        mBLDescription = bLDescription;
    }

    public String getBalance() {
        return mBalance;
    }

    public void setBalance(String balance) {
        mBalance = balance;
    }

    public String getBillDate() {
        return mBillDate;
    }

    public void setBillDate(String billDate) {
        mBillDate = billDate;
    }

    public String getCreditAmt() {
        return mCreditAmt;
    }

    public void setCreditAmt(String creditAmt) {
        mCreditAmt = creditAmt;
    }

    public String getDebitAmt() {
        return mDebitAmt;
    }

    public void setDebitAmt(String debitAmt) {
        mDebitAmt = debitAmt;
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

    public Long getSaleStatus() {
        return mSaleStatus;
    }

    public void setSaleStatus(Long saleStatus) {
        mSaleStatus = saleStatus;
    }

    public String getTick() {
        return mTick;
    }

    public void setTick(String tick) {
        mTick = tick;
    }

}
