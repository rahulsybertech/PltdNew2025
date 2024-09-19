
package com.syber.ssspltd.response.SupplierListPojo;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class FilterListResult {

    @SerializedName("FilterName")
    private String mFilterName;
    @SerializedName("SRNO")
    private String mSRNO;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getFilterName() {
        return mFilterName;
    }

    public void setFilterName(String filterName) {
        mFilterName = filterName;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

}
