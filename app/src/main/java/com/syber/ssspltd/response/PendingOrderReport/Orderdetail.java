
package com.syber.ssspltd.response.PendingOrderReport;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Orderdetail implements Serializable {

    @SerializedName("Amount")
    private String mAmount;
    @SerializedName("ItemName")
    private String mItemName;
    @SerializedName("OrderNo")
    private String mOrderNo;
    @SerializedName("Qty")
    private String mQty;
    @SerializedName("SRNO")
    private String mSRNO;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String orderNo) {
        mOrderNo = orderNo;
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
