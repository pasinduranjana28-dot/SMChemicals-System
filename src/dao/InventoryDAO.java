/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import entity.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pasindu
 */
public class InventoryDAO {
        // STOCK IN
    public boolean stockIn(Inventory inventory) {

        String checkSql = "SELECT inventory_id FROM inventory WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement checkPst = con.prepareStatement(checkSql)) {

            checkPst.setInt(1, inventory.getProductId());

            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {

                // Product already exists in inventory
                String updateSql = "UPDATE inventory SET "
                        + "quantity = quantity + ?, "
                        + "reorder_level = ?, "
                        + "last_updated = CURRENT_TIMESTAMP "
                        + "WHERE product_id = ?";

                try (PreparedStatement updatePst = con.prepareStatement(updateSql)) {

                    updatePst.setInt(1, inventory.getQuantity());
                    updatePst.setInt(2, inventory.getReorderLevel());
                    updatePst.setInt(3, inventory.getProductId());

                    return updatePst.executeUpdate() > 0;
                }

            } else {

                // Product does not exist in inventory
                String insertSql = "INSERT INTO inventory "
                        + "(product_id, quantity, reorder_level, last_updated) "
                        + "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

                try (PreparedStatement insertPst = con.prepareStatement(insertSql)) {

                    insertPst.setInt(1, inventory.getProductId());
                    insertPst.setInt(2, inventory.getQuantity());
                    insertPst.setInt(3, inventory.getReorderLevel());

                    return insertPst.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET ALL INVENTORY
    public List<Inventory> getAllInventory() {

        List<Inventory> inventoryList = new ArrayList<>();

        String sql = "SELECT * FROM inventory";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Inventory inventory = new Inventory();

                inventory.setInventoryId(rs.getInt("inventory_id"));
                inventory.setProductId(rs.getInt("product_id"));
                inventory.setQuantity(rs.getInt("quantity"));
                inventory.setReorderLevel(rs.getInt("reorder_level"));
                inventory.setLastUpdated(
                        rs.getTimestamp("last_updated")
                );

                inventoryList.add(inventory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventoryList;
    }
    
    // Stock out
    public boolean stockOut(int productId, int quantity) {

    String sql = "UPDATE inventory "
               + "SET quantity = quantity - ?, "
               + "last_updated = CURRENT_TIMESTAMP "
               + "WHERE product_id = ? AND quantity >= ?";

    try (java.sql.Connection con = database.DBConnection.getConnection();
         java.sql.PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, quantity);
        pst.setInt(2, productId);
        pst.setInt(3, quantity);

        int rows = pst.executeUpdate();

        return rows > 0;

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    // GET LOW STOCK ITEMS
    public List<Inventory> getLowStockItems() {

        List<Inventory> lowStockList = new ArrayList<>();

        String sql = "SELECT * FROM inventory "
                   + "WHERE quantity <= reorder_level";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Inventory inventory = new Inventory();

                inventory.setInventoryId(rs.getInt("inventory_id"));
                inventory.setProductId(rs.getInt("product_id"));
                inventory.setQuantity(rs.getInt("quantity"));
                inventory.setReorderLevel(rs.getInt("reorder_level"));
                inventory.setLastUpdated(
                        rs.getTimestamp("last_updated")
                );

                lowStockList.add(inventory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lowStockList;
    }
}
