
package com.syber.ssspltd.response.UsersTypeResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UsersTypePoojo {

    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("UsersTypeListResult")
    private List<UsersTypeListResult> mUsersTypeListResult;

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

    public List<UsersTypeListResult> getUsersTypeListResult() {
        return mUsersTypeListResult;
    }

    public void setUsersTypeListResult(List<UsersTypeListResult> usersTypeListResult) {
        mUsersTypeListResult = usersTypeListResult;
    }

}
