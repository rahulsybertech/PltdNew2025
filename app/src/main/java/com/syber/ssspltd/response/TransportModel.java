package com.syber.ssspltd.response;

import com.google.gson.annotations.SerializedName;
import com.syber.ssspltd.model.partyDetails.TransportResponse;

import java.util.List;

public class TransportModel {


    private String tName;

    public TransportModel(String tName) {
        this.tName = tName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }



    @SerializedName("TransportId")
    private String transportId;

    @SerializedName("TransportName")
    private String transportName;

    @SerializedName("DefaultStatus")
    private boolean defaultStatus;

    @SerializedName("StationList")
    private List<StationModel> stationList;


    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public boolean isDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(boolean defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public List<StationModel> getStationList() {
        return stationList;
    }

    public void setStationList(List<StationModel> stationList) {
        this.stationList = stationList;
    }
}
