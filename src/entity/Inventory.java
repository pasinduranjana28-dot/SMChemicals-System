/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;

/**
 *
 * @author Pasindu
 */
public class Inventory {
    private int inventoryId;
    private int productId;
    private int quantity;
    private int reorderLevel;
    private Timestamp lastUpdated;

    public Inventory() {
    }

    public Inventory(int inventoryId, int productId, int quantity,
                     int reorderLevel, Timestamp lastUpdated) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.lastUpdated = lastUpdated;
    }

    public Inventory(int productId, int quantity, int reorderLevel) {
        this.productId = productId;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
