/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dao.SalesDAO;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Pasindu
 */
public class SalesForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SalesForm.class.getName());

    private Map<Integer, String> customerMap;
    private Map<Integer, String> productMap;
    
    private javax.swing.JTextField saleIDTF;
    
    /**
     * Creates new form SalesForm
     */
    public SalesForm() {
        initComponents();
        
        // Style the sales table
            salesTable.setRowHeight(26);

            salesTable.setBackground(java.awt.Color.WHITE);
            salesTable.setForeground(new java.awt.Color(16, 42, 80));

            salesTable.setSelectionBackground(
                new java.awt.Color(232, 240, 252)
            );
            salesTable.setSelectionForeground(
                new java.awt.Color(16, 42, 80)
            );

            salesTable.setGridColor(new java.awt.Color(220, 220, 220));
            salesTable.setShowHorizontalLines(true);
            salesTable.setShowVerticalLines(false);
            salesTable.setAutoCreateRowSorter(true);

            // Style the sales table header
            javax.swing.table.JTableHeader header =
                    salesTable.getTableHeader();

            header.setDefaultRenderer(
                new javax.swing.table.DefaultTableCellRenderer() {
                    @Override
                    public java.awt.Component getTableCellRendererComponent(
                            javax.swing.JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        javax.swing.JLabel label =
                            (javax.swing.JLabel) super.getTableCellRendererComponent(
                                table, value, isSelected, hasFocus, row, column
                            );

                        label.setOpaque(true);
                        label.setBackground(new java.awt.Color(16, 42, 80));
                        label.setForeground(java.awt.Color.WHITE);
                        label.setFont(
                            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
                        );
                        label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                        label.setBorder(javax.swing.BorderFactory.createEmptyBorder(
                            0, 8, 0, 4
                        ));

                        return label;
                    }
                }
            );

            header.setPreferredSize(new java.awt.Dimension(0, 30));
            header.repaint();
            salesTable.repaint();
        
        unitPriceTF.setEditable(false);
        totalAmountTF.setEditable(false);

        loadCustomers();
        loadProducts();
        loadSales();
        
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
    });
    }
    
        private void loadCustomers() {

        SalesDAO dao = new SalesDAO();

        customerMap = dao.getCustomerList();

        customerCMB.removeAllItems();

        customerCMB.addItem("Select Customer");

        for (String customerName : customerMap.values()) {
            customerCMB.addItem(customerName);
        }
    }
        
        private void loadProducts() {

        SalesDAO dao = new SalesDAO();

        productMap = dao.getProductList();

        productCMB.removeAllItems();

        productCMB.addItem("Select Product");

        for (String productName : productMap.values()) {
            productCMB.addItem(productName);
        }
    }
        
        private void loadSales() {

        SalesDAO dao = new SalesDAO();

        DefaultTableModel model =
                (DefaultTableModel) salesTable.getModel();

        model.setRowCount(0);

        String sql =
                "SELECT s.sale_id, "
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

        try (java.sql.Connection con =
                     database.DBConnection.getConnection();
             java.sql.PreparedStatement pst =
                     con.prepareStatement(sql);
             java.sql.ResultSet rs =
                     pst.executeQuery()) {

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt("sale_id"),
                    rs.getString("customer_name"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price"),
                    rs.getDate("sale_date"),
                    rs.getDouble("total_amount")
                });
            }

        } catch (java.sql.SQLException e) {

            e.printStackTrace();

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Failed to load sales."
            );
        }
    }
        
        public void refreshSalesTable() {
            loadSales();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        customerCMB = new javax.swing.JComboBox<>();
        productCMB = new javax.swing.JComboBox<>();
        quantityTF = new javax.swing.JTextField();
        unitPriceTF = new javax.swing.JTextField();
        saleDateTF = new javax.swing.JTextField();
        totalAmountTF = new javax.swing.JTextField();
        addBTN = new javax.swing.JButton();
        updateBTN = new javax.swing.JButton();
        deleteBTN = new javax.swing.JButton();
        clearBTN = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        salesTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        refreshBTN = new javax.swing.JButton();
        backBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 249, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(16, 42, 80));
        jLabel1.setText("Sales Management");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("Customer: ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setText("Product: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText("Quantity: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Unit Price: ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel7.setText("Sales Date: ");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setText("Total Amount: ");

        customerCMB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        customerCMB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        productCMB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        productCMB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        productCMB.addActionListener(this::productCMBActionPerformed);

        quantityTF.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        unitPriceTF.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        saleDateTF.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        saleDateTF.setText("YYYY-MM-DD");

        totalAmountTF.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        addBTN.setBackground(new java.awt.Color(60, 165, 255));
        addBTN.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        addBTN.setForeground(new java.awt.Color(255, 255, 255));
        addBTN.setText("Add Sale");
        addBTN.addActionListener(this::addBTNActionPerformed);

        updateBTN.setBackground(new java.awt.Color(220, 235, 255));
        updateBTN.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        updateBTN.setForeground(new java.awt.Color(16, 42, 80));
        updateBTN.setText("Update Sale");
        updateBTN.addActionListener(this::updateBTNActionPerformed);

        deleteBTN.setBackground(new java.awt.Color(255, 107, 120));
        deleteBTN.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        deleteBTN.setForeground(new java.awt.Color(255, 255, 255));
        deleteBTN.setText("Delete Sale");
        deleteBTN.addActionListener(this::deleteBTNActionPerformed);

        clearBTN.setBackground(new java.awt.Color(229, 237, 247));
        clearBTN.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        clearBTN.setForeground(new java.awt.Color(16, 42, 80));
        clearBTN.setText("Clear");
        clearBTN.addActionListener(this::clearBTNActionPerformed);

        salesTable.setForeground(new java.awt.Color(16, 42, 80));
        salesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Customer", "Product", "Quantity", "Unit Price", "Date", "Amount"
            }
        ));
        salesTable.setRowHeight(26);
        salesTable.setSelectionBackground(new java.awt.Color(232, 240, 252));
        salesTable.setSelectionForeground(new java.awt.Color(16, 42, 80));
        salesTable.setShowHorizontalLines(true);
        salesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(salesTable);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Sales List");

        refreshBTN.setBackground(new java.awt.Color(229, 237, 247));
        refreshBTN.setForeground(new java.awt.Color(16, 42, 80));
        refreshBTN.setText("Refresh");
        refreshBTN.addActionListener(this::refreshBTNActionPerformed);

        backBTN.setBackground(new java.awt.Color(229, 237, 247));
        backBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        backBTN.setForeground(new java.awt.Color(16, 42, 80));
        backBTN.setText("Back");
        backBTN.addActionListener(this::backBTNActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshBTN)
                        .addGap(48, 48, 48))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(saleDateTF, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(quantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(unitPriceTF, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(productCMB, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(customerCMB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(backBTN)
                        .addGap(205, 205, 205)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(addBTN)
                        .addGap(47, 47, 47)
                        .addComponent(updateBTN)
                        .addGap(55, 55, 55)
                        .addComponent(deleteBTN)
                        .addGap(55, 55, 55)
                        .addComponent(clearBTN))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(backBTN)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(productCMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(customerCMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(quantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(unitPriceTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(saleDateTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(totalAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBTN)
                    .addComponent(updateBTN)
                    .addComponent(deleteBTN)
                    .addComponent(clearBTN))
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(refreshBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addContainerGap())
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

    private void productCMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productCMBActionPerformed
        // TODO add your handling code here:
        int selectedIndex = productCMB.getSelectedIndex();

        if (selectedIndex <= 0) {
            unitPriceTF.setText("");
            totalAmountTF.setText("");
            return;
        }

        int productId =
                (int) productMap.keySet().toArray()[selectedIndex - 1];

        SalesDAO dao = new SalesDAO();

        double price = dao.getProductPrice(productId);

        unitPriceTF.setText(String.valueOf(price));

        calculateTotal();
    }//GEN-LAST:event_productCMBActionPerformed

    private void addBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBTNActionPerformed
        // TODO add your handling code here:
        int customerIndex = customerCMB.getSelectedIndex();
        int productIndex = productCMB.getSelectedIndex();

        if (customerIndex <= 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select a customer."
            );
            return;
        }

        if (productIndex <= 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select a product."
            );
            return;
        }

        String quantityText = quantityTF.getText().trim();
        String dateText = saleDateTF.getText().trim();
        String totalText = totalAmountTF.getText().trim();

        if (quantityText.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter quantity."
            );
            return;
        }

        if (dateText.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter sale date."
            );
            return;
        }

        if (totalText.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity."
            );
            return;
        }

        int quantity;

        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be a number."
            );
            return;
        }

        if (quantity <= 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be greater than 0."
            );
            return;
        }

        double unitPrice;
        double totalAmount;

        try {
            unitPrice = Double.parseDouble(unitPriceTF.getText().trim());
            totalAmount = Double.parseDouble(totalText);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Invalid price or total amount."
            );
            return;
        }

        java.sql.Date saleDate;

        try {
            saleDate = java.sql.Date.valueOf(dateText);
        } catch (IllegalArgumentException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Invalid date. Use format: YYYY-MM-DD"
            );
            return;
        }

        int customerId =
                (int) customerMap.keySet().toArray()[customerIndex - 1];

        int productId =
                (int) productMap.keySet().toArray()[productIndex - 1];

        SalesDAO dao = new SalesDAO();

        boolean success = dao.addSaleWithItem(
                customerId,
                saleDate,
                totalAmount,
                productId,
                quantity,
                unitPrice,
                totalAmount
        );

        if (success) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Sale added successfully."
            );

            customerCMB.setSelectedIndex(0);
            productCMB.setSelectedIndex(0);
            quantityTF.setText("");
            unitPriceTF.setText("");
            saleDateTF.setText("");
            totalAmountTF.setText("");

            loadSales();

        } else {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Failed to add sale."
            );
        }
    }//GEN-LAST:event_addBTNActionPerformed

    private void salesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTableMouseClicked
        // TODO add your handling code here:
        int row = salesTable.getSelectedRow();

        if (row >= 0) {

            saleIDTF.setText(
                    salesTable.getValueAt(row, 0).toString()
            );

            String customerName =
                    salesTable.getValueAt(row, 1).toString();

            String productName =
                    salesTable.getValueAt(row, 2).toString();

            customerCMB.setSelectedItem(customerName);
            productCMB.setSelectedItem(productName);

            quantityTF.setText(
                    salesTable.getValueAt(row, 3).toString()
            );

            unitPriceTF.setText(
                    salesTable.getValueAt(row, 4).toString()
            );

            saleDateTF.setText(
                    salesTable.getValueAt(row, 5).toString()
            );

            totalAmountTF.setText(
                    salesTable.getValueAt(row, 6).toString()
            );
        }
    }//GEN-LAST:event_salesTableMouseClicked

    private void updateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBTNActionPerformed
        // TODO add your handling code here:
           UpdateSalesForm form = new UpdateSalesForm(this);
            form.setLocationRelativeTo(this);
            form.setVisible(true);
    }//GEN-LAST:event_updateBTNActionPerformed

    private void deleteBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBTNActionPerformed
        // TODO add your handling code here:
       DeleteSalesForm form = new DeleteSalesForm(this);
        form.setLocationRelativeTo(this);
        form.setVisible(true);
    }//GEN-LAST:event_deleteBTNActionPerformed

    private void clearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBTNActionPerformed
        // TODO add your handling code here:

        customerCMB.setSelectedIndex(0);

        productCMB.setSelectedIndex(0);

        quantityTF.setText("");

        unitPriceTF.setText("");

        saleDateTF.setText("");

        totalAmountTF.setText("");
    }//GEN-LAST:event_clearBTNActionPerformed

    private void refreshBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBTNActionPerformed
        // TODO add your handling code here:
        loadSales();
    }//GEN-LAST:event_refreshBTNActionPerformed

    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_backBTNActionPerformed

        private void calculateTotal() {

        String quantityText = quantityTF.getText().trim();
        String priceText = unitPriceTF.getText().trim();

        if (quantityText.isEmpty() || priceText.isEmpty()) {
            totalAmountTF.setText("");
            return;
        }

        try {

            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);

            double total = quantity * price;

            totalAmountTF.setText(
                    String.format("%.2f", total)
            );

        } catch (NumberFormatException e) {

            totalAmountTF.setText("");
        }
    }
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
        java.awt.EventQueue.invokeLater(() -> new SalesForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBTN;
    private javax.swing.JButton backBTN;
    private javax.swing.JButton clearBTN;
    private javax.swing.JComboBox<String> customerCMB;
    private javax.swing.JButton deleteBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> productCMB;
    private javax.swing.JTextField quantityTF;
    private javax.swing.JButton refreshBTN;
    private javax.swing.JTextField saleDateTF;
    private javax.swing.JTable salesTable;
    private javax.swing.JTextField totalAmountTF;
    private javax.swing.JTextField unitPriceTF;
    private javax.swing.JButton updateBTN;
    // End of variables declaration//GEN-END:variables
}
