package com.syber.ssspltd.response;

public class MarketerModel {

    private String marketerName;
    private String mCode;
    private String ID;

    public MarketerModel(String marketerName, String mCode,String ID) {
        this.marketerName = marketerName;
        this.mCode = mCode;
        this.ID = ID;
    }

    public String getMarketerName() {
        return marketerName;
    }

    public void setMarketerName(String marketerName) {
        this.marketerName = marketerName;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
