package com.syber.ssspltd.model.fairOrder.model;

public class ItemsData {
    public String itemID;
    public String itemName;

    public ItemsData(String itemID, String itemName) {
        this.itemID = itemID;
        this.itemName = itemName;
    }



    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return itemName;
    }
}
