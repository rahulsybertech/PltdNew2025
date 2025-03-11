package com.syber.ssspltd.model.booking.branchlist;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {
    private int ResponseCode;
    private boolean ResponseStatus;
    private String ResponseMessage;
    private int BookingTime;
    private ArrayList<Account> AccountNameList;
    private ArrayList<NickNameList> NickNameList;

    // Getters and Setters
    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        this.ResponseCode = responseCode;
    }

    public boolean isResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(boolean responseStatus) {
        this.ResponseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.ResponseMessage = responseMessage;
    }

    public int getBookingTime() {
        return BookingTime;
    }

    public void setBookingTime(int bookingTime) {
        this.BookingTime = bookingTime;
    }

    public List<Account> getAccountNameList() {
        return AccountNameList;
    }

    public void setAccountNameList(ArrayList<Account> accountNameList) {
        this.AccountNameList = accountNameList;
    }

    public List<NickNameList> getNickNameList() {
        return NickNameList;
    }

    public void setNickNameList(ArrayList<NickNameList> nickNameList) {
        this.NickNameList = nickNameList;
    }
}
