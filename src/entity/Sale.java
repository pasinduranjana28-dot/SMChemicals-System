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
public class Sale {
        private int saleId;
        private int customerId;
        private Date saleDate;
        private double totalAmount;

        // Display fields
        private String customerName;
        private String productName;
        private int quantity;
        private double unitPrice;

        public Sale() {
        }

        public Sale(int customerId, Date saleDate, double totalAmount) {
            this.customerId = customerId;
            this.saleDate = saleDate;
            this.totalAmount = totalAmount;
        }

        public Sale(int saleId, int customerId, Date saleDate, double totalAmount) {
            this.saleId = saleId;
            this.customerId = customerId;
            this.saleDate = saleDate;
            this.totalAmount = totalAmount;
        }

        // Sale ID
        public int getSaleId() {
            return saleId;
        }

        public void setSaleId(int saleId) {
            this.saleId = saleId;
        }

        // Customer ID
        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        // Sale Date
        public Date getSaleDate() {
            return saleDate;
        }

        public void setSaleDate(Date saleDate) {
            this.saleDate = saleDate;
        }

        // Total Amount
        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        // Customer Name
        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        // Product Name
        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        // Quantity
        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        // Unit Price
        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }
}
