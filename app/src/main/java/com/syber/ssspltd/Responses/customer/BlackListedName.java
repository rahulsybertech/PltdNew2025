
package com.syber.ssspltd.Responses.customer;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BlackListedName {

    @SerializedName("Address")
    private String address;
    @SerializedName("GSTNo")
    private String gSTNo;
    @SerializedName("MobileNo")
    private String mobileNo;
    @SerializedName("Name")
    private String name;
    @SerializedName("OwnerName")
    private String ownerName;
    @SerializedName("SNo")
    private Long sNo;
    @SerializedName("Station")
    private String station;
    @SerializedName("VisibleTo")
    private String VisibleTo;
    @SerializedName("Url")
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String address) {
        this.url = url;
    }


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getVisibleTo() {
        return VisibleTo;
    }

    public void setVisibleTo(String VisibleTo) {
        this.VisibleTo = VisibleTo;
    }

    public String getGSTNo() {
        return gSTNo;
    }

    public void setGSTNo(String gSTNo) {
        this.gSTNo = gSTNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getSNo() {
        return sNo;
    }

    public void setSNo(Long sNo) {
        this.sNo = sNo;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
