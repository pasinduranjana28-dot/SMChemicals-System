/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import entity.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pasindu
 */
public class SupplierDAO {
    // ADD SUPPLIER
    public boolean addSupplier(Supplier supplier) {

        String sql = "INSERT INTO suppliers "
                   + "(supplier_name, phone, email, address) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getPhone());
            pst.setString(3, supplier.getEmail());
            pst.setString(4, supplier.getAddress());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET ALL SUPPLIERS
    public List<Supplier> getAllSuppliers() {

        List<Supplier> supplierList = new ArrayList<>();

        String sql = "SELECT * FROM suppliers";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Supplier supplier = new Supplier();

                supplier.setSupplierId(
                        rs.getInt("supplier_id"));

                supplier.setSupplierName(
                        rs.getString("supplier_name"));

                supplier.setPhone(
                        rs.getString("phone"));

                supplier.setEmail(
                        rs.getString("email"));

                supplier.setAddress(
                        rs.getString("address"));

                supplierList.add(supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierList;
    }

    // UPDATE SUPPLIER
    public boolean updateSupplier(Supplier supplier) {

        String sql = "UPDATE suppliers SET "
                   + "supplier_name = ?, "
                   + "phone = ?, "
                   + "email = ?, "
                   + "address = ? "
                   + "WHERE supplier_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getPhone());
            pst.setString(3, supplier.getEmail());
            pst.setString(4, supplier.getAddress());
            pst.setInt(5, supplier.getSupplierId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE SUPPLIER
    public boolean deleteSupplier(int supplierId) {

        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, supplierId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // SEARCH SUPPLIER
    public List<Supplier> searchSuppliers(String keyword) {

        List<Supplier> supplierList = new ArrayList<>();

        String sql = "SELECT * FROM suppliers "
                   + "WHERE supplier_name LIKE ? "
                   + "OR phone LIKE ? "
                   + "OR email LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            String searchValue = "%" + keyword + "%";

            pst.setString(1, searchValue);
            pst.setString(2, searchValue);
            pst.setString(3, searchValue);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Supplier supplier = new Supplier();

                supplier.setSupplierId(
                        rs.getInt("supplier_id"));

                supplier.setSupplierName(
                        rs.getString("supplier_name"));

                supplier.setPhone(
                        rs.getString("phone"));

                supplier.setEmail(
                        rs.getString("email"));

                supplier.setAddress(
                        rs.getString("address"));

                supplierList.add(supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierList;
    }
}
