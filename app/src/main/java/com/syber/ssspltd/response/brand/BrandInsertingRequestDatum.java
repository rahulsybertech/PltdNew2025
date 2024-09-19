
package com.syber.ssspltd.response.brand;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BrandInsertingRequestDatum implements Serializable {

    @SerializedName("ArrayProductImageA")
    private List<ArrayProductImageA> mArrayProductImageA;
    @SerializedName("BrandCategoryA")
    private List<BrandCategoryA> mBrandCategoryA;
    @SerializedName("Branch_Code")
    private String mBranchCode;
    @SerializedName("BrandDate")
    private String mBrandDate;
    @SerializedName("BrandDescription")
    private String mBrandDescription;
    @SerializedName("BrandID")
    private Long mBrandID;
    @SerializedName("BrandLogo")
    private String mBrandLogo;
    @SerializedName("BrandLogoType")
    private String mBrandLogoType;
    @SerializedName("BrandName")
    private String mBrandName;
        @SerializedName("SNO")
    private Long mSNO;







    public List<ArrayProductImageA> getArrayProductImageA() {
        return mArrayProductImageA;
    }

    public void setArrayProductImageA(List<ArrayProductImageA> arrayProductImageA) {
        mArrayProductImageA = arrayProductImageA;
    }


    public List<BrandCategoryA> getBrandCategoryA() {
        return mBrandCategoryA;
    }

    public void setBrandCategoryA(List<BrandCategoryA> arrayBrandCategoryA) {
        mBrandCategoryA = arrayBrandCategoryA;
    }

    public String getBranchCode() {
        return mBranchCode;
    }

    public void setBranchCode(String branchCode) {
        mBranchCode = branchCode;
    }

    public String getBrandDate() {
        return mBrandDate;
    }

    public void setBrandDate(String brandDate) {
        mBrandDate = brandDate;
    }

    public String getBrandDescription() {
        return mBrandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        mBrandDescription = brandDescription;
    }

    public Long getBrandID() {
        return mBrandID;
    }

    public void setBrandID(Long brandID) {
        mBrandID = brandID;
    }

    public String getBrandLogo() {
        return mBrandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        mBrandLogo = brandLogo;
    }

    public String getBrandLogoType() {
        return mBrandLogoType;
    }

    public void setBrandLogoType(String brandLogoType) {
        mBrandLogoType = brandLogoType;
    }

    public String getBrandName() {
        return mBrandName;
    }

    public void setBrandName(String brandName) {
        mBrandName = brandName;
    }

    public Long getSNO() {
        return mSNO;
    }

    public void setSNO(Long sNO) {
        mSNO = sNO;
    }

}
