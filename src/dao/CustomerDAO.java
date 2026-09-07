/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Pasindu
 */
public class CustomerDAO {
    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT customer_id, customer_name, phone, email, address FROM customers";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Customer customer = new Customer();

                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setCustomerName(rs.getString("customer_name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));

                customers.add(customer);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }
    public boolean addCustomer(Customer customer) {

    String sql = "INSERT INTO customers "
               + "(customer_name, phone, email, address) "
               + "VALUES (?, ?, ?, ?)";

    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, customer.getCustomerName());
        pst.setString(2, customer.getPhone());
        pst.setString(3, customer.getEmail());
        pst.setString(4, customer.getAddress());

        int rows = pst.executeUpdate();

        pst.close();
        con.close();

        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean updateCustomer(Customer customer) {

    String sql = "UPDATE customers SET customer_name = ?, phone = ?, email = ?, address = ? WHERE customer_id = ?";

    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, customer.getCustomerName());
        pst.setString(2, customer.getPhone());
        pst.setString(3, customer.getEmail());
        pst.setString(4, customer.getAddress());
        pst.setInt(5, customer.getCustomerId());

        int rows = pst.executeUpdate();

        pst.close();
        con.close();

        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean deleteCustomer(int customerId) {

    String sql = "DELETE FROM customers WHERE customer_id = ?";

    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, customerId);

        int rows = pst.executeUpdate();

        pst.close();
        con.close();

        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    
        // Search customer
    
        public List<Customer> searchCustomers(String keyword) {

        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT customer_id, customer_name, phone, email, address "
                   + "FROM customers "
                   + "WHERE customer_name LIKE ? "
                   + "OR phone LIKE ? "
                   + "OR email LIKE ?";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement pst = con.prepareStatement(sql);

            String search = "%" + keyword + "%";

            pst.setString(1, search);
            pst.setString(2, search);
            pst.setString(3, search);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Customer customer = new Customer();

                customer.setCustomerId(
                        rs.getInt("customer_id"));

                customer.setCustomerName(
                        rs.getString("customer_name"));

                customer.setPhone(
                        rs.getString("phone"));

                customer.setEmail(
                        rs.getString("email"));

                customer.setAddress(
                        rs.getString("address"));

                customers.add(customer);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }

}
