/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

/**
 *
 * @author Pasindu
 */
public class InventoryReportForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(InventoryReportForm.class.getName());

    /**
     * Creates new form InventoryReportForm
     */
    public InventoryReportForm() {
        initComponents();
        
        // Style the report table
        inventoryReportTable.setRowHeight(26);

        inventoryReportTable.setBackground(java.awt.Color.WHITE);
        inventoryReportTable.setForeground(
            new java.awt.Color(16, 42, 80));

        inventoryReportTable.setSelectionBackground(
            new java.awt.Color(232, 240, 252));

        inventoryReportTable.setSelectionForeground(
            new java.awt.Color(16, 42, 80));

        inventoryReportTable.setGridColor(
            new java.awt.Color(220, 220, 220));

        inventoryReportTable.setShowHorizontalLines(true);
        inventoryReportTable.setShowVerticalLines(false);
        inventoryReportTable.setAutoCreateRowSorter(true);

        // Style the table header
        javax.swing.table.JTableHeader header =
                inventoryReportTable.getTableHeader();

        header.setUI(
                new javax.swing.plaf.basic.BasicTableHeaderUI());

        header.setOpaque(true);
        header.setBackground(new java.awt.Color(16, 42, 80));
        header.setForeground(java.awt.Color.WHITE);

        header.setFont(new java.awt.Font(
                "Segoe UI", java.awt.Font.BOLD, 13));

        header.setPreferredSize(new java.awt.Dimension(0, 30));

        // Refresh the table header appearance
        header.repaint();
        inventoryReportTable.repaint();

        
        reportTypeCMB.setSelectedIndex(0);
        loadSelectedReport();

        setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    
        
            private void loadInventoryReport() {

                String sql = "SELECT p.product_name, i.quantity, "
                        + "i.reorder_level, "
                        + "CASE "
                        + "WHEN i.quantity = 0 THEN 'Out of Stock' "
                        + "WHEN i.quantity <= i.reorder_level THEN 'Low Stock' "
                        + "ELSE 'Available' "
                        + "END AS status "
                        + "FROM inventory i "
                        + "INNER JOIN products p "
                        + "ON i.product_id = p.product_id";

                DefaultTableModel model =
                        (DefaultTableModel) inventoryReportTable.getModel();

                model.setRowCount(0);

                // Restore the correct Inventory Report columns
                model.setColumnIdentifiers(new String[]{
                    "Product", "Quantity", "Reorder Level", "Status"
                });

                try (Connection con = database.DBConnection.getConnection();
                     PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("product_name"),
                            rs.getInt("quantity"),
                            rs.getInt("reorder_level"),
                            rs.getString("status")
                        });
                    }

                } catch (java.sql.SQLException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to load inventory report: "
                                    + e.getMessage());
                }
            }


    
        private void loadSelectedReport() {
            String report = String.valueOf(
                    reportTypeCMB.getSelectedItem());

            switch (report) {
                case "Inventory Report":
                    loadInventoryReport();
                    break;

                case "Sales Report":
                    loadSalesReport();
                    break;

                case "Purchase Report":
                    loadPurchaseReport();
                    break;

                case "Payment Report":
                    loadPaymentReport();
                    break;

                case "Monthly Summary":
                    loadMonthlySummary();
                    break;

                default:
                    DefaultTableModel model =
                            (DefaultTableModel) inventoryReportTable.getModel();

                    model.setRowCount(0);
                    model.setColumnIdentifiers(
                            new String[]{"Select a report"});
            }
        }
        
        
            private void loadSalesReport() {

                String sql = "SELECT s.sale_id, c.customer_name, "
                        + "p.product_name, si.quantity, s.sale_date, "
                        + "s.total_amount "
                        + "FROM sales s "
                        + "INNER JOIN customers c "
                        + "ON s.customer_id = c.customer_id "
                        + "INNER JOIN sale_items si "
                        + "ON s.sale_id = si.sale_id "
                        + "INNER JOIN products p "
                        + "ON si.product_id = p.product_id "
                        + "ORDER BY s.sale_date DESC";

                DefaultTableModel model =
                        (DefaultTableModel) inventoryReportTable.getModel();

                model.setRowCount(0);
                model.setColumnIdentifiers(new String[]{
                    "Sale ID", "Customer", "Product",
                    "Quantity", "Date", "Total Amount"
                });

                try (Connection con = database.DBConnection.getConnection();
                     PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getInt("sale_id"),
                            rs.getString("customer_name"),
                            rs.getString("product_name"),
                            rs.getInt("quantity"),
                            rs.getDate("sale_date"),
                            rs.getDouble("total_amount")
                        });
                    }

                } catch (java.sql.SQLException e) {
                    JOptionPane.showMessageDialog(
                            this, "Failed to load sales report: " + e.getMessage());
                }
            }
            
            
            private void loadPurchaseReport() {

                String sql = "SELECT pu.purchase_id, s.supplier_name, "
                        + "p.product_name, pi.quantity, pu.purchase_date, "
                        + "pu.total_amount "
                        + "FROM purchases pu "
                        + "INNER JOIN suppliers s "
                        + "ON pu.supplier_id = s.supplier_id "
                        + "INNER JOIN purchase_items pi "
                        + "ON pu.purchase_id = pi.purchase_id "
                        + "INNER JOIN products p "
                        + "ON pi.product_id = p.product_id "
                        + "ORDER BY pu.purchase_date DESC";

                DefaultTableModel model =
                        (DefaultTableModel) inventoryReportTable.getModel();

                model.setRowCount(0);
                model.setColumnIdentifiers(new String[]{
                    "Purchase ID", "Supplier", "Product",
                    "Quantity", "Date", "Total Amount"
                });

                try (Connection con = database.DBConnection.getConnection();
                     PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getInt("purchase_id"),
                            rs.getString("supplier_name"),
                            rs.getString("product_name"),
                            rs.getInt("quantity"),
                            rs.getDate("purchase_date"),
                            rs.getDouble("total_amount")
                        });
                    }

                } catch (java.sql.SQLException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to load purchase report: " + e.getMessage());
                }
            }
            
            
            private void loadPaymentReport() {

                String sql = "SELECT payment_id, invoice_id, payment_date, "
                        + "amount, payment_method, payment_status "
                        + "FROM payments "
                        + "ORDER BY payment_date DESC";

                DefaultTableModel model =
                        (DefaultTableModel) inventoryReportTable.getModel();

                model.setRowCount(0);
                model.setColumnIdentifiers(new String[]{
                    "Payment ID", "Invoice ID", "Payment Date",
                    "Amount", "Method", "Status"
                });

                try (Connection con = database.DBConnection.getConnection();
                     PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getInt("payment_id"),
                            rs.getInt("invoice_id"),
                            rs.getDate("payment_date"),
                            rs.getDouble("amount"),
                            rs.getString("payment_method"),
                            rs.getString("payment_status")
                        });
                    }

                } catch (java.sql.SQLException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to load payment report: " + e.getMessage());
                }
            }
            
            
            private void loadMonthlySummary() {

                String sql = "SELECT report_month, "
                        + "SUM(sales_total) AS total_sales, "
                        + "SUM(purchase_total) AS total_purchases "
                        + "FROM ( "
                        + "SELECT DATE_FORMAT(sale_date, '%Y-%m') AS report_month, "
                        + "total_amount AS sales_total, 0 AS purchase_total "
                        + "FROM sales "
                        + "UNION ALL "
                        + "SELECT DATE_FORMAT(purchase_date, '%Y-%m') AS report_month, "
                        + "0 AS sales_total, total_amount AS purchase_total "
                        + "FROM purchases "
                        + ") AS monthly_data "
                        + "GROUP BY report_month "
                        + "ORDER BY report_month DESC";

                DefaultTableModel model =
                        (DefaultTableModel) inventoryReportTable.getModel();

                model.setRowCount(0);
                model.setColumnIdentifiers(new String[]{
                    "Month", "Total Sales", "Total Purchases"
                });

                try (Connection con = database.DBConnection.getConnection();
                     PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("report_month"),
                            rs.getDouble("total_sales"),
                            rs.getDouble("total_purchases")
                        });
                    }

                } catch (java.sql.SQLException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to load monthly summary: " + e.getMessage());
                }
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
        scrollpane = new javax.swing.JScrollPane();
        inventoryReportTable = new javax.swing.JTable();
        backBTN = new javax.swing.JButton();
        refreshBTN = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        reportTypeCMB = new javax.swing.JComboBox<>();
        downloadCSVBTN = new javax.swing.JButton();
        downloadPDFBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 249, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(16, 42, 80));
        jLabel1.setText("Report Management");

        scrollpane.setBackground(new java.awt.Color(255, 255, 255));
        scrollpane.setForeground(new java.awt.Color(16, 42, 80));

        inventoryReportTable.setForeground(new java.awt.Color(16, 42, 80));
        inventoryReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product", "Quantity", "Reorder Level", "Status"
            }
        ));
        inventoryReportTable.setRowHeight(26);
        inventoryReportTable.setSelectionBackground(new java.awt.Color(232, 240, 252));
        inventoryReportTable.setSelectionForeground(new java.awt.Color(16, 42, 80));
        inventoryReportTable.setShowHorizontalLines(true);
        scrollpane.setViewportView(inventoryReportTable);

        backBTN.setBackground(new java.awt.Color(229, 237, 247));
        backBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        backBTN.setForeground(new java.awt.Color(16, 42, 80));
        backBTN.setText("Back");
        backBTN.addActionListener(this::backBTNActionPerformed);

        refreshBTN.setBackground(new java.awt.Color(229, 237, 247));
        refreshBTN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        refreshBTN.setForeground(new java.awt.Color(16, 42, 80));
        refreshBTN.setText("Refresh");
        refreshBTN.addActionListener(this::refreshBTNActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Select Report Type:");

        reportTypeCMB.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        reportTypeCMB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Report", "Inventory Report", "Sales Report", "Purchase Report", "Payment Report", "Monthly Summary" }));
        reportTypeCMB.addActionListener(this::reportTypeCMBActionPerformed);

        downloadCSVBTN.setBackground(new java.awt.Color(102, 255, 102));
        downloadCSVBTN.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        downloadCSVBTN.setText("Download CSV");
        downloadCSVBTN.addActionListener(this::downloadCSVBTNActionPerformed);

        downloadPDFBTN.setBackground(new java.awt.Color(204, 51, 0));
        downloadPDFBTN.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        downloadPDFBTN.setForeground(new java.awt.Color(255, 255, 255));
        downloadPDFBTN.setText("Download PDF");
        downloadPDFBTN.addActionListener(this::downloadPDFBTNActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(backBTN)
                        .addGap(209, 209, 209)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel2)
                        .addGap(57, 57, 57)
                        .addComponent(reportTypeCMB, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(refreshBTN)
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(downloadCSVBTN)
                .addGap(117, 117, 117)
                .addComponent(downloadPDFBTN)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(backBTN)))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(reportTypeCMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(refreshBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(downloadCSVBTN)
                    .addComponent(downloadPDFBTN))
                .addGap(82, 82, 82))
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

    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_backBTNActionPerformed

    private void refreshBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBTNActionPerformed
        // TODO add your handling code here:
        loadInventoryReport();
    }//GEN-LAST:event_refreshBTNActionPerformed

    private void reportTypeCMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportTypeCMBActionPerformed
        // TODO add your handling code here:
         loadSelectedReport();
    }//GEN-LAST:event_reportTypeCMBActionPerformed

    private void downloadCSVBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadCSVBTNActionPerformed
        // TODO add your handling code here:
        if (reportTypeCMB.getSelectedIndex() <= 0) {
        javax.swing.JOptionPane.showMessageDialog(
                this, "Please select a report first.");
        return;
            }

            javax.swing.JFileChooser chooser =
                    new javax.swing.JFileChooser();

            chooser.setDialogTitle("Save Report as CSV");
            chooser.setSelectedFile(new java.io.File(
                    reportTypeCMB.getSelectedItem()
                            .toString().replace(" ", "_") + ".csv"));

            if (chooser.showSaveDialog(this)
                    != javax.swing.JFileChooser.APPROVE_OPTION) {
                return;
            }

            java.io.File file = chooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new java.io.File(file.getAbsolutePath() + ".csv");
            }

            try (java.io.PrintWriter writer =
                    new java.io.PrintWriter(
                            new java.io.OutputStreamWriter(
                                    new java.io.FileOutputStream(file),
                                    java.nio.charset.StandardCharsets.UTF_8))) {

                javax.swing.table.TableModel model =
                        inventoryReportTable.getModel();

                // Write column headings
                for (int col = 0; col < model.getColumnCount(); col++) {
                    if (col > 0) {
                        writer.print(",");
                    }

                    writer.print(csvEscape(
                            model.getColumnName(col)));
                }
                writer.println();

                // Write table data
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        if (col > 0) {
                            writer.print(",");
                        }

                        Object value = model.getValueAt(row, col);

                        writer.print(csvEscape(
                                value == null ? "" : value.toString()));
                    }
                    writer.println();
                }

                javax.swing.JOptionPane.showMessageDialog(
                        this, "CSV report downloaded successfully!");

            } catch (java.io.IOException e) {
                javax.swing.JOptionPane.showMessageDialog(
                        this, "Failed to save CSV: " + e.getMessage());
            }
        }

        private String csvEscape(String value) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
    }//GEN-LAST:event_downloadCSVBTNActionPerformed

    private void downloadPDFBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadPDFBTNActionPerformed
        // TODO add your handling code here:
        if (reportTypeCMB.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(
                this, "Please select a report first.");
        return;
    }

            if (inventoryReportTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                        this, "There is no data to export.");
                return;
            }

            String reportName =
                    reportTypeCMB.getSelectedItem().toString();

            javax.swing.JFileChooser chooser =
                    new javax.swing.JFileChooser();

            chooser.setDialogTitle("Save Professional PDF Report");
            chooser.setSelectedFile(new java.io.File(
                    reportName.replace(" ", "_") + ".pdf"));

            if (chooser.showSaveDialog(this)
                    != javax.swing.JFileChooser.APPROVE_OPTION) {
                return;
            }

            java.io.File file = chooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".pdf")) {
                file = new java.io.File(file.getAbsolutePath() + ".pdf");
            }

            Document document = new Document(
                    com.itextpdf.text.PageSize.A4.rotate(),
                    36, 36, 40, 40);

            try {
                PdfWriter.getInstance(
                        document, new FileOutputStream(file));

                document.open();

                // Fonts
                com.itextpdf.text.Font companyFont =
                        new com.itextpdf.text.Font(
                                com.itextpdf.text.Font.FontFamily.HELVETICA,
                                20, com.itextpdf.text.Font.BOLD);

                com.itextpdf.text.Font titleFont =
                        new com.itextpdf.text.Font(
                                com.itextpdf.text.Font.FontFamily.HELVETICA,
                                15, com.itextpdf.text.Font.BOLD);

                com.itextpdf.text.Font normalFont =
                        new com.itextpdf.text.Font(
                                com.itextpdf.text.Font.FontFamily.HELVETICA,
                                10, com.itextpdf.text.Font.NORMAL);

                com.itextpdf.text.Font headerFont =
                        new com.itextpdf.text.Font(
                                com.itextpdf.text.Font.FontFamily.HELVETICA,
                                10, com.itextpdf.text.Font.BOLD,
                                com.itextpdf.text.BaseColor.WHITE);

                // Company name
                Paragraph company = new Paragraph(
                        "S M CHEMICALS", companyFont);

                company.setAlignment(
                        com.itextpdf.text.Element.ALIGN_CENTER);

                document.add(company);

                // Report title
                Paragraph title = new Paragraph(
                        reportName.toUpperCase(), titleFont);

                title.setAlignment(
                        com.itextpdf.text.Element.ALIGN_CENTER);

                title.setSpacingBefore(8);
                title.setSpacingAfter(8);

                document.add(title);

                // Generation date
                Paragraph generated = new Paragraph(
                        "Generated on: "
                        + new java.text.SimpleDateFormat(
                                "dd MMM yyyy, hh:mm a")
                                .format(new java.util.Date()),
                        normalFont);

                generated.setAlignment(
                        com.itextpdf.text.Element.ALIGN_RIGHT);

                generated.setSpacingAfter(15);

                document.add(generated);

                // Create report table
                javax.swing.table.TableModel model =
                        inventoryReportTable.getModel();

                PdfPTable pdfTable =
                        new PdfPTable(model.getColumnCount());

                pdfTable.setWidthPercentage(100);
                pdfTable.setSpacingBefore(5);
                pdfTable.setHeaderRows(1);
                pdfTable.setSplitRows(true);

                // Column headings
                for (int col = 0;
                        col < model.getColumnCount(); col++) {

                    PdfPCell cell = new PdfPCell(
                            new Phrase(
                                    model.getColumnName(col),
                                    headerFont));

                    cell.setBackgroundColor(
                            new com.itextpdf.text.BaseColor(
                                    42, 100, 65));

                    cell.setHorizontalAlignment(
                            com.itextpdf.text.Element.ALIGN_CENTER);

                    cell.setVerticalAlignment(
                            com.itextpdf.text.Element.ALIGN_MIDDLE);

                    cell.setPadding(8);

                    pdfTable.addCell(cell);
                }

                // Report data with alternating row backgrounds
                for (int row = 0;
                        row < model.getRowCount(); row++) {

                    for (int col = 0;
                            col < model.getColumnCount(); col++) {

                        Object value = model.getValueAt(row, col);

                        String text;

                        if (value == null) {
                            text = "";
                        } else if (value instanceof Number
                            && (model.getColumnName(col).toLowerCase()
                                    .contains("amount")
                            || model.getColumnName(col).toLowerCase()
                                    .contains("sales")
                            || model.getColumnName(col).toLowerCase()
                                    .contains("purchases"))) {

                            text = String.format(
                                    "%,.2f", ((Number) value).doubleValue());

                        } else {
                            text = value.toString();
                        }

                        PdfPCell cell = new PdfPCell(
                                new Phrase(text, normalFont));

                        cell.setPadding(7);
                        cell.setVerticalAlignment(
                                com.itextpdf.text.Element.ALIGN_MIDDLE);

                        if (row % 2 == 0) {
                            cell.setBackgroundColor(
                                    new com.itextpdf.text.BaseColor(
                                            240, 248, 242));
                        } else {
                            cell.setBackgroundColor(
                                    com.itextpdf.text.BaseColor.WHITE);
                        }

                        // Align numeric columns to the right
                        if (value instanceof Number) {
                            cell.setHorizontalAlignment(
                                    com.itextpdf.text.Element.ALIGN_RIGHT);
                        }

                        pdfTable.addCell(cell);
                    }
                }
                
                
                
                    if ("Sales Report".equals(reportName)
                        || "Purchase Report".equals(reportName)
                        || "Payment Report".equals(reportName)) {

                        double totalAmount = 0;

                        for (int row = 0; row < model.getRowCount(); row++) {
                            int amountColumn = "Payment Report".equals(reportName) ? 3 : 5;
                            Object value = model.getValueAt(row, amountColumn);

                            if (value instanceof Number) {
                                totalAmount += ((Number) value).doubleValue();
                            }
                        }

                        String totalLabelText;

                        if ("Sales Report".equals(reportName)) {
                            totalLabelText = "TOTAL SALES";
                        } else if ("Purchase Report".equals(reportName)) {
                            totalLabelText = "TOTAL PURCHASES";
                        } else {
                            totalLabelText = "TOTAL PAYMENTS";
                        }

                        PdfPCell totalLabel = new PdfPCell(
                                new Phrase(totalLabelText, titleFont));

                        totalLabel.setColspan(5);
                        totalLabel.setHorizontalAlignment(
                                com.itextpdf.text.Element.ALIGN_RIGHT);
                        totalLabel.setPadding(8);
                        totalLabel.setBackgroundColor(
                                new com.itextpdf.text.BaseColor(220, 235, 223));

                        pdfTable.addCell(totalLabel);

                        PdfPCell totalValue = new PdfPCell(
                                new Phrase(
                                        String.format("%,.2f", totalAmount),
                                        titleFont));

                        totalValue.setHorizontalAlignment(
                                com.itextpdf.text.Element.ALIGN_RIGHT);
                        totalValue.setPadding(8);
                        totalValue.setBackgroundColor(
                                new com.itextpdf.text.BaseColor(220, 235, 223));

                        pdfTable.addCell(totalValue);
                    }
                    
                    if ("Inventory Report".equals(reportName)) {

                    int totalProducts = model.getRowCount();
                    int lowStockItems = 0;
                    int outOfStockItems = 0;

                    for (int row = 0; row < model.getRowCount(); row++) {

                        String status = model.getValueAt(row, 3).toString();

                        if ("Low Stock".equalsIgnoreCase(status)) {
                            lowStockItems++;
                        } else if ("Out of Stock".equalsIgnoreCase(status)) {
                            outOfStockItems++;
                        }
                    }

                    com.itextpdf.text.Font summaryFont =
                            new com.itextpdf.text.Font(
                                    normalFont.getFamily(),
                                    11,
                                    com.itextpdf.text.Font.BOLD);

                    PdfPCell summaryCell = new PdfPCell();

                    summaryCell.setColspan(4);
                    summaryCell.setPadding(10);
                    summaryCell.setBackgroundColor(
                            new com.itextpdf.text.BaseColor(220, 235, 223));

                    summaryCell.addElement(
                            new Phrase("INVENTORY SUMMARY", summaryFont));

                    summaryCell.addElement(
                            new Phrase("Total Products: " + totalProducts, normalFont));

                    summaryCell.addElement(
                            new Phrase("Low-Stock Items: " + lowStockItems, normalFont));

                    summaryCell.addElement(
                            new Phrase("Out-of-Stock Items: " + outOfStockItems, normalFont));

                    pdfTable.addCell(summaryCell);
                }



                document.add(pdfTable);

                document.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Professional PDF report saved successfully!");

            } catch (Exception e) {

                if (document.isOpen()) {
                    document.close();
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to generate PDF: " + e.getMessage());
            }
    }//GEN-LAST:event_downloadPDFBTNActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new InventoryReportForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBTN;
    private javax.swing.JButton downloadCSVBTN;
    private javax.swing.JButton downloadPDFBTN;
    private javax.swing.JTable inventoryReportTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton refreshBTN;
    private javax.swing.JComboBox<String> reportTypeCMB;
    private javax.swing.JScrollPane scrollpane;
    // End of variables declaration//GEN-END:variables
}
