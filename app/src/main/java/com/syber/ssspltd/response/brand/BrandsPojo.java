
package com.syber.ssspltd.response.brand;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BrandsPojo  implements Serializable {

    @SerializedName("BrandInsertingRequestData")
    private List<BrandInsertingRequestDatum> mBrandInsertingRequestData;
    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;

    public List<BrandInsertingRequestDatum> getBrandInsertingRequestData() {
        return mBrandInsertingRequestData;
    }

    public void setBrandInsertingRequestData(List<BrandInsertingRequestDatum> brandInsertingRequestData) {
        mBrandInsertingRequestData = brandInsertingRequestData;
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
