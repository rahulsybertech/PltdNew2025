package com.syber.ssspltd.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DispatchResponse {

    @SerializedName("ResponseCode")
    private int responseCode;

    @SerializedName("ResponseStatus")
    private boolean responseStatus;

    @SerializedName("ResponseMessage")
    private String responseMessage;

    @SerializedName("AllowedAllType")
    private boolean allowedAllType;

    @SerializedName("DispatchTypeList")
    private List<DispatchType> dispatchTypeList;

    public int getResponseCode() {
        return responseCode;
    }

    public boolean isResponseStatus() {
        return responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public boolean isAllowedAllType() {
        return allowedAllType;
    }

    public List<DispatchType> getDispatchTypeList() {
        return dispatchTypeList;
    }

    public static class DispatchType {

        @SerializedName("id")
        private String id;

        @SerializedName("value")
        private String value;

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }
    }
}
