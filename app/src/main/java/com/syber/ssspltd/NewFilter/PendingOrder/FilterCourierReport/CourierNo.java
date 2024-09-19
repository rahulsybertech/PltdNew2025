
package com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CourierNo {

    @SerializedName("CourierNumber")
    private String mCourierNumber;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCourierNumber() {
        return mCourierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        mCourierNumber = courierNumber;
    }

}
