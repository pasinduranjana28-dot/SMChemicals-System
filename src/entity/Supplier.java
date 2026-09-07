/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Pasindu
 */
public class Supplier {
    private int supplierId;
    private String supplierName;
    private String phone;
    private String email;
    private String address;

    public Supplier() {
    }

    // Constructor for adding a supplier
    public Supplier(String supplierName, String phone,
                    String email, String address) {
        this.supplierName = supplierName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Constructor for update/display
    public Supplier(int supplierId, String supplierName,
                    String phone, String email, String address) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
