
package com.syber.ssspltd.response.FinanacialYearListRespon;

import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class FYearListResult {

    @SerializedName("DBNAME")
    private String mDBNAME;
    @SerializedName("FYEAR")
    private String mFYEAR;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("ID")
    private String mID;

    @SerializedName("FY_StartDate")
    private String mFY_StartDate;

    @SerializedName("FY_EndDate")
    private String mFY_EndDate;

    @SerializedName("DEFAULTDB")
    private String mDEFAULTDB;


    private boolean checked = false;


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDBNAME() {
        return mDBNAME;
    }

    public void setDBNAME(String dBNAME) {
        mDBNAME = dBNAME;
    }

    public String getFYEAR() {
        return mFYEAR;
    }

    public void setFYEAR(String fYEAR) {
        mFYEAR = fYEAR;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

    public String getmFY_StartDate() {
        return mFY_StartDate;
    }

    public void setmFY_StartDate(String mFY_StartDate) {
        this.mFY_StartDate = mFY_StartDate;
    }

    public String getmFY_EndDate() {
        return mFY_EndDate;
    }

    public void setmFY_EndDate(String mFY_EndDate) {
        this.mFY_EndDate = mFY_EndDate;
    }

    public String getId() {
        return mID;
    }

    public void setId(String id) {
        this.mID = id;
    }

    public String getmDEFAULTDB() {
        return mDEFAULTDB;
    }

    public void setmDEFAULTDB(String mDEFAULTDB) {
        this.mDEFAULTDB = mDEFAULTDB;
    }
}
