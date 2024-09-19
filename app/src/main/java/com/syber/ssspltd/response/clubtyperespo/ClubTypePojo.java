
package com.syber.ssspltd.response.clubtyperespo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ClubTypePojo {

    @SerializedName("Clubdetail")
    private List<Clubdetail> mClubdetail;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("StatusLock")
    private Boolean mStatusLock;
    @SerializedName("SupplierOrderStatus")
    private Boolean mSupplierOrderStatus;

    public List<Clubdetail> getClubdetail() {
        return mClubdetail;
    }

    public void setClubdetail(List<Clubdetail> clubdetail) {
        mClubdetail = clubdetail;
    }

    public Long getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(Long responseCode) {
        mResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        mResponseMessage = responseMessage;
    }

    public Boolean getResponseStatus() {
        return mResponseStatus;
    }

    public void setResponseStatus(Boolean responseStatus) {
        mResponseStatus = responseStatus;
    }

    public Boolean getStatusLock() {
        return mStatusLock;
    }

    public void setStatusLock(Boolean statusLock) {
        mStatusLock = statusLock;
    }

    public Boolean getSupplierOrderStatus() {
        return mSupplierOrderStatus;
    }

    public void setSupplierOrderStatus(Boolean supplierOrderStatus) {
        mSupplierOrderStatus = supplierOrderStatus;
    }

}
