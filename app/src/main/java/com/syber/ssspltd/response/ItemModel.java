package com.syber.ssspltd.response;

public class ItemModel {
    private String itemName;
    private String itemID;

    public ItemModel(String itemName,String itemID) {
        this.itemName = itemName;
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
