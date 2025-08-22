package com.syber.ssspltd.model.fairOrder.model;

import java.util.ArrayList;

import java.io.Serializable;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderRequestNew implements Serializable {
    private int totalQty;
    private int totalAmount;
    private String pcsId;
    private ArrayList<ItemDetailNew> itemDetail; // ✅ Added list of itemDetail

    public OrderRequestNew() {
        this.itemDetail = new ArrayList<>(); // Initialize list to avoid null
    }

    public OrderRequestNew(int totalQty, int totalAmount, String pcsId, ArrayList<ItemDetailNew> itemDetail) {
        this.totalQty = totalQty;
        this.totalAmount = totalAmount;
        this.pcsId = pcsId;
        this.itemDetail = itemDetail;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPcsId() {
        return pcsId;
    }

    public void setPcsId(String pcsId) {
        this.pcsId = pcsId;
    }

    public ArrayList<ItemDetailNew> getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ArrayList<ItemDetailNew> itemDetail) {
        this.itemDetail = itemDetail;
    }

    @Override
    public String toString() {
        return "OrderRequestNew{" +
                "totalQty=" + totalQty +
                ", totalAmount=" + totalAmount +
                ", pcsId='" + pcsId + '\'' +
                ", itemDetail=" + itemDetail +
                '}';
    }


    private boolean isOpenItem=false;

    public boolean isOpenItem() {
        return isOpenItem;
    }

    public void setOpenItem(boolean openItem) {
        isOpenItem = openItem;
    }
}
