
package com.syber.ssspltd.response.ChooseCatagriesRespo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GetUserListPojo {

    @SerializedName("CustomerListResult")
    private List<CustomerListResult> mCustomerListResult;
    @SerializedName("EmployeeListResult")
    private List<EmployeeListResult> mEmployeeListResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("SupplierListResult")
    private List<SupplierListResult> mSupplierListResult;

    public List<CustomerListResult> getCustomerListResult() {
        return mCustomerListResult;
    }

    public void setCustomerListResult(List<CustomerListResult> customerListResult) {
        mCustomerListResult = customerListResult;
    }

    public List<EmployeeListResult> getEmployeeListResult() {
        return mEmployeeListResult;
    }

    public void setEmployeeListResult(List<EmployeeListResult> employeeListResult) {
        mEmployeeListResult = employeeListResult;
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

    public List<SupplierListResult> getSupplierListResult() {
        return mSupplierListResult;
    }

    public void setSupplierListResult(List<SupplierListResult> supplierListResult) {
        mSupplierListResult = supplierListResult;
    }

}
