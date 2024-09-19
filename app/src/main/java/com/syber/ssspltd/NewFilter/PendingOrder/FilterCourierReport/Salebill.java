
package com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Salebill {

    @SerializedName("SalebillNo")
    private String mSalebillNo;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSalebillNo() {
        return mSalebillNo;
    }

    public void setSalebillNo(String salebillNo) {
        mSalebillNo = salebillNo;
    }

}
