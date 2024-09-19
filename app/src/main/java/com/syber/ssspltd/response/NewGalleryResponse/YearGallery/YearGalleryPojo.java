
package com.syber.ssspltd.response.NewGalleryResponse.YearGallery;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class YearGalleryPojo {

    @SerializedName("ResponseCode")
    private Long mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;
    @SerializedName("ResponseStatus")
    private Boolean mResponseStatus;
    @SerializedName("EventName")
    private String mEventName;
    @SerializedName("EventLogo")
    private String mEventLogo;
    @SerializedName("Year")
    private List<Year> mYear;

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

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String eventName) {
        mEventName = eventName;
    }
    public String getEventLogo() {
        return mEventLogo;
    }

    public void setEventLogo(String eventLogo) {
        mEventLogo = eventLogo;
    }


    public List<Year> getYear() {
        return mYear;
    }

    public void setYear(List<Year> year) {
        mYear = year;
    }

}
