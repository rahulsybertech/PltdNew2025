
package com.syber.ssspltd.response.BranchListResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class FilterListPojo {

    @SerializedName("FilterListResult")
    private List<FilterListResult> mFilterListResult;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;

    public List<FilterListResult> getFilterListResult() {
        return mFilterListResult;
    }

    public void setFilterListResult(List<FilterListResult> filterListResult) {
        mFilterListResult = filterListResult;
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
