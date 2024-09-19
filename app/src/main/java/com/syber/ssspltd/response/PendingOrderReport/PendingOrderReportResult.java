
package com.syber.ssspltd.response.PendingOrderReport;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PendingOrderReportResult implements Serializable {

    @SerializedName("Amount")
    private String mAmount;
    @SerializedName("BranchName")
    private String mBranchName;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("DeliveryDate")
    private String mDeliveryDate;
    @SerializedName("Items")
    private String mItems;
    @SerializedName("Marketer")
    private String mMarketer;
    @SerializedName("NetAmt")
    private String mNetAmt;
    @SerializedName("OrderNo")
    private String mOrderNo;
    @SerializedName("Orderdetail")
    private List<Orderdetail> mOrderdetail;
    @SerializedName("Pcs")
    private String mPcs;
    @SerializedName("Qty")
    private String mQty;
    @SerializedName("SRNO")
    private String mSRNO;
    @SerializedName("SubParty")
    private String mSubParty;
    @SerializedName("Supplier")
    private String mSupplier;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDeliveryDate() {
        return mDeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        mDeliveryDate = deliveryDate;
    }

    public String getItems() {
        return mItems;
    }

    public void setItems(String items) {
        mItems = items;
    }

    public String getMarketer() {
        return mMarketer;
    }

    public void setMarketer(String marketer) {
        mMarketer = marketer;
    }

    public String getNetAmt() {
        return mNetAmt;
    }

    public void setNetAmt(String netAmt) {
        mNetAmt = netAmt;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String orderNo) {
        mOrderNo = orderNo;
    }

    public List<Orderdetail> getOrderdetail() {
        return mOrderdetail;
    }

    public void setOrderdetail(List<Orderdetail> orderdetail) {
        mOrderdetail = orderdetail;
    }

    public String getPcs() {
        return mPcs;
    }

    public void setPcs(String pcs) {
        mPcs = pcs;
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

    public String getSubParty() {
        return mSubParty;
    }

    public void setSubParty(String subParty) {
        mSubParty = subParty;
    }

    public String getSupplier() {
        return mSupplier;
    }

    public void setSupplier(String supplier) {
        mSupplier = supplier;
    }

}
