
package com.syber.ssspltd.response.GalleryResponse;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class ImageListAppResult implements Serializable {

    @SerializedName("EVENTNAME")
    private String mEVENTNAME;
    @SerializedName("ImageListSecondaryData")
    private List<ImageListSecondaryDatum> mImageListSecondaryData;
    @SerializedName("SRNO")
    private String mSRNO;

    public String getEVENTNAME() {
        return mEVENTNAME;
    }

    public void setEVENTNAME(String eVENTNAME) {
        mEVENTNAME = eVENTNAME;
    }

    public List<ImageListSecondaryDatum> getImageListSecondaryData() {
        return mImageListSecondaryData;
    }

    public void setImageListSecondaryData(List<ImageListSecondaryDatum> imageListSecondaryData) {
        mImageListSecondaryData = imageListSecondaryData;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

}
