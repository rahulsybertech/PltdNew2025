package com.syber.ssspltd.model.fairOrder.model;

public class SalepartyModelFair {

    private String name;
    private boolean isSelecte;
    private String type;
    private boolean orangeListStatus;
    private  String lockMsg;
    private String ID;
    private String AccountCode;

    public SalepartyModelFair(String name, boolean isSelecte, String type,String AccountCode,String ID) {
        this.name = name;
        this.isSelecte = isSelecte;
        this.type = type;
        this.AccountCode = AccountCode;
        this.ID = ID;
    }

    public SalepartyModelFair(String name, boolean isSelecte, String type, boolean orangeListStatus, String lockMsg) {
        this.name = name;
        this.isSelecte = isSelecte;
        this.type = type;
        this.orangeListStatus = orangeListStatus;
        this.lockMsg = lockMsg;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelecte() {
        return isSelecte;
    }

    public void setSelecte(boolean selecte) {
        isSelecte = selecte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getOrangeListStatus() {
        return orangeListStatus;
    }

    public void setOrangeListStatus(boolean orangeListStatus) {
        this.orangeListStatus = orangeListStatus;
    }

    public String getLockMsg() {
        return lockMsg;
    }

    public void setLockMsg(String lockMsg) {
        this.lockMsg = lockMsg;
    }

    public boolean isOrangeListStatus() {
        return orangeListStatus;
    }

    public String getAccountId() {
        return AccountCode;
    }

    public void setAccountId(String AccountCode) {
        this.AccountCode = AccountCode;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

