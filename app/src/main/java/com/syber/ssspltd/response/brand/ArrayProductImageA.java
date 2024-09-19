
package com.syber.ssspltd.response.brand;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ArrayProductImageA  implements Serializable {

    @SerializedName("BrandProductImageA_Type")
    private String mBrandProductImageAType;
    @SerializedName("ID")
    private Long mID;
    @SerializedName("ProductImageA")
    private String mProductImageA;

    public String getBrandProductImageAType() {
        return mBrandProductImageAType;
    }

    public void setBrandProductImageAType(String brandProductImageAType) {
        mBrandProductImageAType = brandProductImageAType;
    }

    public Long getID() {
        return mID;
    }

    public void setID(Long iD) {
        mID = iD;
    }

    public String getProductImageA() {
        return mProductImageA;
    }

    public void setProductImageA(String productImageA) {
        mProductImageA = productImageA;
    }

}
