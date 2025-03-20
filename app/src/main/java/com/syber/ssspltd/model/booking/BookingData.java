package com.syber.ssspltd.model.booking;

import android.os.Parcel;
import android.os.Parcelable;

public class BookingData implements Parcelable {
    private String id;
    private String companyID;
    private String branchID;
    private String checkInDate;
    private String checkInTime;
    private String checkoutDate;
    private String checkoutTime;
    private String noOfPerson;
    private String branchName;
    private String accountID;
    private String accountName;
    private String bookingID;

    // Constructor
    public BookingData(String id, String companyID, String branchID, String checkInDate, String checkInTime, String checkoutDate, String checkoutTime, String noOfPerson, String branchName) {
        this.id = id;
        this.companyID = companyID;
        this.branchID = branchID;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkoutDate = checkoutDate;
        this.checkoutTime = checkoutTime;
        this.noOfPerson = noOfPerson;
        this.branchName = branchName;
    }

    // Parcelable Implementation
    protected BookingData(Parcel in) {
        id = in.readString();
        companyID = in.readString();
        branchID = in.readString();
        checkInDate = in.readString();
        checkInTime = in.readString();
        checkoutDate = in.readString();
        checkoutTime = in.readString();
        noOfPerson = in.readString();
        branchName = in.readString();
        accountID = in.readString();
        accountName = in.readString();
        bookingID = in.readString();
    }

    public static final Creator<BookingData> CREATOR = new Creator<BookingData>() {
        @Override
        public BookingData createFromParcel(Parcel in) {
            return new BookingData(in);
        }

        @Override
        public BookingData[] newArray(int size) {
            return new BookingData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(companyID);
        dest.writeString(branchID);
        dest.writeString(checkInDate);
        dest.writeString(checkInTime);
        dest.writeString(checkoutDate);
        dest.writeString(checkoutTime);
        dest.writeString(noOfPerson);
        dest.writeString(branchName);
        dest.writeString(accountID);
        dest.writeString(accountName);
        dest.writeString(bookingID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public String getId() { return id; }
    public String getCompanyID() { return companyID; }
    public String getBranchID() { return branchID; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckInTime() { return checkInTime; }
    public String getCheckoutDate() { return checkoutDate; }
    public String getCheckoutTime() { return checkoutTime; }
    public String getNoOfPerson() { return noOfPerson; }
    public String getBranchName() { return branchName; }
    public String getaccountID() { return accountID; }
    public String getaccountName() { return accountName; }
    public String getBookingID() { return bookingID; }
}
