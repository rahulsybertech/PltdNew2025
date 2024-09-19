
package com.syber.ssspltd.response.clubtyperespo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Clubdetail {

    @SerializedName("FeatureImage")
    private String mFeatureImage;
    @SerializedName("IconImage")
    private String mIconImage;

    public String getFeatureImage() {
        return mFeatureImage;
    }

    public void setFeatureImage(String featureImage) {
        mFeatureImage = featureImage;
    }

    public String getIconImage() {
        return mIconImage;
    }

    public void setIconImage(String iconImage) {
        mIconImage = iconImage;
    }

}
