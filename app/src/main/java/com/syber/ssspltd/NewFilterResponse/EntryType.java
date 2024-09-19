
package com.syber.ssspltd.NewFilterResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EntryType {

    @SerializedName("EntryTypeName")
    private String mEntryTypeName;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getEntryTypeName() {
        return mEntryTypeName;
    }

    public void setEntryTypeName(String entryTypeName) {
        mEntryTypeName = entryTypeName;
    }

}
