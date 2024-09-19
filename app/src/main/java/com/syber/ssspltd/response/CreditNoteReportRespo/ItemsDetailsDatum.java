
package com.syber.ssspltd.response.CreditNoteReportRespo;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ItemsDetailsDatum {

    @SerializedName("Item")
    private String mItem;
    @SerializedName("NetAmt")
    private String mNetAmt;
    @SerializedName("Qty")
    private String mQty;
    @SerializedName("SRNO")
    private String mSRNO;

    public String getItem() {
        return mItem;
    }

    public void setItem(String item) {
        mItem = item;
    }

    public String getNetAmt() {
        return mNetAmt;
    }

    public void setNetAmt(String netAmt) {
        mNetAmt = netAmt;
    }

    public String getQty() {
        return mQty;
    }

    public void setQty(String qty) {
        mQty = qty;
    }

    public String getSRNO() {
        return mSRNO;
    }

    public void setSRNO(String sRNO) {
        mSRNO = sRNO;
    }

}
