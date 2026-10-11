/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pasindu
 */
public class DashboardDAO {
    
    public int getCustomerCount() {
        return getCount("SELECT COUNT(*) FROM customers");
    }

    public int getProductCount() {
        return getCount("SELECT COUNT(*) FROM products");
    }

    public int getSupplierCount() {
        return getCount("SELECT COUNT(*) FROM suppliers");
    }

    private int getCount(String sql) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getTotalSales() {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM sales";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
    
    
public java.util.Map<String, Double> getMonthlySales() {

    java.util.Map<String, Double> sales =
            new java.util.LinkedHashMap<>();

    String sql =
            "SELECT DATE_FORMAT(sale_date, '%Y-%m') AS month, " +
            "SUM(total_amount) AS total " +
            "FROM sales " +
            "GROUP BY DATE_FORMAT(sale_date, '%Y-%m') " +
            "ORDER BY month";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            sales.put(
                    rs.getString("month"),
                    rs.getDouble("total")
            );
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return sales;
}

            public java.util.Map<String, Integer> getInventoryStatus() {

                java.util.Map<String, Integer> status =
                        new java.util.LinkedHashMap<>();

                String sql =
                        "SELECT " +
                        "SUM(CASE WHEN quantity > reorder_level THEN 1 ELSE 0 END) AS available, " +
                        "SUM(CASE WHEN quantity <= reorder_level THEN 1 ELSE 0 END) AS low_stock " +
                        "FROM inventory";

                try (Connection con = DBConnection.getConnection();
                     PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {

                    if (rs.next()) {
                        status.put("Available", rs.getInt("available"));
                        status.put("Low Stock", rs.getInt("low_stock"));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return status;
            }

    
        public java.util.List<String[]> getRecentActivity() {

            java.util.List<String[]> activities =
                    new java.util.ArrayList<>();

            String sql =
                    "SELECT sale_id, sale_date, total_amount " +
                    "FROM sales " +
                    "ORDER BY sale_date DESC, sale_id DESC " +
                    "LIMIT 5";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    String[] activity = {
                        "Sale #" + rs.getInt("sale_id"),
                        rs.getString("sale_date"),
                        String.format(
                            java.util.Locale.US,
                            "Rs. %,.2f",
                            rs.getDouble("total_amount")
                        )
                    };

                    activities.add(activity);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return activities;
        }


    
}
