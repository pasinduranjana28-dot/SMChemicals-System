/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Pasindu
 */
public class ProductDAO {
    
    // ADD PRODUCT
    public boolean addProduct(Product product) {

        String sql = "INSERT INTO products "
                + "(product_name, description, unit_price, category) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, product.getProductName());
            pst.setString(2, product.getDescription());
            pst.setDouble(3, product.getUnitPrice());
            pst.setString(4, product.getCategory());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET ALL PRODUCTS
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Product product = new Product();

                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setUnitPrice(rs.getDouble("unit_price"));
                product.setCategory(rs.getString("category"));

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    
    // SEARCH PRODUCTS
    public List<Product> searchProducts(String keyword) {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products "
                + "WHERE product_name LIKE ? "
                + "OR category LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";

            pst.setString(1, searchKeyword);
            pst.setString(2, searchKeyword);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Product product = new Product();

                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setUnitPrice(rs.getDouble("unit_price"));
                product.setCategory(rs.getString("category"));

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    // UPDATE PRODUCT
    public boolean updateProduct(Product product) {

        String sql = "UPDATE products SET "
                + "product_name = ?, "
                + "description = ?, "
                + "unit_price = ?, "
                + "category = ? "
                + "WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, product.getProductName());
            pst.setString(2, product.getDescription());
            pst.setDouble(3, product.getUnitPrice());
            pst.setString(4, product.getCategory());
            pst.setInt(5, product.getProductId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE PRODUCT
    public boolean deleteProduct(int productId) {

        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public java.util.Map<Integer, String> getProductList() {

    java.util.Map<Integer, String> productMap =
            new java.util.LinkedHashMap<>();

    String sql = "SELECT product_id, product_name FROM products";

    try (java.sql.Connection con = database.DBConnection.getConnection();
         java.sql.PreparedStatement pst = con.prepareStatement(sql);
         java.sql.ResultSet rs = pst.executeQuery()) {

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
    
}
