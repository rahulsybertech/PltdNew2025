
package com.syber.ssspltd.response.NewGalleryResponse.YearGallery;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Year {

    @SerializedName("TourImages")
    private String mTourImages;
    @SerializedName("Year")
    private Integer mYear;
    @SerializedName("YearID")
    private Long mYearID;
    @SerializedName("Yearimage_list")
    private List<YearimageList> mYearimageList;

    public String getTourImages() {
        return mTourImages;
    }

    public void setTourImages(String tourImages) {
        mTourImages = tourImages;
    }

    public Integer getYear() {
        return mYear;
    }

    public void setYear(Integer year) {
        mYear = year;
    }

    public Long getYearID() {
        return mYearID;
    }

    public void setYearID(Long yearID) {
        mYearID = yearID;
    }

    public List<YearimageList> getYearimageList() {
        return mYearimageList;
    }

    public void setYearimageList(List<YearimageList> yearimageList) {
        mYearimageList = yearimageList;
    }

}
