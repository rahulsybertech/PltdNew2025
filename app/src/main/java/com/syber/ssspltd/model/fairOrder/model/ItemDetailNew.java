package com.syber.ssspltd.model.fairOrder.model;

public class ItemDetailNew {
    private String itemId;
    private String itemName;
    private String itemQty;
    private String amount;

    // Default constructor
    public ItemDetailNew() {
    }

    // Parameterized constructor
    public ItemDetailNew(String itemId, String itemName, String itemQty, String amount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.amount = amount;
    }

    // Getters and Setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    // Optional: Useful for debugging
    @Override
    public String toString() {
        return "ItemDetailNew{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemQty='" + itemQty + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
