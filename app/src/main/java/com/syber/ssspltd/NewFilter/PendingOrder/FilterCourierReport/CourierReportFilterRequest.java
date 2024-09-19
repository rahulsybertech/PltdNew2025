package com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport;

import com.google.gson.annotations.SerializedName;
import com.syber.ssspltd.NewFilter.PendingOrder.Branch;
import com.syber.ssspltd.NewFilter.PendingOrder.Brand;
import com.syber.ssspltd.NewFilter.PendingOrder.SubParty;

import java.util.List;

public class CourierReportFilterRequest {

    @SerializedName("StartDate")
    private String mStartDate;
    @SerializedName("EndDate")
    private String mEndDate;
    @SerializedName("FILTERTYPE")
    private String mFILTERTYPE;
    @SerializedName("PARTYCODE")
    private String mPARTYCODE;
    @SerializedName("Courier")
    private List<Courier> mCourier;
    @SerializedName("CourierNo")
    private List<CourierNo> mCourierNo;
    @SerializedName("Salebill")
    private List<Salebill> mSalebill;
    @SerializedName("DBNAME")
    private String mDBNAME;

    public CourierReportFilterRequest(String mStartDate, String mEndDate, String mFILTERTYPE, String mPARTYCODE, List<Courier> mCourier, List<CourierNo> mCourierNo, List<Salebill> mSalebill, String mDBNAME) {
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
        this.mFILTERTYPE = mFILTERTYPE;
        this.mPARTYCODE = mPARTYCODE;
        this.mCourier = mCourier;
        this.mCourierNo = mCourierNo;
        this.mSalebill = mSalebill;
        this.mDBNAME = mDBNAME;
    }


    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public String getmFILTERTYPE() {
        return mFILTERTYPE;
    }

    public void setmFILTERTYPE(String mFILTERTYPE) {
        this.mFILTERTYPE = mFILTERTYPE;
    }

    public String getmPARTYCODE() {
        return mPARTYCODE;
    }

    public void setmPARTYCODE(String mPARTYCODE) {
        this.mPARTYCODE = mPARTYCODE;
    }

    public List<Courier> getmCourier() {
        return mCourier;
    }

    public void setmCourier(List<Courier> mCourier) {
        this.mCourier = mCourier;
    }

    public List<CourierNo> getmCourierNo() {
        return mCourierNo;
    }

    public void setmCourierNo(List<CourierNo> mCourierNo) {
        this.mCourierNo = mCourierNo;
    }

    public List<Salebill> getmSalebill() {
        return mSalebill;
    }

    public void setmSalebill(List<Salebill> mSalebill) {
        this.mSalebill = mSalebill;
    }

    public String getmDBNAME() {
        return mDBNAME;
    }

    public void setmDBNAME(String mDBNAME) {
        this.mDBNAME = mDBNAME;
    }
}
