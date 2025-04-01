package com.syber.ssspltd.model.booking.branchlist;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GuestMasterDetail implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("companyID")
    private String companyId;

    @SerializedName("accountID")
    private String accountId;

    @SerializedName("customerName")
    private String customerName;

    @SerializedName("guestName")
    private String guestName;

    @SerializedName("frontDocPath")
    private String frontDocPath;

    @SerializedName("backDocPath")
    private String backDocPath;

    @SerializedName("date")
    private String date;

    @SerializedName("updatedDate")
    private String updatedDate;

    @SerializedName("activeStatus")
    private boolean activeStatus;

    @SerializedName("deletedStatus")
    private boolean deletedStatus;

    @SerializedName("partyCode")
    private String partyCode;

    @SerializedName("nameCount")
    private String nameCount;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getFrontDocPath() { return frontDocPath; }
    public void setFrontDocPath(String frontDocPath) { this.frontDocPath = frontDocPath; }

    public String getBackDocPath() { return backDocPath; }
    public void setBackDocPath(String backDocPath) { this.backDocPath = backDocPath; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }

    public boolean isActiveStatus() { return activeStatus; }
    public void setActiveStatus(boolean activeStatus) { this.activeStatus = activeStatus; }

    public boolean isDeletedStatus() { return deletedStatus; }
    public void setDeletedStatus(boolean deletedStatus) { this.deletedStatus = deletedStatus; }

    public String getPartyCode() { return partyCode; }
    public void setPartyCode(String partyCode) { this.partyCode = partyCode; }

    public String getNameCount() { return nameCount; }
    public void setNameCount(String nameCount) { this.nameCount = nameCount; }
}
