package com.syber.ssspltd.response;

public class MarketerModel {

    private String marketerName;
    private String mCode;

    public MarketerModel(String marketerName, String mCode) {
        this.marketerName = marketerName;
        this.mCode = mCode;
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
}
