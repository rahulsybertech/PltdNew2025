
package com.syber.ssspltd.NewFilterResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AccountType {

    @SerializedName("AccountTypeName")
    private String mAccountTypeName;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAccountTypeName() {
        return mAccountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        mAccountTypeName = accountTypeName;
    }

}
