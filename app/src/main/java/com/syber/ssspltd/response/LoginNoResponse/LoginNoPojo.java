
package com.syber.ssspltd.response.LoginNoResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginNoPojo {

    @SerializedName("AccountDetail")
    private List<AccountDetail> mAccountDetail;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseOTP")
    private String mResponseOTP;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("UserStatus")
    private String mUserStatus;

    public List<AccountDetail> getAccountDetail() {
        return mAccountDetail;
    }

    public void setAccountDetail(List<AccountDetail> accountDetail) {
        mAccountDetail = accountDetail;
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

    public String getResponseOTP() {
        return mResponseOTP;
    }

    public void setResponseOTP(String responseOTP) {
        mResponseOTP = responseOTP;
    }

    public Boolean getResponseStatus() {
        return mResponseStatus;
    }

    public void setResponseStatus(Boolean responseStatus) {
        mResponseStatus = responseStatus;
    }

    public String getUserStatus() {
        return mUserStatus;
    }

    public void setUserStatus(String userStatus) {
        mUserStatus = userStatus;
    }

}
