/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import entity.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Pasindu
 */
public class SalesDAO {
    // ADD SALE
    public boolean addSale(Sale sale) {

        String sql = "INSERT INTO sales "
                   + "(customer_id, sale_date, total_amount) "
                   + "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, sale.getCustomerId());
            pst.setDate(2, sale.getSaleDate());
            pst.setDouble(3, sale.getTotalAmount());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

        // GET ALL SALES
        public List<Sale> getAllSales() {

            List<Sale> sales = new ArrayList<>();

            String sql =
                    "SELECT s.sale_id, "
                  + "s.customer_id, "
                  + "c.customer_name, "
                  + "p.product_name, "
                  + "si.quantity, "
                  + "si.unit_price, "
                  + "s.sale_date, "
                  + "s.total_amount "
                  + "FROM sales s "
                  + "INNER JOIN customers c "
                  + "ON s.customer_id = c.customer_id "
                  + "INNER JOIN sale_items si "
                  + "ON s.sale_id = si.sale_id "
                  + "INNER JOIN products p "
                  + "ON si.product_id = p.product_id "
                  + "ORDER BY s.sale_id";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    Sale sale = new Sale();

                    sale.setSaleId(rs.getInt("sale_id"));
                    sale.setCustomerId(rs.getInt("customer_id"));
                    sale.setCustomerName(rs.getString("customer_name"));
                    sale.setProductName(rs.getString("product_name"));
                    sale.setQuantity(rs.getInt("quantity"));
                    sale.setUnitPrice(rs.getDouble("unit_price"));
                    sale.setSaleDate(rs.getDate("sale_date"));
                    sale.setTotalAmount(rs.getDouble("total_amount"));

                    sales.add(sale);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return sales;
        }

        // UPDATE SALE WITH SALE ITEM AND INVENTORY
        public boolean updateSale(
                int saleId,
                int customerId,
                java.sql.Date saleDate,
                double totalAmount,
                int productId,
                int quantity,
                double unitPrice,
                double subtotal) {

            String getOldSQL =
                    "SELECT product_id, quantity "
                  + "FROM sale_items "
                  + "WHERE sale_id = ?";

            String returnStockSQL =
                    "UPDATE inventory "
                  + "SET quantity = quantity + ?, "
                  + "last_updated = CURRENT_TIMESTAMP "
                  + "WHERE product_id = ?";

            String checkStockSQL =
                    "UPDATE inventory "
                  + "SET quantity = quantity - ?, "
                  + "last_updated = CURRENT_TIMESTAMP "
                  + "WHERE product_id = ? "
                  + "AND quantity >= ?";

            String updateSaleSQL =
                    "UPDATE sales SET "
                  + "customer_id = ?, "
                  + "sale_date = ?, "
                  + "total_amount = ? "
                  + "WHERE sale_id = ?";

            String updateItemSQL =
                    "UPDATE sale_items SET "
                  + "product_id = ?, "
                  + "quantity = ?, "
                  + "unit_price = ?, "
                  + "subtotal = ? "
                  + "WHERE sale_id = ?";

            Connection con = null;

            try {

                con = DBConnection.getConnection();

                // Start transaction
                con.setAutoCommit(false);

                int oldProductId;
                int oldQuantity;

                // 1. Get old sale item details
                try (PreparedStatement pst =
                        con.prepareStatement(getOldSQL)) {

                    pst.setInt(1, saleId);

                    try (ResultSet rs = pst.executeQuery()) {

                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        oldProductId = rs.getInt("product_id");
                        oldQuantity = rs.getInt("quantity");
                    }
                }

                // 2. Return old quantity to inventory
                try (PreparedStatement pst =
                        con.prepareStatement(returnStockSQL)) {

                    pst.setInt(1, oldQuantity);
                    pst.setInt(2, oldProductId);

                    pst.executeUpdate();
                }

                // 3. Deduct new quantity from inventory
                try (PreparedStatement pst =
                        con.prepareStatement(checkStockSQL)) {

                    pst.setInt(1, quantity);
                    pst.setInt(2, productId);
                    pst.setInt(3, quantity);

                    int updatedRows = pst.executeUpdate();

                    // Not enough stock
                    if (updatedRows == 0) {
                        con.rollback();
                        return false;
                    }
                }

                // 4. Update sales table
                try (PreparedStatement pst =
                        con.prepareStatement(updateSaleSQL)) {

                    pst.setInt(1, customerId);
                    pst.setDate(2, saleDate);
                    pst.setDouble(3, totalAmount);
                    pst.setInt(4, saleId);

                    pst.executeUpdate();
                }

                // 5. Update sale_items table
                try (PreparedStatement pst =
                        con.prepareStatement(updateItemSQL)) {

                    pst.setInt(1, productId);
                    pst.setInt(2, quantity);
                    pst.setDouble(3, unitPrice);
                    pst.setDouble(4, subtotal);
                    pst.setInt(5, saleId);

                    pst.executeUpdate();
                }

                // 6. Save all changes
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

        // DELETE SALE AND RETURN STOCK TO INVENTORY
        public boolean deleteSale(int saleId) {

            String getSaleItemSQL =
                    "SELECT product_id, quantity "
                  + "FROM sale_items "
                  + "WHERE sale_id = ?";

            String returnStockSQL =
                    "UPDATE inventory "
                  + "SET quantity = quantity + ?, "
                  + "last_updated = CURRENT_TIMESTAMP "
                  + "WHERE product_id = ?";

            String deleteItemSQL =
                    "DELETE FROM sale_items "
                  + "WHERE sale_id = ?";

            String deleteSaleSQL =
                    "DELETE FROM sales "
                  + "WHERE sale_id = ?";

            Connection con = null;

            try {

                con = DBConnection.getConnection();

                // Start transaction
                con.setAutoCommit(false);

                int productId;
                int quantity;

                // 1. Get the product and quantity from the sale
                try (PreparedStatement pst =
                        con.prepareStatement(getSaleItemSQL)) {

                    pst.setInt(1, saleId);

                    try (ResultSet rs = pst.executeQuery()) {

                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        productId = rs.getInt("product_id");
                        quantity = rs.getInt("quantity");
                    }
                }

                // 2. Return sold quantity to inventory
                try (PreparedStatement pst =
                        con.prepareStatement(returnStockSQL)) {

                    pst.setInt(1, quantity);
                    pst.setInt(2, productId);

                    pst.executeUpdate();
                }

                // 3. Delete sale item
                try (PreparedStatement pst =
                        con.prepareStatement(deleteItemSQL)) {

                    pst.setInt(1, saleId);
                    pst.executeUpdate();
                }

                // 4. Delete sale
                try (PreparedStatement pst =
                        con.prepareStatement(deleteSaleSQL)) {

                    pst.setInt(1, saleId);

                    int deletedRows = pst.executeUpdate();

                    if (deletedRows == 0) {
                        con.rollback();
                        return false;
                    }
                }

                // 5. Save all changes
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

        // GET CUSTOMER LIST
        public Map<Integer, String> getCustomerList() {

            Map<Integer, String> customerMap =
                    new LinkedHashMap<>();

            String sql = "SELECT customer_id, customer_name "
                       + "FROM customers";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    customerMap.put(
                            rs.getInt("customer_id"),
                            rs.getString("customer_name")
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return customerMap;
        }

        // GET PRODUCT LIST
        public Map<Integer, String> getProductList() {

            Map<Integer, String> productMap =
                    new LinkedHashMap<>();

            String sql = "SELECT product_id, product_name "
                       + "FROM products";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    productMap.put(
                            rs.getInt("product_id"),
                            rs.getString("product_name")
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return productMap;
        }

        // GET PRODUCT PRICE
        public double getProductPrice(int productId) {

            String sql = "SELECT unit_price "
                       + "FROM products "
                       + "WHERE product_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, productId);

                try (ResultSet rs = pst.executeQuery()) {

                    if (rs.next()) {
                        return rs.getDouble("unit_price");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0.0;
        }
    
        // ADD SALE WITH SALE ITEM AND REDUCE INVENTORY
        public boolean addSaleWithItem(
                int customerId,
                java.sql.Date saleDate,
                double totalAmount,
                int productId,
                int quantity,
                double unitPrice,
                double subtotal) {

            String saleSQL =
                    "INSERT INTO sales "
                  + "(customer_id, sale_date, total_amount) "
                  + "VALUES (?, ?, ?)";

            String itemSQL =
                    "INSERT INTO sale_items "
                  + "(sale_id, product_id, quantity, unit_price, subtotal) "
                  + "VALUES (?, ?, ?, ?, ?)";

            String stockSQL =
                    "UPDATE inventory "
                  + "SET quantity = quantity - ?, "
                  + "last_updated = CURRENT_TIMESTAMP "
                  + "WHERE product_id = ? "
                  + "AND quantity >= ?";

            Connection con = null;

            try {

                con = DBConnection.getConnection();

                // Start transaction
                con.setAutoCommit(false);

                // 1. Reduce inventory
                try (PreparedStatement stockPst =
                        con.prepareStatement(stockSQL)) {

                    stockPst.setInt(1, quantity);
                    stockPst.setInt(2, productId);
                    stockPst.setInt(3, quantity);

                    int updatedRows = stockPst.executeUpdate();

                    // Not enough stock
                    if (updatedRows == 0) {
                        con.rollback();
                        return false;
                    }
                }

                int saleId;

                // 2. Insert sale
                try (PreparedStatement pst =
                        con.prepareStatement(
                                saleSQL,
                                java.sql.Statement.RETURN_GENERATED_KEYS)) {

                    pst.setInt(1, customerId);
                    pst.setDate(2, saleDate);
                    pst.setDouble(3, totalAmount);

                    pst.executeUpdate();

                    try (ResultSet rs = pst.getGeneratedKeys()) {

                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        saleId = rs.getInt(1);
                    }
                }

                // 3. Insert sale item
                try (PreparedStatement pst =
                        con.prepareStatement(itemSQL)) {

                    pst.setInt(1, saleId);
                    pst.setInt(2, productId);
                    pst.setInt(3, quantity);
                    pst.setDouble(4, unitPrice);
                    pst.setDouble(5, subtotal);

                    pst.executeUpdate();
                }

                // 4. Save all changes
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
        
        
}

