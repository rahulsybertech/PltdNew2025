
package com.syber.ssspltd.response.GalleryResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ImageListSecondaryDatum implements Serializable {

    @SerializedName("BannerImage")
    private String mBannerImage;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("ID")
    private String mID;
    @SerializedName("ImageCategory")
    private String mImageCategory;
    @SerializedName("ImageDescription")
    private String mImageDescription;
    @SerializedName("ImageOrder")
    private String mImageOrder;
    @SerializedName("ImgActiveStatus")
    private String mImgActiveStatus;
    @SerializedName("ImgType")
    private String mImgType;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("VideoImgLink")
    private String mVideoImgLink;
    @SerializedName("VideoLink")
    private String mVideoLink;

    public String getBannerImage() {
        return mBannerImage;
    }

    public void setBannerImage(String bannerImage) {
        mBannerImage = bannerImage;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getImageCategory() {
        return mImageCategory;
    }

    public void setImageCategory(String imageCategory) {
        mImageCategory = imageCategory;
    }

    public String getImageDescription() {
        return mImageDescription;
    }

    public void setImageDescription(String imageDescription) {
        mImageDescription = imageDescription;
    }

    public String getImageOrder() {
        return mImageOrder;
    }

    public void setImageOrder(String imageOrder) {
        mImageOrder = imageOrder;
    }

    public String getImgActiveStatus() {
        return mImgActiveStatus;
    }

    public void setImgActiveStatus(String imgActiveStatus) {
        mImgActiveStatus = imgActiveStatus;
    }

    public String getImgType() {
        return mImgType;
    }

    public void setImgType(String imgType) {
        mImgType = imgType;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

    public String getVideoImgLink() {
        return mVideoImgLink;
    }

    public void setVideoImgLink(String videoImgLink) {
        mVideoImgLink = videoImgLink;
    }

    public String getVideoLink() {
        return mVideoLink;
    }

    public void setVideoLink(String videoLink) {
        mVideoLink = videoLink;
    }

}
