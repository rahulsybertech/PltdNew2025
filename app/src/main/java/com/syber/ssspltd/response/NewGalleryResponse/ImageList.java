
package com.syber.ssspltd.response.NewGalleryResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ImageList {

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
