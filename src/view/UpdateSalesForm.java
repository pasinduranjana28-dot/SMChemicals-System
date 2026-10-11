/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dao.SalesDAO;
import entity.Sale;
import entity.SaleItem;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Pasindu
 */
public class UpdateSalesForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UpdateSalesForm.class.getName());
    
    private SalesDAO salesDAO = new SalesDAO();

    private Map<Integer, String> customerMap = new LinkedHashMap<>();
    private Map<Integer, String> productMap = new LinkedHashMap<>();

    private boolean loadingSale = false;
    
    private SalesForm salesForm;

    /**
     * Creates new form UpdateSalesForm
     */
        public UpdateSalesForm(SalesForm salesForm) {
            initComponents();

            this.salesForm = salesForm;

            setTitle("Update Sales");
            setDefaultCloseOperation(
                    javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            unitPriceTF.setEditable(false);
            totalAmountTF.setEditable(false);

            loadCustomers();
            loadProducts();
            loadSaleIDs();

            saleIDCMB.addActionListener(e -> loadSelectedSale());
            productCMB.addActionListener(e -> updatePriceFromProduct());

            quantityTF.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        calculateTotal();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        calculateTotal();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        calculateTotal();
                    }
                }
            );

            updateBTN.addActionListener(e -> updateSale());
            clearBTN.addActionListener(e -> clearFields());
            backBTN.addActionListener(e -> dispose());
        }
        
        
private void loadCustomers() {
    customerMap = salesDAO.getCustomerList();

    customerCMB.removeAllItems();
    customerCMB.addItem("Select Customer");

    for (String name : customerMap.values()) {
        customerCMB.addItem(name);
    }
}

private void loadProducts() {
    productMap = salesDAO.getProductList();

    productCMB.removeAllItems();
    productCMB.addItem("Select Product");

    for (String name : productMap.values()) {
        productCMB.addItem(name);
    }
}

private void loadSaleIDs() {
    saleIDCMB.removeAllItems();
    saleIDCMB.addItem("Select Sales ID");

    String sql = "SELECT sale_id FROM sales ORDER BY sale_id";

    try (java.sql.Connection con =
                 database.DBConnection.getConnection();
         java.sql.PreparedStatement pst =
                 con.prepareStatement(sql);
         java.sql.ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            saleIDCMB.addItem(
                    String.valueOf(rs.getInt("sale_id")));
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
                this, "Failed to load Sales IDs.");
    }
}

private void loadSelectedSale() {
    if (saleIDCMB.getSelectedIndex() <= 0) {
        return;
    }

    try {
        int saleId = Integer.parseInt(
                saleIDCMB.getSelectedItem().toString());

        Sale sale = salesDAO.getSaleById(saleId);
        SaleItem item = salesDAO.getSaleItemBySaleId(saleId);

        if (sale == null || item == null) {
            JOptionPane.showMessageDialog(
                    this, "Sale details could not be loaded.");
            return;
        }

        loadingSale = true;

        customerCMB.setSelectedItem(
                customerMap.get(sale.getCustomerId()));

        productCMB.setSelectedItem(
                productMap.get(item.getProductId()));

        quantityTF.setText(
                String.valueOf(item.getQuantity()));

        // Preserve the price stored with this sale.
        unitPriceTF.setText(
                String.valueOf(item.getUnitPrice()));

        saleDateTF.setText(
                sale.getSaleDate().toString());

        totalAmountTF.setText(
                String.format("%.2f", sale.getTotalAmount()));

        loadingSale = false;

    } catch (Exception e) {
        loadingSale = false;
        e.printStackTrace();
        JOptionPane.showMessageDialog(
                this, "Error loading sale details.");
    }
}

        private void updatePriceFromProduct() {
            if (loadingSale || productCMB.getSelectedIndex() <= 0) {
                return;
            }

            int index = productCMB.getSelectedIndex() - 1;

            if (index >= productMap.size()) {
                return;
            }

            int productId =
                    (int) productMap.keySet().toArray()[index];

            double price = salesDAO.getProductPrice(productId);

            unitPriceTF.setText(String.valueOf(price));
            calculateTotal();
        }

        private void calculateTotal() {
            try {
                int quantity = Integer.parseInt(
                        quantityTF.getText().trim());

                double price = Double.parseDouble(
                        unitPriceTF.getText().trim());

                if (quantity <= 0 || price < 0) {
                    totalAmountTF.setText("");
                    return;
                }

                totalAmountTF.setText(
                        String.format("%.2f", quantity * price));

            } catch (NumberFormatException e) {
                totalAmountTF.setText("");
            }
        }
        
        
        private void updateSale() {

            if (saleIDCMB.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(
                        this, "Please select a Sales ID.");
                return;
            }

            if (customerCMB.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(
                        this, "Please select a customer.");
                return;
            }

            if (productCMB.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(
                        this, "Please select a product.");
                return;
            }

            int quantity;
            double unitPrice;
            double totalAmount;
            int saleId;

            try {
                saleId = Integer.parseInt(
                        saleIDCMB.getSelectedItem().toString());

                quantity = Integer.parseInt(
                        quantityTF.getText().trim());

                unitPrice = Double.parseDouble(
                        unitPriceTF.getText().trim());

                totalAmount = Double.parseDouble(
                        totalAmountTF.getText().trim());

                if (quantity <= 0 || unitPrice < 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Quantity must be greater than 0 and price cannot be negative.");
                    return;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this, "Enter a valid quantity and price.");
                return;
            }

            java.sql.Date saleDate;

            try {
                saleDate = java.sql.Date.valueOf(
                        saleDateTF.getText().trim());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                        this, "Use the date format YYYY-MM-DD.");
                return;
            }

            int customerIndex = customerCMB.getSelectedIndex() - 1;
            int productIndex = productCMB.getSelectedIndex() - 1;

            int customerId = (int) customerMap.keySet()
                    .toArray()[customerIndex];

            int productId = (int) productMap.keySet()
                    .toArray()[productIndex];

            boolean success = salesDAO.updateSale(
                    saleId,
                    customerId,
                    saleDate,
                    totalAmount,
                    productId,
                    quantity,
                    unitPrice,
                    totalAmount
            );

                    if (success) {
                JOptionPane.showMessageDialog(
                        this, "Sale updated successfully.");

                if (salesForm != null) {
                    salesForm.refreshSalesTable();
                }

                clearFields();
                loadSaleIDs();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Sale update failed. Please check available stock and try again.");
            }
        }
        
        
        private void clearFields() {

            saleIDCMB.setSelectedIndex(0);
            customerCMB.setSelectedIndex(0);
            productCMB.setSelectedIndex(0);

            quantityTF.setText("");
            unitPriceTF.setText("");
            saleDateTF.setText("");
            totalAmountTF.setText("");
        }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLBL = new javax.swing.JLabel();
        productIDLBL = new javax.swing.JLabel();
        saleIDCMB = new javax.swing.JComboBox<>();
        productNameLBL = new javax.swing.JLabel();
        descriptionLBL = new javax.swing.JLabel();
        unitPriceLBL = new javax.swing.JLabel();
        quantityTF = new javax.swing.JTextField();
        updateBTN = new javax.swing.JButton();
        clearBTN = new javax.swing.JButton();
        backBTN = new javax.swing.JButton();
        customerCMB = new javax.swing.JComboBox<>();
        productCMB = new javax.swing.JComboBox<>();
        descriptionLBL1 = new javax.swing.JLabel();
        unitPriceTF = new javax.swing.JTextField();
        unitPriceLBL1 = new javax.swing.JLabel();
        saleDateTF = new javax.swing.JTextField();
        unitPriceLBL2 = new javax.swing.JLabel();
        totalAmountTF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 249, 255));

        titleLBL.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        titleLBL.setForeground(new java.awt.Color(16, 42, 80));
        titleLBL.setText("Update Sales");

        productIDLBL.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        productIDLBL.setText("Sales ID: ");

        saleIDCMB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        saleIDCMB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        productNameLBL.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        productNameLBL.setText("Customer: ");

        descriptionLBL.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        descriptionLBL.setText("Product: ");

        unitPriceLBL.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        unitPriceLBL.setText("Unit Price: ");

        quantityTF.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        quantityTF.addActionListener(this::quantityTFActionPerformed);

        updateBTN.setBackground(new java.awt.Color(60, 165, 255));
        updateBTN.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        updateBTN.setForeground(new java.awt.Color(255, 255, 255));
        updateBTN.setText("Update Sale");

        clearBTN.setBackground(new java.awt.Color(229, 237, 247));
        clearBTN.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        clearBTN.setForeground(new java.awt.Color(16, 42, 80));
        clearBTN.setText("Clear");

        backBTN.setBackground(new java.awt.Color(229, 237, 247));
        backBTN.setForeground(new java.awt.Color(16, 42, 80));
        backBTN.setText("Back");

        customerCMB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        customerCMB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        productCMB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        productCMB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        descriptionLBL1.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        descriptionLBL1.setText("Quantity: ");

        unitPriceTF.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        unitPriceTF.addActionListener(this::unitPriceTFActionPerformed);

        unitPriceLBL1.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        unitPriceLBL1.setText("Sales Date: ");

        saleDateTF.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        saleDateTF.addActionListener(this::saleDateTFActionPerformed);

        unitPriceLBL2.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        unitPriceLBL2.setText("Total Amount: ");

        totalAmountTF.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        totalAmountTF.addActionListener(this::totalAmountTFActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(productIDLBL)
                                .addGap(37, 37, 37)
                                .addComponent(saleIDCMB, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(descriptionLBL1)
                            .addComponent(unitPriceLBL)
                            .addComponent(unitPriceLBL1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(unitPriceLBL2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(productNameLBL)
                                    .addComponent(descriptionLBL))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(productCMB, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(customerCMB, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(backBTN)
                                .addGap(105, 105, 105)
                                .addComponent(titleLBL))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(165, 165, 165)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(unitPriceTF, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(quantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(saleDateTF, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(148, Short.MAX_VALUE)
                .addComponent(updateBTN)
                .addGap(80, 80, 80)
                .addComponent(clearBTN)
                .addGap(149, 149, 149))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(titleLBL))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(backBTN)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productIDLBL)
                    .addComponent(saleIDCMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productNameLBL)
                    .addComponent(customerCMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionLBL)
                    .addComponent(productCMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(quantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descriptionLBL1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitPriceLBL)
                    .addComponent(unitPriceTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitPriceLBL1)
                    .addComponent(saleDateTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitPriceLBL2)
                    .addComponent(totalAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearBTN)
                    .addComponent(updateBTN))
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quantityTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityTFActionPerformed

    private void unitPriceTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitPriceTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unitPriceTFActionPerformed

    private void saleDateTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saleDateTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saleDateTFActionPerformed

    private void totalAmountTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalAmountTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalAmountTFActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater( () -> new UpdateSalesForm(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBTN;
    private javax.swing.JButton clearBTN;
    private javax.swing.JComboBox<String> customerCMB;
    private javax.swing.JLabel descriptionLBL;
    private javax.swing.JLabel descriptionLBL1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> productCMB;
    private javax.swing.JLabel productIDLBL;
    private javax.swing.JLabel productNameLBL;
    private javax.swing.JTextField quantityTF;
    private javax.swing.JTextField saleDateTF;
    private javax.swing.JComboBox<String> saleIDCMB;
    private javax.swing.JLabel titleLBL;
    private javax.swing.JTextField totalAmountTF;
    private javax.swing.JLabel unitPriceLBL;
    private javax.swing.JLabel unitPriceLBL1;
    private javax.swing.JLabel unitPriceLBL2;
    private javax.swing.JTextField unitPriceTF;
    private javax.swing.JButton updateBTN;
    // End of variables declaration//GEN-END:variables
}
