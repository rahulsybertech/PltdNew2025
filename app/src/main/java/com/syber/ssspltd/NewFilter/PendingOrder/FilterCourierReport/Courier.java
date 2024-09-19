
package com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Courier {

    @SerializedName("CourierName")
    private String mCourierName;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCourierName() {
        return mCourierName;
    }

    public void setCourierName(String courierName) {
        mCourierName = courierName;
    }

}
