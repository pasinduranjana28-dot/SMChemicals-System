/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import entity.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import entity.Invoice;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pasindu
 */
public class InvoiceDAO {
    // ADD INVOICE
    public boolean addInvoice(Invoice invoice) {

        String sql =
                "INSERT INTO invoices "
              + "(sale_id, invoice_date, total_amount) "
              + "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, invoice.getSaleId());
            pst.setDate(2, invoice.getInvoiceDate());
            pst.setDouble(3, invoice.getTotalAmount());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
        public Map<Integer, Double> getSaleList() {

        Map<Integer, Double> saleMap = new LinkedHashMap<>();

        String sql = "SELECT sale_id, total_amount FROM sales ORDER BY sale_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int saleId = rs.getInt("sale_id");
                double totalAmount = rs.getDouble("total_amount");

                saleMap.put(saleId, totalAmount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return saleMap;
    }
        
        public List<Invoice> getAllInvoices() {

        List<Invoice> invoiceList = new ArrayList<>();

        String sql = "SELECT invoice_id, sale_id, invoice_date, total_amount "
                   + "FROM invoices ORDER BY invoice_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Invoice invoice = new Invoice();

                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setSaleId(rs.getInt("sale_id"));
                invoice.setInvoiceDate(rs.getDate("invoice_date"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));

                invoiceList.add(invoice);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return invoiceList;
    }
        
        public boolean updateInvoice(Invoice invoice) {

        String sql = "UPDATE invoices "
                   + "SET sale_id = ?, invoice_date = ?, total_amount = ? "
                   + "WHERE invoice_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, invoice.getSaleId());
            pst.setDate(2, invoice.getInvoiceDate());
            pst.setDouble(3, invoice.getTotalAmount());
            pst.setInt(4, invoice.getInvoiceId());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
        
        public boolean deleteInvoice(int invoiceId) {

        String sql = "DELETE FROM invoices WHERE invoice_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, invoiceId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
