
package com.syber.ssspltd.response.CourierReport;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CourierReportResult {

    @SerializedName("CourierName")
    private String mCourierName;
    @SerializedName("CourierNo")
    private String mCourierNo;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("SaleBillNumber")
    private String mSaleBillNumber;
    @SerializedName("Station")
    private String mStation;

    public String getCourierName() {
        return mCourierName;
    }

    public void setCourierName(String courierName) {
        mCourierName = courierName;
    }

    public String getCourierNo() {
        return mCourierNo;
    }

    public void setCourierNo(String courierNo) {
        mCourierNo = courierNo;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

    public String getSaleBillNumber() {
        return mSaleBillNumber;
    }

    public void setSaleBillNumber(String saleBillNumber) {
        mSaleBillNumber = saleBillNumber;
    }

    public String getStation() {
        return mStation;
    }

    public void setStation(String station) {
        mStation = station;
    }

}
