package com.syber.ssspltd.NewFilter.PendingOrder;

import com.google.gson.annotations.SerializedName;
import com.syber.ssspltd.NewFilterResponse.AccountType;
import com.syber.ssspltd.NewFilterResponse.AdjustmentType;
import com.syber.ssspltd.NewFilterResponse.EntryType;

import java.util.List;

public class PendingOrderFilterRequest {

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
    private List<SubParty> mSub_Party;
    @SerializedName("Brand")
    private List<Brand> mBrand;
    @SerializedName("DBNAME")
    private String mDBNAME;

    public PendingOrderFilterRequest(String mStartDate, String mEndDate, String mFILTERTYPE, String mPARTYCODE, List<Branch> mBranch, List<SubParty> mSub_Party, List<Brand> mBrand, String mDBNAME) {
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
        this.mFILTERTYPE = mFILTERTYPE;
        this.mPARTYCODE = mPARTYCODE;
        this.mBranch = mBranch;
        this.mSub_Party = mSub_Party;
        this.mBrand = mBrand;
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

    public List<Branch> getmBranch() {
        return mBranch;
    }

    public void setmBranch(List<Branch> mBranch) {
        this.mBranch = mBranch;
    }

    public List<SubParty> getmSub_Party() {
        return mSub_Party;
    }

    public void setmSub_Party(List<SubParty> mSub_Party) {
        this.mSub_Party = mSub_Party;
    }

    public List<Brand> getmBrand() {
        return mBrand;
    }

    public void setmBrand(List<Brand> mBrand) {
        this.mBrand = mBrand;
    }

    public String getmDBNAME() {
        return mDBNAME;
    }

    public void setmDBNAME(String mDBNAME) {
        this.mDBNAME = mDBNAME;
    }
}
