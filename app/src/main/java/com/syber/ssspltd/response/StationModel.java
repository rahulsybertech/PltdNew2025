package com.syber.ssspltd.response;

import com.google.gson.annotations.SerializedName;

public class StationModel {

    private String sName;

    public StationModel(String sName) {
        this.sName = sName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }


    @SerializedName("StationId")
    private String stationId;

    @SerializedName("StationName")
    private String stationName;

    // Getters and Setters

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
