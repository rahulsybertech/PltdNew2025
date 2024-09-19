package com.syber.ssspltd.response.brand;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")

public class BrandCategoryA implements Serializable {

    @SerializedName("Brand_Category")
    private String mBrand_Category;
    @SerializedName("ID")
    private Long mID;

    public String getBrand_Category() {
        return mBrand_Category;
    }

    public void setBrand_Category(String brand_category) {
        mBrand_Category = brand_category;
    }

    public Long getID() {
        return mID;
    }

    public void setID(Long iD) {
        mID = iD;
    }

}
