package com.syber.ssspltd.response;

import com.google.gson.annotations.SerializedName;

public class BookingData {

    @SerializedName("id")
    private String id;

    @SerializedName("dbPrefix")
    private String dbPrefix;

    @SerializedName("stayfacility")
    private String stayFacility;

    // Constructor
    public BookingData(String id, String dbPrefix, String stayFacility) {
        this.id = id;
        this.dbPrefix = dbPrefix;
        this.stayFacility = stayFacility;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDbPrefix() {
        return dbPrefix;
    }

    public void setDbPrefix(String dbPrefix) {
        this.dbPrefix = dbPrefix;
    }

    public String getStayFacility() {
        return stayFacility;
    }

    public void setStayFacility(String stayFacility) {
        this.stayFacility = stayFacility;
    }
}
