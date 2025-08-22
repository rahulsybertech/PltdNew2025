package com.syber.ssspltd.model.booking;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

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
    private String actualCheckInDate;
    private String actualCheckoutDate;
    private String bookingID;
    private List<String> guestIds;
    private String nickName;
    private String nickNameID;
    private String mobileNo;
    private String firmName;
    private String isNewUser;
    private Boolean isStay; // Nullable Boolean

    // Constructor
    public BookingData(String id, String companyID, String branchID, String checkInDate, String checkInTime,
                       String checkoutDate, String checkoutTime, String noOfPerson, String branchName,
                       List<String> guestIds, String nickName, String nickNameID) {
        this.id = id;
        this.companyID = companyID;
        this.branchID = branchID;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkoutDate = checkoutDate;
        this.checkoutTime = checkoutTime;
        this.noOfPerson = noOfPerson;
        this.branchName = branchName;
        this.guestIds = guestIds;
        this.nickName = nickName;
        this.nickNameID = nickNameID;
    }

    // Parcelable Constructor
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
        actualCheckInDate = in.readString();
        actualCheckoutDate = in.readString();
        bookingID = in.readString();
        guestIds = in.createStringArrayList();
        nickName = in.readString();
        nickNameID = in.readString();
        mobileNo = in.readString();
        firmName = in.readString();
        isNewUser = in.readString();

        byte isStayByte = in.readByte();
        if (isStayByte == 0) {
            isStay = null;
        } else if (isStayByte == 1) {
            isStay = true;
        } else {
            isStay = false;
        }
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
        dest.writeString(actualCheckInDate);
        dest.writeString(actualCheckoutDate);
        dest.writeString(bookingID);
        dest.writeStringList(guestIds);
        dest.writeString(nickName);
        dest.writeString(nickNameID);
        dest.writeString(mobileNo);
        dest.writeString(firmName);
        dest.writeString(isNewUser);

        dest.writeByte(isStay == null ? (byte) 0 : (isStay ? (byte) 1 : (byte) 2));
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
    public String getActualCheckInDate() { return actualCheckInDate; }
    public String getActualCheckoutDate() { return actualCheckoutDate; }
    public String getBookingID() { return bookingID; }
    public List<String> getGuestIds() { return guestIds; }
    public String getNickName() { return nickName; }
    public String getNickNameID() { return nickNameID; }
    public String getMobileNo() { return mobileNo; }
    public String getfirmName() { return firmName; }
    public String getIsNewUser() { return isNewUser; }
    public Boolean getIsStay() { return isStay; }

    // Setters (as needed)
    public void setActualCheckInDate(String actualCheckInDate) {
        this.actualCheckInDate = actualCheckInDate;
    }

    public void setActualCheckoutDate(String actualCheckoutDate) {
        this.actualCheckoutDate = actualCheckoutDate;
    }

    public void setIsStay(Boolean isStay) {
        this.isStay = isStay;
    }
}
