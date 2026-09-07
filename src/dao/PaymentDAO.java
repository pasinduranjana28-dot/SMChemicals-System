/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DBConnection;
import entity.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import dao.PaymentDAO;

/**
 *
 * @author Pasindu
 */
public class PaymentDAO {
     // ADD PAYMENT
    public boolean addPayment(Payment payment) {

        String sql =
                "INSERT INTO payments "
              + "(invoice_id, payment_date, amount, payment_method, payment_status) "
              + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, payment.getInvoiceId());
            pst.setDate(2, payment.getPaymentDate());
            pst.setDouble(3, payment.getAmount());
            pst.setString(4, payment.getPaymentMethod());
            pst.setString(5, payment.getPaymentStatus());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

        // GET INVOICE LIST
        public Map<Integer, Double> getInvoiceList() {

        Map<Integer, Double> invoiceMap =
                new LinkedHashMap<>();

        String sql =
                "SELECT invoice_id, total_amount "
              + "FROM invoices "
              + "ORDER BY invoice_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                int invoiceId = rs.getInt("invoice_id");
                double totalAmount = rs.getDouble("total_amount");

                invoiceMap.put(invoiceId, totalAmount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return invoiceMap;
    }
       
        public java.util.List<Payment> getAllPayments() {

        java.util.List<Payment> paymentList =
                new java.util.ArrayList<>();

        String sql =
                "SELECT payment_id, invoice_id, payment_date, amount, "
              + "payment_method, payment_status "
              + "FROM payments ORDER BY payment_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(
                        rs.getInt("payment_id")
                );

                payment.setInvoiceId(
                        rs.getInt("invoice_id")
                );

                payment.setPaymentDate(
                        rs.getDate("payment_date")
                );

                payment.setAmount(
                        rs.getDouble("amount")
                );

                payment.setPaymentMethod(
                        rs.getString("payment_method")
                );

                payment.setPaymentStatus(
                        rs.getString("payment_status")
                );

                paymentList.add(payment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentList;
    }
       
        // Update paymnet
        public boolean updatePayment(Payment payment) {

            String sql =
                    "UPDATE payments "
                  + "SET invoice_id = ?, payment_date = ?, amount = ?, "
                  + "payment_method = ?, payment_status = ? "
                  + "WHERE payment_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, payment.getInvoiceId());
                pst.setDate(2, payment.getPaymentDate());
                pst.setDouble(3, payment.getAmount());
                pst.setString(4, payment.getPaymentMethod());
                pst.setString(5, payment.getPaymentStatus());
                pst.setInt(6, payment.getPaymentId());

                return pst.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        public boolean deletePayment(int paymentId) {

            String sql = "DELETE FROM payments WHERE payment_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, paymentId);

                return pst.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        
}
