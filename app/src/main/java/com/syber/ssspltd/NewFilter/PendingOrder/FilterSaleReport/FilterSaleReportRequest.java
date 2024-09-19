package com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;


@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FilterSaleReportRequest {
    @SerializedName("StartDate")
    private String mStartDate;
    @SerializedName("EndDate")
    private String mEndDate;
    @SerializedName("FILTERTYPE")
    private String mFILTERTYPE;
    @SerializedName("PARTYCODE")
    private String mPARTYCODE;
    @SerializedName("Branch")
    private List<Branch> mBranch;
    @SerializedName("SubParty")
    private List<SubParty> mSubParty;
    @SerializedName("Brand")
    private List<Brand> mBrand;
    @SerializedName("Transporter")
    private List<Transporter> mTransporter;
    @SerializedName("DBNAME")
    private String mDBNAME;

    public FilterSaleReportRequest(String mStartDate, String mEndDate, String mFILTERTYPE, String mPARTYCODE, List<Branch> mBranch, List<SubParty> mSubParty, List<Brand> mBrand, List<Transporter> mTransporter, String mDBNAME) {
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
        this.mFILTERTYPE = mFILTERTYPE;
        this.mPARTYCODE = mPARTYCODE;
        this.mBranch = mBranch;
        this.mSubParty = mSubParty;
        this.mBrand = mBrand;
        this.mTransporter = mTransporter;
        this.mDBNAME = mDBNAME;
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

    public List<Branch> getmBranch() {
        return mBranch;
    }

    public void setmBranch(List<Branch> mBranch) {
        this.mBranch = mBranch;
    }

    public List<SubParty> getmSubParty() {
        return mSubParty;
    }

    public void setmSubParty(List<SubParty> mSubParty) {
        this.mSubParty = mSubParty;
    }

    public List<Brand> getmBrand() {
        return mBrand;
    }

    public void setmBrand(List<Brand> mBrand) {
        this.mBrand = mBrand;
    }

    public List<Transporter> getmTransporter() {
        return mTransporter;
    }

    public void setmTransporter(List<Transporter> mTransporter) {
        this.mTransporter = mTransporter;
    }

    public String getmDBNAME() {
        return mDBNAME;
    }

    public void setmDBNAME(String mDBNAME) {
        this.mDBNAME = mDBNAME;
    }

}
