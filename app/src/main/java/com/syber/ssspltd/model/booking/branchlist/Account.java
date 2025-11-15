package com.syber.ssspltd.model.booking.branchlist;

public class Account {
    private String id;
    private String name;
    private String partyType;
    private String nickName;
    private String nickNameID;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNickNameID() {
        return nickNameID;
    }

    public void setNickNameID(String nickNameID) {
        this.nickNameID = nickNameID;
    }

    public String getName() {
        return name;
    }  public String getNickName() {
        return nickName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
