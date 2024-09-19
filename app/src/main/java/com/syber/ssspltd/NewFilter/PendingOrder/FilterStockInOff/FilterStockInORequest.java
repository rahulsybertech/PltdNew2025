
package com.syber.ssspltd.NewFilter.PendingOrder.FilterStockInOff;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FilterStockInORequest {

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
    @SerializedName("DBNAME")
    private String mDBNAME;

    public FilterStockInORequest(String mStartDate, String mEndDate, String mFILTERTYPE, String mPARTYCODE, List<Branch> mBranch, List<SubParty> mSubParty, List<Brand> mBrand, String mDBNAME) {
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
        this.mFILTERTYPE = mFILTERTYPE;
        this.mPARTYCODE = mPARTYCODE;
        this.mBranch = mBranch;
        this.mSubParty = mSubParty;
        this.mBrand = mBrand;
        this.mDBNAME = mDBNAME;
    }

    public List<Branch> getBranch() {
        return mBranch;
    }

    public void setBranch(List<Branch> branch) {
        mBranch = branch;
    }

    public List<Brand> getBrand() {
        return mBrand;
    }

    public void setBrand(List<Brand> brand) {
        mBrand = brand;
    }

    public String getDBNAME() {
        return mDBNAME;
    }

    public void setDBNAME(String dBNAME) {
        mDBNAME = dBNAME;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getFILTERTYPE() {
        return mFILTERTYPE;
    }

    public void setFILTERTYPE(String fILTERTYPE) {
        mFILTERTYPE = fILTERTYPE;
    }

    public String getPARTYCODE() {
        return mPARTYCODE;
    }

    public void setPARTYCODE(String pARTYCODE) {
        mPARTYCODE = pARTYCODE;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public List<SubParty> getSubParty() {
        return mSubParty;
    }

    public void setSubParty(List<SubParty> subParty) {
        mSubParty = subParty;
    }

}
