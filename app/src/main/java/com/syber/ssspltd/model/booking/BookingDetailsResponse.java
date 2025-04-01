package com.syber.ssspltd.model.booking;



import com.google.gson.annotations.SerializedName;

public class BookingDetailsResponse {
    @SerializedName("ResponseCode")
    private int responseCode;

    @SerializedName("ResponseStatus")
    private boolean responseStatus;

    @SerializedName("ResponseMessage")
    private String responseMessage;

    @SerializedName("BookingTime")
    private int bookingTime;

    @SerializedName("StayBookingData")
    private BookingDetails stayBookingData;

    // Getters
    public int getResponseCode() { return responseCode; }
    public boolean isResponseStatus() { return responseStatus; }
    public String getResponseMessage() { return responseMessage; }
    public int getBookingTime() { return bookingTime; }
    public BookingDetails getStayBookingData() { return stayBookingData; }
}

