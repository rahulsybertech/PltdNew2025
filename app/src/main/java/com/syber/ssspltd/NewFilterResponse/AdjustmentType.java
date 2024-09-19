
package com.syber.ssspltd.NewFilterResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AdjustmentType {

    @SerializedName("AdjustmentName")
    private String mAdjustmentName;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getAdjustmentName() {
        return mAdjustmentName;
    }

    public void setAdjustmentName(String adjustmentName) {
        mAdjustmentName = adjustmentName;
    }

}
