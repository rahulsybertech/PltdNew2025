
package com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Transporter {

    @SerializedName("TransporterName")
    private String mTransporterName;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTransporterName() {
        return mTransporterName;
    }

    public void setTransporterName(String transporterName) {
        mTransporterName = transporterName;
    }

}
