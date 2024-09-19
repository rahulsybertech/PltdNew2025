
package com.syber.ssspltd.response.AccountResponse;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AccountPojo {
    @SerializedName("BranchEmployeesResult")
    private List<BranchEmployeesResult> mBranchEmployeesResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;

    public List<BranchEmployeesResult> getBranchEmployeesResult() {
        return mBranchEmployeesResult;
    }

    public void setBranchEmployeesResult(List<BranchEmployeesResult> branchEmployeesResult) {
        mBranchEmployeesResult = branchEmployeesResult;
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


}
