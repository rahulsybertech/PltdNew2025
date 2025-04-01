package com.syber.ssspltd.model.booking;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingDetails {
    @SerializedName("id")
    private String id;

    @SerializedName("companyID")
    private String companyID;

    @SerializedName("branchID")
    private String branchID;

    @SerializedName("branchName")
    private String branchName;

    @SerializedName("accountID")
    private String accountID;

    @SerializedName("accountName")
    private String accountName;

    @SerializedName("bookingID")
    private int bookingID;

    @SerializedName("date")
    private String date;

    @SerializedName("checkInDate")
    private String checkInDate;

    @SerializedName("checkInTime")
    private String checkInTime;

    @SerializedName("checkoutDate")
    private String checkoutDate;

    @SerializedName("checkoutTime")
    private String checkoutTime;

    @SerializedName("noOfPerson")
    private int noOfPerson;

    @SerializedName("aadhaarNo")
    private String aadhaarNo;

    @SerializedName("contactNo")
    private String contactNo;

    @SerializedName("inTime")
    private String inTime;

    @SerializedName("outTime")
    private String outTime;

    @SerializedName("updatedDate")
    private String updatedDate;

    @SerializedName("deletedStatus")
    private boolean deletedStatus;

    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("partyCode")
    private String partyCode;

    @SerializedName("updateBooking")
    private String updateBooking;

    @SerializedName("guestId")
    private String guestId;

    @SerializedName("guestIds")
    private List<String> guestIds;

    // Getters
    public String getId() { return id; }
    public String getCompanyID() { return companyID; }
    public String getBranchID() { return branchID; }
    public String getBranchName() { return branchName; }
    public String getAccountID() { return accountID; }
    public String getAccountName() { return accountName; }
    public int getBookingID() { return bookingID; }
    public String getDate() { return date; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckInTime() { return checkInTime; }
    public String getCheckoutDate() { return checkoutDate; }
    public String getCheckoutTime() { return checkoutTime; }
    public int getNoOfPerson() { return noOfPerson; }
    public String getAadhaarNo() { return aadhaarNo; }
    public String getContactNo() { return contactNo; }
    public String getInTime() { return inTime; }
    public String getOutTime() { return outTime; }
    public String getUpdatedDate() { return updatedDate; }
    public boolean isDeletedStatus() { return deletedStatus; }
    public String getCreatedBy() { return createdBy; }
    public String getPartyCode() { return partyCode; }
    public String getUpdateBooking() { return updateBooking; }
    public String getGuestId() { return guestId; }
    public List<String> getGuestIds() { return guestIds; }
}

