package com.syber.ssspltd.model.booking;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StayBookingResponse {
    @SerializedName("ResponseCode")
    private int responseCode;

    @SerializedName("ResponseStatus")
    private boolean responseStatus;

    @SerializedName("ResponseMessage")
    private String responseMessage;

    @SerializedName("StayBookingList")
    private List<BookingData> stayBookingList;

    // Getters and Setters
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(boolean responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<BookingData> getStayBookingList() {
        return stayBookingList;
    }

    public void setStayBookingList(List<BookingData> stayBookingList) {
        this.stayBookingList = stayBookingList;
    }
}
