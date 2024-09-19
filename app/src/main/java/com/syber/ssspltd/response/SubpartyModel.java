package com.syber.ssspltd.response;

import java.io.Serializable;

public class SubpartyModel implements Serializable {

    private String accountCode,name;

    public SubpartyModel(String name, String accountCode) {
        this.name = name;
        this.accountCode = accountCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCodee(String accountCode) {
        this.accountCode = accountCode;
    }
}
