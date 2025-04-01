package com.syber.ssspltd.response;

import com.google.gson.annotations.SerializedName;
import com.syber.ssspltd.model.partyDetails.TransportResponse;

import java.io.Serializable;
import java.util.List;

public class SubpartyModel implements Serializable {

    @SerializedName("SubPartyId")
    private String subPartyId;

    @SerializedName("SubPartyName")
    private String subPartyName;

    @SerializedName("AccountCode")
    private String accountCode;

    @SerializedName("Name")
    private String name;

    @SerializedName("TransportList")
    private List<TransportModel> transportList;

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

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public List<TransportModel> getTransportList() {
        return transportList;
    }

    public void setTransportList(List<TransportModel> transportList) {
        this.transportList = transportList;
    }

    public String getSubPartyId() {
        return subPartyId;
    }

    public void setSubPartyId(String subPartyId) {
        this.subPartyId = subPartyId;
    }

    public String getSubPartyName() {
        return subPartyName;
    }

    public void setSubPartyName(String subPartyName) {
        this.subPartyName = subPartyName;
    }
}
