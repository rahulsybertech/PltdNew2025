
package com.syber.ssspltd.Responses.customer;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CustomerListPojo {

    @SerializedName("BlackListedName")
    private List<BlackListedName> blackListedName;
    @SerializedName("ResponseCode")
    private Long responseCode;
    @SerializedName("ResponseMessage")
    private String responseMessage;
    @SerializedName("ResponseStatus")
    private Boolean responseStatus;
    @SerializedName("StatusLock")
    private Boolean statusLock;
    @SerializedName("SupplierOrderStatus")
    private Boolean supplierOrderStatus;

    public List<BlackListedName> getBlackListedName() {
        return blackListedName;
    }

    public void setBlackListedName(List<BlackListedName> blackListedName) {
        this.blackListedName = blackListedName;
    }

    public Long getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Long responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Boolean getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Boolean responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Boolean getStatusLock() {
        return statusLock;
    }

    public void setStatusLock(Boolean statusLock) {
        this.statusLock = statusLock;
    }

    public Boolean getSupplierOrderStatus() {
        return supplierOrderStatus;
    }

    public void setSupplierOrderStatus(Boolean supplierOrderStatus) {
        this.supplierOrderStatus = supplierOrderStatus;
    }

}
