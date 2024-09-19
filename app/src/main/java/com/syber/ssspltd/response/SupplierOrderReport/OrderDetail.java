
package com.syber.ssspltd.response.SupplierOrderReport;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class OrderDetail implements Serializable {

    @SerializedName("Amount")
    private Double amount;
    @SerializedName("ImageList")
    private List<ImageList> imageList;
    @SerializedName("ItemName")
    private String itemName;
    @SerializedName("OrderDate")
    private String orderDate;
    @SerializedName("OrderNo")
    private String orderNo;
    @SerializedName("OrderStatus")
    private String orderStatus;
    @SerializedName("OrderType")
    private String orderType;
    @SerializedName("PcsType")
    private String pcsType;
    @SerializedName("PdfPath")
    private String pdfPath;
    @SerializedName("Qty")
    private Double qty;
    @SerializedName("SaleParty")
    private String saleParty;
    @SerializedName("SubParty")
    private String subParty;
    @SerializedName("SupplierName")
    private String supplierName;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<ImageList> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageList> imageList) {
        this.imageList = imageList;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPcsType() {
        return pcsType;
    }

    public void setPcsType(String pcsType) {
        this.pcsType = pcsType;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getSaleParty() {
        return saleParty;
    }

    public void setSaleParty(String saleParty) {
        this.saleParty = saleParty;
    }

    public String getSubParty() {
        return subParty;
    }

    public void setSubParty(String subParty) {
        this.subParty = subParty;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

}
