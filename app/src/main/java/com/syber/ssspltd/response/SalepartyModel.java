package com.syber.ssspltd.response;

public class SalepartyModel {

    private String name;
    private boolean isSelecte;
    private String type;
    private boolean orangeListStatus;
    private  String lockMsg;
    private String accountId;

    public SalepartyModel(String name, boolean isSelecte, String type,String accountId) {
        this.name = name;
        this.isSelecte = isSelecte;
        this.type = type;
        this.accountId = accountId;
    }

    public SalepartyModel(String name, boolean isSelecte, String type, boolean orangeListStatus, String lockMsg) {
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
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
