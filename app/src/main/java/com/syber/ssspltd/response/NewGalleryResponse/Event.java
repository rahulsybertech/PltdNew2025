
package com.syber.ssspltd.response.NewGalleryResponse;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Event implements Serializable {

    @SerializedName("EventID")
    private Long mEventID;
    @SerializedName("EventName")
    private String mEventName;
    @SerializedName("image_list")
    private List<ImageList> mImageList;

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String eventName) {
        mEventName = eventName;
    }

    public List<ImageList> getImageList() {
        return mImageList;
    }

    public void setImageList(List<ImageList> imageList) {
        mImageList = imageList;
    }

}
