package com.syber.ssspltd.model.booking.branchlist;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GuestMasterResponse {
    @SerializedName("ResponseCode")
    private int responseCode;

    @SerializedName("ResponseStatus")
    private boolean responseStatus;

    @SerializedName("ResponseMessage")
    private String responseMessage;

    @SerializedName("BookingTime")
    private int bookingTime;

    @SerializedName("GuestMasterDetailList")
    private ArrayList<GuestMasterDetail> guestMasterDetailList;

    // Getters and Setters
    public int getResponseCode() { return responseCode; }
    public void setResponseCode(int responseCode) { this.responseCode = responseCode; }

    public boolean isResponseStatus() { return responseStatus; }
    public void setResponseStatus(boolean responseStatus) { this.responseStatus = responseStatus; }

    public String getResponseMessage() { return responseMessage; }
    public void setResponseMessage(String responseMessage) { this.responseMessage = responseMessage; }

    public int getBookingTime() { return bookingTime; }
    public void setBookingTime(int bookingTime) { this.bookingTime = bookingTime; }

    public ArrayList<GuestMasterDetail> getGuestMasterDetailList() { return guestMasterDetailList; }
    public void setGuestMasterDetailList(ArrayList<GuestMasterDetail> guestMasterDetailList) { this.guestMasterDetailList = guestMasterDetailList; }
}

