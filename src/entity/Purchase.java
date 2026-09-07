/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Pasindu
 */
public class Purchase {
    private int purchaseId;
    private int supplierId;
    private Date purchaseDate;
    private double totalAmount;

    public Purchase() {
    }

    // Constructor for adding a purchase
    public Purchase(int supplierId, Date purchaseDate,
                    double totalAmount) {
        this.supplierId = supplierId;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
    }

    // Constructor for update/display
    public Purchase(int purchaseId, int supplierId,
                    Date purchaseDate, double totalAmount) {
        this.purchaseId = purchaseId;
        this.supplierId = supplierId;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
