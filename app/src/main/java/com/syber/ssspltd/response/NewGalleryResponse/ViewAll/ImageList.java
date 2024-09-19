
package com.syber.ssspltd.response.NewGalleryResponse.ViewAll;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ImageList implements Serializable {

    @SerializedName("linktype")
    private String mLinktype;
    @SerializedName("source_url")
    private String mSourceUrl;

    public String getLinktype() {
        return mLinktype;
    }

    public void setLinktype(String linktype) {
        mLinktype = linktype;
    }

    public String getSourceUrl() {
        return mSourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        mSourceUrl = sourceUrl;
    }

}
