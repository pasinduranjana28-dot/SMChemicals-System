/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import entity.Purchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.PurchaseItem;

/**
 *
 * @author Pasindu
 */
public class PurchaseDAO {
     // ADD PURCHASE
    public boolean addPurchase(Purchase purchase) {

        String sql = "INSERT INTO purchases "
                   + "(supplier_id, purchase_date, total_amount) "
                   + "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, purchase.getSupplierId());
            pst.setDate(2, purchase.getPurchaseDate());
            pst.setDouble(3, purchase.getTotalAmount());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET ALL PURCHASES
    public List<Purchase> getAllPurchases() {

        List<Purchase> purchaseList = new ArrayList<>();

        String sql = "SELECT * FROM purchases";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Purchase purchase = new Purchase();

                purchase.setPurchaseId(
                        rs.getInt("purchase_id"));

                purchase.setSupplierId(
                        rs.getInt("supplier_id"));

                purchase.setPurchaseDate(
                        rs.getDate("purchase_date"));

                purchase.setTotalAmount(
                        rs.getDouble("total_amount"));

                purchaseList.add(purchase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchaseList;
    }

    // UPDATE PURCHASE
    public boolean updatePurchase(Purchase purchase) {

        String sql = "UPDATE purchases SET "
                   + "supplier_id = ?, "
                   + "purchase_date = ?, "
                   + "total_amount = ? "
                   + "WHERE purchase_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, purchase.getSupplierId());
            pst.setDate(2, purchase.getPurchaseDate());
            pst.setDouble(3, purchase.getTotalAmount());
            pst.setInt(4, purchase.getPurchaseId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE PURCHASE
    public boolean deletePurchase(int purchaseId) {

        String getItemSQL =
            "SELECT product_id, quantity "
          + "FROM purchase_items "
          + "WHERE purchase_id = ?";

    String decreaseInventorySQL =
            "UPDATE inventory SET "
          + "quantity = quantity - ?, "
          + "last_updated = CURRENT_TIMESTAMP "
          + "WHERE product_id = ?";

    String deleteItemSQL =
            "DELETE FROM purchase_items WHERE purchase_id = ?";

    String deletePurchaseSQL =
            "DELETE FROM purchases WHERE purchase_id = ?";

    java.sql.Connection con = null;

    try {

        con = database.DBConnection.getConnection();
        con.setAutoCommit(false);

        int productId;
        int quantity;

        // 1. Get the purchase item
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(getItemSQL)) {

            pst.setInt(1, purchaseId);

            try (java.sql.ResultSet rs =
                         pst.executeQuery()) {

                if (!rs.next()) {
                    con.rollback();
                    return false;
                }

                productId = rs.getInt("product_id");
                quantity = rs.getInt("quantity");
            }
        }

        // 2. Remove purchased quantity from inventory
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(decreaseInventorySQL)) {

            pst.setInt(1, quantity);
            pst.setInt(2, productId);

            pst.executeUpdate();
        }

        // 3. Delete purchase item
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(deleteItemSQL)) {

            pst.setInt(1, purchaseId);
            pst.executeUpdate();
        }

        // 4. Delete purchase
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(deletePurchaseSQL)) {

            pst.setInt(1, purchaseId);

            int rows = pst.executeUpdate();

            if (rows == 0) {
                con.rollback();
                return false;
            }
        }

        con.commit();

        return true;

    } catch (java.sql.SQLException e) {

        e.printStackTrace();

        if (con != null) {
            try {
                con.rollback();
            } catch (java.sql.SQLException rollbackError) {
                rollbackError.printStackTrace();
            }
        }

        return false;

    } finally {

        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    }
    
    //Get supplier details to combo box
    
    public java.util.Map<Integer, String> getSupplierList() {

    java.util.Map<Integer, String> supplierMap =
            new java.util.LinkedHashMap<>();

    String sql = "SELECT supplier_id, supplier_name "
               + "FROM suppliers";

    try (java.sql.Connection con =
                database.DBConnection.getConnection();
         java.sql.PreparedStatement pst =
                con.prepareStatement(sql);
         java.sql.ResultSet rs =
                pst.executeQuery()) {

        while (rs.next()) {

            supplierMap.put(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name")
            );
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }

    return supplierMap;
}
    
    //Get product details to combo box
    
    public java.util.Map<Integer, String> getProductList() {

    java.util.Map<Integer, String> productMap =
            new java.util.LinkedHashMap<>();

    String sql = "SELECT product_id, product_name "
               + "FROM products";

    try (java.sql.Connection con =
                database.DBConnection.getConnection();
         java.sql.PreparedStatement pst =
                con.prepareStatement(sql);
         java.sql.ResultSet rs =
                pst.executeQuery()) {

        while (rs.next()) {

            productMap.put(
                    rs.getInt("product_id"),
                    rs.getString("product_name")
            );
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }

    return productMap;
}
    
    // Get the unit price to the text field
    
    public double getProductPrice(int productId) {

    String sql = "SELECT unit_price FROM products WHERE product_id = ?";

    try (java.sql.Connection con =
                database.DBConnection.getConnection();
         java.sql.PreparedStatement pst =
                con.prepareStatement(sql)) {

        pst.setInt(1, productId);

        try (java.sql.ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("unit_price");
            }
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }

    return 0.0;
}
    
    // Add purchase to database
    
        public boolean addPurchaseWithItem(
        int supplierId,
        java.sql.Date purchaseDate,
        double totalAmount,
        int productId,
        int quantity,
        double unitPrice,
        double subtotal) {

    String purchaseSQL =
            "INSERT INTO purchases "
          + "(supplier_id, purchase_date, total_amount) "
          + "VALUES (?, ?, ?)";

    String itemSQL =
            "INSERT INTO purchase_items "
          + "(purchase_id, product_id, quantity, unit_price, subtotal) "
          + "VALUES (?, ?, ?, ?, ?)";

    String checkInventorySQL =
            "SELECT inventory_id FROM inventory WHERE product_id = ?";

    String updateInventorySQL =
            "UPDATE inventory SET "
          + "quantity = quantity + ?, "
          + "last_updated = CURRENT_TIMESTAMP "
          + "WHERE product_id = ?";

    String insertInventorySQL =
            "INSERT INTO inventory "
          + "(product_id, quantity, reorder_level, last_updated) "
          + "VALUES (?, ?, 0, CURRENT_TIMESTAMP)";

    java.sql.Connection con = null;

    try {

        con = database.DBConnection.getConnection();

        con.setAutoCommit(false);

        int purchaseId;

        // 1. Add purchase
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(
                             purchaseSQL,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, supplierId);
            pst.setDate(2, purchaseDate);
            pst.setDouble(3, totalAmount);

            pst.executeUpdate();

            try (java.sql.ResultSet rs =
                         pst.getGeneratedKeys()) {

                if (!rs.next()) {
                    con.rollback();
                    return false;
                }

                purchaseId = rs.getInt(1);
            }
        }

        // 2. Add purchase item
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(itemSQL)) {

            pst.setInt(1, purchaseId);
            pst.setInt(2, productId);
            pst.setInt(3, quantity);
            pst.setDouble(4, unitPrice);
            pst.setDouble(5, subtotal);

            pst.executeUpdate();
        }

        // 3. Check inventory
        boolean inventoryExists = false;

        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(checkInventorySQL)) {

            pst.setInt(1, productId);

            try (java.sql.ResultSet rs =
                         pst.executeQuery()) {

                if (rs.next()) {
                    inventoryExists = true;
                }
            }
        }

        // 4. Update or create inventory
        if (inventoryExists) {

            try (java.sql.PreparedStatement pst =
                         con.prepareStatement(updateInventorySQL)) {

                pst.setInt(1, quantity);
                pst.setInt(2, productId);

                pst.executeUpdate();
            }

        } else {

            try (java.sql.PreparedStatement pst =
                         con.prepareStatement(insertInventorySQL)) {

                pst.setInt(1, productId);
                pst.setInt(2, quantity);

                pst.executeUpdate();
            }
        }

        // 5. Save everything
        con.commit();

        return true;

    } catch (java.sql.SQLException e) {

        e.printStackTrace();

        if (con != null) {
            try {
                con.rollback();
            } catch (java.sql.SQLException rollbackError) {
                rollbackError.printStackTrace();
            }
        }

        return false;

    } finally {

        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
        
        // Update purchace
        
        public boolean updatePurchaseWithItem(
        int purchaseId,
        int supplierId,
        java.sql.Date purchaseDate,
        double totalAmount,
        int productId,
        int quantity,
        double unitPrice,
        double subtotal) {

    String getOldItemSQL =
            "SELECT product_id, quantity "
          + "FROM purchase_items "
          + "WHERE purchase_id = ?";

    String purchaseSQL =
            "UPDATE purchases SET "
          + "supplier_id = ?, "
          + "purchase_date = ?, "
          + "total_amount = ? "
          + "WHERE purchase_id = ?";

    String itemSQL =
            "UPDATE purchase_items SET "
          + "product_id = ?, "
          + "quantity = ?, "
          + "unit_price = ?, "
          + "subtotal = ? "
          + "WHERE purchase_id = ?";

    String decreaseInventorySQL =
            "UPDATE inventory SET "
          + "quantity = quantity - ?, "
          + "last_updated = CURRENT_TIMESTAMP "
          + "WHERE product_id = ?";

    String increaseInventorySQL =
            "UPDATE inventory SET "
          + "quantity = quantity + ?, "
          + "last_updated = CURRENT_TIMESTAMP "
          + "WHERE product_id = ?";

    java.sql.Connection con = null;

    try {

        con = database.DBConnection.getConnection();
        con.setAutoCommit(false);

        int oldProductId;
        int oldQuantity;

        // 1. Get old purchase item
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(getOldItemSQL)) {

            pst.setInt(1, purchaseId);

            try (java.sql.ResultSet rs =
                         pst.executeQuery()) {

                if (!rs.next()) {
                    con.rollback();
                    return false;
                }

                oldProductId = rs.getInt("product_id");
                oldQuantity = rs.getInt("quantity");
            }
        }

        // 2. Remove old quantity from inventory
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(decreaseInventorySQL)) {

            pst.setInt(1, oldQuantity);
            pst.setInt(2, oldProductId);

            pst.executeUpdate();
        }

        // 3. Add new quantity to inventory
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(increaseInventorySQL)) {

            pst.setInt(1, quantity);
            pst.setInt(2, productId);

            pst.executeUpdate();
        }

        // 4. Update purchase
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(purchaseSQL)) {

            pst.setInt(1, supplierId);
            pst.setDate(2, purchaseDate);
            pst.setDouble(3, totalAmount);
            pst.setInt(4, purchaseId);

            pst.executeUpdate();
        }

        // 5. Update purchase item
        try (java.sql.PreparedStatement pst =
                     con.prepareStatement(itemSQL)) {

            pst.setInt(1, productId);
            pst.setInt(2, quantity);
            pst.setDouble(3, unitPrice);
            pst.setDouble(4, subtotal);
            pst.setInt(5, purchaseId);

            pst.executeUpdate();
        }

        con.commit();

        return true;

    } catch (java.sql.SQLException e) {

        e.printStackTrace();

        if (con != null) {
            try {
                con.rollback();
            } catch (java.sql.SQLException rollbackError) {
                rollbackError.printStackTrace();
            }
        }

        return false;

    } finally {

        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
    }
}   
            
        // GET PURCHASE BY ID
        public Purchase getPurchaseById(int purchaseId) {

            String sql = "SELECT * FROM purchases WHERE purchase_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, purchaseId);

                try (ResultSet rs = pst.executeQuery()) {

                    if (rs.next()) {

                        Purchase purchase = new Purchase();

                        purchase.setPurchaseId(
                                rs.getInt("purchase_id"));

                        purchase.setSupplierId(
                                rs.getInt("supplier_id"));

                        purchase.setPurchaseDate(
                                rs.getDate("purchase_date"));

                        purchase.setTotalAmount(
                                rs.getDouble("total_amount"));

                        return purchase;
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
        
            // GET PURCHASE ITEM BY PURCHASE ID
        public PurchaseItem getPurchaseItemByPurchaseId(int purchaseId) {

            String sql = "SELECT * FROM purchase_items "
                       + "WHERE purchase_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, purchaseId);

                try (ResultSet rs = pst.executeQuery()) {

                    if (rs.next()) {

                        PurchaseItem item = new PurchaseItem();

                        item.setPurchaseItemId(
                                rs.getInt("purchase_item_id"));

                        item.setPurchaseId(
                                rs.getInt("purchase_id"));

                        item.setProductId(
                                rs.getInt("product_id"));

                        item.setQuantity(
                                rs.getInt("quantity"));

                        item.setUnitPrice(
                                rs.getDouble("unit_price"));

                        item.setSubtotal(
                                rs.getDouble("subtotal"));

                        return item;
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
        
}
