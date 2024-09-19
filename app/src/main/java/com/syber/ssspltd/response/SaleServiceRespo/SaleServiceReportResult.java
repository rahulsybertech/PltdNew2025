
package com.syber.ssspltd.response.SaleServiceRespo;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SaleServiceReportResult {

    @SerializedName("BillNo")
    private String mBillNo;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("NetAmt")
    private String mNetAmt;
    @SerializedName("PDFPath")
    private String mPDFPath;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("StationName")
    private String mStationName;
    @SerializedName("SubParty")
    private String mSubParty;
    @SerializedName("TransportName")
    private String mTransportName;

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

    public String getNetAmt() {
        return mNetAmt;
    }

    public void setNetAmt(String netAmt) {
        mNetAmt = netAmt;
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

    public String getStationName() {
        return mStationName;
    }

    public void setStationName(String stationName) {
        mStationName = stationName;
    }

    public String getSubParty() {
        return mSubParty;
    }

    public void setSubParty(String subParty) {
        mSubParty = subParty;
    }

    public String getTransportName() {
        return mTransportName;
    }

    public void setTransportName(String transportName) {
        mTransportName = transportName;
    }

}
