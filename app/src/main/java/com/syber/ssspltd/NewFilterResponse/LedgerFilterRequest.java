
package com.syber.ssspltd.NewFilterResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LedgerFilterRequest {

    @SerializedName("StartDate")
    private String mStartDate;
    @SerializedName("EndDate")
    private String mEndDate;
    @SerializedName("FILTERTYPE")
    private String mFILTERTYPE;
    @SerializedName("PARTYCODE")
    private String mPARTYCODE;
    @SerializedName("AccountType")
    private List<AccountType> mAccountType;
    @SerializedName("EntryType")
    private List<EntryType> mEntryType;
    @SerializedName("AdjustmentType")
    private List<AdjustmentType> mAdjustmentType;
    @SerializedName("DBNAME")
    private String mDBNAME;

    public LedgerFilterRequest(String mStartDate, String mEndDate, String mFILTERTYPE, String mPARTYCODE, List<AccountType> mAccountType, List<EntryType> mEntryType, List<AdjustmentType> mAdjustmentType, String mDBNAME) {
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
        this.mFILTERTYPE = mFILTERTYPE;
        this.mPARTYCODE = mPARTYCODE;
        this.mAccountType = mAccountType;
        this.mEntryType = mEntryType;
        this.mAdjustmentType = mAdjustmentType;
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

    public List<AccountType> getmAccountType() {
        return mAccountType;
    }

    public void setmAccountType(List<AccountType> mAccountType) {
        this.mAccountType = mAccountType;
    }

    public List<EntryType> getmEntryType() {
        return mEntryType;
    }

    public void setmEntryType(List<EntryType> mEntryType) {
        this.mEntryType = mEntryType;
    }

    public List<AdjustmentType> getmAdjustmentType() {
        return mAdjustmentType;
    }

    public void setmAdjustmentType(List<AdjustmentType> mAdjustmentType) {
        this.mAdjustmentType = mAdjustmentType;
    }

    public String getmDBNAME() {
        return mDBNAME;
    }

    public void setmDBNAME(String mDBNAME) {
        this.mDBNAME = mDBNAME;
    }
}
