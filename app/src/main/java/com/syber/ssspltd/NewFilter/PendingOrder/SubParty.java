
package com.syber.ssspltd.NewFilter.PendingOrder;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SubParty {

    @SerializedName("SubPartyName")
    private String mSubPartyName;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSubPartyName() {
        return mSubPartyName;
    }

    public void setSubPartyName(String subPartyName) {
        mSubPartyName = subPartyName;
    }

}
