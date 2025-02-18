package com.syber.ssspltd.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookingResponse {
    
    @SerializedName("BranchDetailList")
    private List<BookingData> data;

    @SerializedName("ResponseMessage")
    private String message;

    @SerializedName("ResponseStatus")
    private boolean success;

    @SerializedName("Error")
    private boolean error;

    @SerializedName("ResponseCode")
    private String responseCode;

    // Getters and Setters
    public List<BookingData> getData() {
        return data;
    }

    public void setData(List<BookingData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
