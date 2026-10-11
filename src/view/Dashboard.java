/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JLabel;
/**
 *
 * @author Pasindu
 */
public class Dashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dashboard.class.getName());

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        
            // Set equal sizes for both chart panels
            jPanel7.setPreferredSize(new java.awt.Dimension(430, 220));
            jPanel8.setPreferredSize(new java.awt.Dimension(430, 220));

            // Keep the chart panels from growing too large
            jPanel7.setMaximumSize(new java.awt.Dimension(450, 230));
            jPanel8.setMaximumSize(new java.awt.Dimension(450, 230));
            
            jPanel9.setPreferredSize(new java.awt.Dimension(700, 150));
            jPanel9.setMinimumSize(new java.awt.Dimension(700, 150));
            jPanel9.setMaximumSize(new java.awt.Dimension(700, 150));
            

            // Set a reasonable size for the dashboard window
            setSize(1250, 700);
            setLocationRelativeTo(null);
        
            loadSalesChart();
            loadInventoryChart();
        
            loadDashboardSummary();
            loadRecentActivity();
        
    }
    

        private void loadDashboardSummary() {
            dao.DashboardDAO dao = new dao.DashboardDAO();

            customerCardTitleLBL.setText(
                    String.valueOf(dao.getCustomerCount()));

            productCardTitleLBL.setText(
                    String.valueOf(dao.getProductCount()));

            supplierCardTitleLBL.setText(
                    String.valueOf(dao.getSupplierCount()));

            salesTotalLBL.setText("Total Sales");

            jLabel9.setText(String.format(
                    java.util.Locale.US,
                    "Rs. %,.2f",
                    dao.getTotalSales()));
        }
        
        
        
                
                    private void loadSalesChart() {

                        dao.DashboardDAO dao = new dao.DashboardDAO();

                        org.jfree.data.category.DefaultCategoryDataset dataset =
                                new org.jfree.data.category.DefaultCategoryDataset();

                        java.util.Map<String, Double> sales = dao.getMonthlySales();

                        for (java.util.Map.Entry<String, Double> entry : sales.entrySet()) {
                            dataset.addValue(entry.getValue(), "Sales", entry.getKey());
                        }

                        org.jfree.chart.JFreeChart chart =
                                org.jfree.chart.ChartFactory.createBarChart(
                                        "Monthly Sales",
                                        "Month",
                                        "Sales Amount (Rs.)",
                                        dataset,
                                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                                        false,
                                        true,
                                        false
                                );

                        // Overall chart background
                        chart.setBackgroundPaint(java.awt.Color.WHITE);

                        // Chart title
                        chart.getTitle().setFont(
                                new java.awt.Font("Arial", java.awt.Font.BOLD, 16)
                        );
                        chart.getTitle().setPaint(new java.awt.Color(25, 49, 83));

                        // Plot background and styling
                        org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();

                        plot.setBackgroundPaint(java.awt.Color.WHITE);
                        plot.setOutlineVisible(false);
                        plot.setRangeGridlinePaint(new java.awt.Color(232, 236, 241));

                        // Remove the default plot shadow
                        plot.setShadowGenerator(null);

                        // Navy blue sales bars
                        org.jfree.chart.renderer.category.BarRenderer renderer =
                                (org.jfree.chart.renderer.category.BarRenderer)
                                        plot.getRenderer();
                        
                        renderer.setItemMargin(0.25);

                        renderer.setSeriesPaint(0, new java.awt.Color(25, 49, 83));
                        renderer.setDrawBarOutline(false);
                        
                        renderer.setDefaultItemLabelsVisible(true);
                        renderer.setDefaultItemLabelGenerator(
                                new org.jfree.chart.labels.StandardCategoryItemLabelGenerator(
                                        "Rs. {2}", new java.text.DecimalFormat("#,##0.00")
                                )
                        );
                        
                        renderer.setDefaultItemLabelFont(
                                    new java.awt.Font("Arial", java.awt.Font.BOLD, 11)
                            );

                            renderer.setDefaultItemLabelPaint(
                                    new java.awt.Color(25, 49, 83)
                            );

                        // Category axis
                        org.jfree.chart.axis.CategoryAxis domainAxis =
                                plot.getDomainAxis();

                        domainAxis.setLabelFont(
                                new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
                        );
                        domainAxis.setTickLabelFont(
                                new java.awt.Font("Arial", java.awt.Font.PLAIN, 11)
                        );
                        domainAxis.setLabelPaint(new java.awt.Color(70, 80, 95));
                        domainAxis.setTickLabelPaint(new java.awt.Color(70, 80, 95));

                        // Value axis
                        org.jfree.chart.axis.ValueAxis rangeAxis =
                                plot.getRangeAxis();
                        rangeAxis.setUpperMargin(0.20);

                        rangeAxis.setLabelFont(
                                new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
                        );
                        rangeAxis.setTickLabelFont(
                                new java.awt.Font("Arial", java.awt.Font.PLAIN, 11)
                        );
                        rangeAxis.setLabelPaint(new java.awt.Color(70, 80, 95));
                        rangeAxis.setTickLabelPaint(new java.awt.Color(70, 80, 95));

                        // Keep the existing chart panel layout and dimensions
                        jPanel7.removeAll();
                        jPanel7.setLayout(new java.awt.BorderLayout());

                        org.jfree.chart.ChartPanel chartPanel =
                                new org.jfree.chart.ChartPanel(chart);

                        chartPanel.setBackground(java.awt.Color.WHITE);
                        chartPanel.setPreferredSize(new java.awt.Dimension(400, 200));
                        chartPanel.setMinimumDrawWidth(200);
                        chartPanel.setMinimumDrawHeight(150);
                        chartPanel.setMaximumDrawWidth(2000);
                        chartPanel.setMaximumDrawHeight(1200);

                        jPanel7.add(chartPanel, java.awt.BorderLayout.CENTER);

                        jPanel7.revalidate();
                        jPanel7.repaint();
                    }



        
                
                private void loadInventoryChart() {

                    dao.DashboardDAO dao = new dao.DashboardDAO();

                    org.jfree.data.general.DefaultPieDataset<String> dataset =
                            new org.jfree.data.general.DefaultPieDataset<>();

                    java.util.Map<String, Integer> status = dao.getInventoryStatus();

                    for (java.util.Map.Entry<String, Integer> entry : status.entrySet()) {
                        dataset.setValue(entry.getKey(), entry.getValue());
                    }

                    org.jfree.chart.JFreeChart chart =
                            org.jfree.chart.ChartFactory.createPieChart(
                                    "Inventory Status",
                                    dataset,
                                    true,
                                    true,
                                    false
                            );

                    // Overall chart background
                    chart.setBackgroundPaint(java.awt.Color.WHITE);

                    // Chart title
                    chart.getTitle().setFont(
                            new java.awt.Font("Arial", java.awt.Font.BOLD, 16)
                    );
                    chart.getTitle().setPaint(
                            new java.awt.Color(25, 49, 83)
                    );

                    // Pie chart styling
                    org.jfree.chart.plot.PiePlot<String> plot =
                            (org.jfree.chart.plot.PiePlot<String>) chart.getPlot();

                    plot.setBackgroundPaint(java.awt.Color.WHITE);
                    plot.setOutlineVisible(false);
                    plot.setOutlineVisible(false);

                    // Remove the default shadow
                    plot.setShadowPaint(null);
                    plot.setShadowXOffset(0.0);
                    plot.setShadowYOffset(0.0);

                    // Match slice colors to the dashboard theme
                    plot.setSectionPaint(
                            "Available",
                            new java.awt.Color(22, 132, 91)
                    );
                    plot.setSectionPaint(
                            "Low Stock",
                            new java.awt.Color(233, 162, 59)
                    );

                    // Clean labels
                    plot.setLabelFont(
                            new java.awt.Font("Arial", java.awt.Font.PLAIN, 11)
                    );
                    plot.setLabelPaint(new java.awt.Color(25, 49, 83));
                    
                    plot.setLabelGenerator(null);

                    // Keep the existing chart panel layout and dimensions
                    jPanel8.removeAll();
                    jPanel8.setLayout(new java.awt.BorderLayout());

                    org.jfree.chart.ChartPanel chartPanel =
                            new org.jfree.chart.ChartPanel(chart);

                    chartPanel.setBackground(java.awt.Color.WHITE);
                    chartPanel.setPreferredSize(new java.awt.Dimension(400, 200));
                    chartPanel.setMinimumDrawWidth(200);
                    chartPanel.setMinimumDrawHeight(150);
                    chartPanel.setMaximumDrawWidth(2000);
                    chartPanel.setMaximumDrawHeight(1200);

                    jPanel8.add(chartPanel, java.awt.BorderLayout.CENTER);

                    jPanel8.revalidate();
                    jPanel8.repaint();
                }


        
        
           
        
            private void loadRecentActivity() {

                dao.DashboardDAO dao = new dao.DashboardDAO();

                jPanel9.removeAll();
                jPanel9.setLayout(new java.awt.BorderLayout());
                jPanel9.setBackground(java.awt.Color.WHITE);

                // Section heading
                JLabel title = new JLabel("Recent Activity");
                title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
                title.setForeground(new java.awt.Color(25, 49, 83));
                title.setBorder(
                        javax.swing.BorderFactory.createEmptyBorder(12, 15, 10, 15)
                );

                // Heading with a separator underneath
                javax.swing.JPanel headerPanel = new javax.swing.JPanel(
                        new java.awt.BorderLayout()
                );

                headerPanel.setBackground(java.awt.Color.WHITE);
                headerPanel.add(title, java.awt.BorderLayout.CENTER);

                javax.swing.JSeparator headerSeparator =
                        new javax.swing.JSeparator();

                headerSeparator.setForeground(new java.awt.Color(220, 225, 232));
                headerSeparator.setBackground(new java.awt.Color(220, 225, 232));

                headerPanel.add(headerSeparator, java.awt.BorderLayout.SOUTH);

                jPanel9.add(headerPanel, java.awt.BorderLayout.NORTH);

                // List of recent sales
                javax.swing.JPanel activityList = new javax.swing.JPanel();
                activityList.setLayout(
                        new javax.swing.BoxLayout(
                                activityList,
                                javax.swing.BoxLayout.Y_AXIS
                        )
                );
                activityList.setBackground(java.awt.Color.WHITE);

                java.util.List<String[]> activities = dao.getRecentActivity();

                if (activities.isEmpty()) {
                    JLabel emptyLabel = new JLabel("No recent sales records.");
                    emptyLabel.setFont(
                            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
                    );
                    emptyLabel.setBorder(
                            javax.swing.BorderFactory.createEmptyBorder(12, 15, 12, 15)
                    );
                    activityList.add(emptyLabel);

                } else {
                    for (int i = 0; i < activities.size(); i++) {

                        String[] activity = activities.get(i);

                        javax.swing.JPanel row = new javax.swing.JPanel(
                                new java.awt.BorderLayout(15, 0)
                        );
                        row.setBackground(java.awt.Color.WHITE);
                        
                        row.setBorder(
                                javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18)
                            );
                        row.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                        row.setMaximumSize(
                                new java.awt.Dimension(Integer.MAX_VALUE, 48)
                        );

                        JLabel saleLabel = new JLabel(activity[0]);
                        saleLabel.setFont(
                                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
                        );
                        saleLabel.setForeground(new java.awt.Color(25, 49, 83));

                        JLabel dateLabel = new JLabel(activity[1]);
                        dateLabel.setFont(
                                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)
                        );
                        dateLabel.setForeground(new java.awt.Color(100, 110, 125));

                        JLabel amountLabel = new JLabel(activity[2]);
                        amountLabel.setFont(
                                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
                        );
                        amountLabel.setForeground(new java.awt.Color(35, 125, 85));

                        row.add(saleLabel, java.awt.BorderLayout.WEST);
                        row.add(dateLabel, java.awt.BorderLayout.CENTER);
                        row.add(amountLabel, java.awt.BorderLayout.EAST);
                        
                        
                        java.awt.Color normalColor = java.awt.Color.WHITE;
                        java.awt.Color hoverColor = new java.awt.Color(240, 246, 255);

                        java.awt.event.MouseAdapter hoverListener =
                                new java.awt.event.MouseAdapter() {

                            @Override
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                row.setBackground(hoverColor);
                            }

                            @Override
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                java.awt.Component source =
                                        (java.awt.Component) evt.getSource();

                                java.awt.Point point = javax.swing.SwingUtilities.convertPoint(
                                        source, evt.getPoint(), row
                                );

                                if (!row.contains(point)) {
                                    row.setBackground(normalColor);
                                }
                            }
                        };

                        row.addMouseListener(hoverListener);
                        saleLabel.addMouseListener(hoverListener);
                        dateLabel.addMouseListener(hoverListener);
                        amountLabel.addMouseListener(hoverListener);


                        activityList.add(row);

                        if (i < activities.size() - 1) {
                            javax.swing.JSeparator separator =
                                    new javax.swing.JSeparator();

                            separator.setForeground(new java.awt.Color(235, 238, 242));
                            separator.setMaximumSize(
                                    new java.awt.Dimension(Integer.MAX_VALUE, 1)
                            );

                            activityList.add(separator);
                        }
                    }
                }

                javax.swing.JScrollPane scrollPane =
                        new javax.swing.JScrollPane(activityList);

                scrollPane.setBorder(null);
                scrollPane.setHorizontalScrollBarPolicy(
                        javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                );
                scrollPane.setVerticalScrollBarPolicy(
                        javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                );

                // Slim, modern scrollbar
                javax.swing.JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setPreferredSize(new java.awt.Dimension(8, 0));
                bar.setUnitIncrement(16);

                jPanel9.add(scrollPane, java.awt.BorderLayout.CENTER);

                jPanel9.revalidate();
                jPanel9.repaint();
            }
            
            
            private void styleSidebarButtons() {

                javax.swing.JButton[] buttons = {
                    dashboardBTN,
                    customerBTN,
                    productBTN,
                    inventoryBTN,
                    supplierBTN,
                    purchaseBTN,
                    salesBTN,
                    billingBTN,
                    paymentBTN,
                    reportsBTN
                };

                for (javax.swing.JButton button : buttons) {
                    button.setOpaque(true);
                    button.setContentAreaFilled(true);
                    button.setFocusPainted(false);
                    button.setBorderPainted(false);
                    button.setBackground(java.awt.Color.WHITE);
                    button.setForeground(new java.awt.Color(25, 49, 83));
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

        BorderLayout = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dashboardBTN = new javax.swing.JButton();
        customerBTN = new javax.swing.JButton();
        productBTN = new javax.swing.JButton();
        inventoryBTN = new javax.swing.JButton();
        supplierBTN = new javax.swing.JButton();
        purchaseBTN = new javax.swing.JButton();
        salesBTN = new javax.swing.JButton();
        billingBTN = new javax.swing.JButton();
        paymentBTN = new javax.swing.JButton();
        reportsBTN = new javax.swing.JButton();
        reportsBTN1 = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        welcomeLBL = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        customerCountLBL = new javax.swing.JLabel();
        customerCardTitleLBL = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        productCountLBL = new javax.swing.JLabel();
        productCardTitleLBL = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        supplierCountLBL = new javax.swing.JLabel();
        supplierCardTitleLBL = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        salesTotalLBL = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        chartsContainer = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        customerCountLBL1 = new javax.swing.JLabel();
        customerCardTitleLBL1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        customerCountLBL2 = new javax.swing.JLabel();
        customerCardTitleLBL2 = new javax.swing.JLabel();
        welcomeLBL1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BorderLayout.setBackground(new java.awt.Color(23, 43, 77));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("S M CHEMICALS SYSTEM  ");

        dashboardBTN.setBackground(new java.awt.Color(230, 239, 252));
        dashboardBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        dashboardBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dashboard.png"))); // NOI18N
        dashboardBTN.setText("Dashboard");
        dashboardBTN.addActionListener(this::dashboardBTNActionPerformed);

        customerBTN.setBackground(new java.awt.Color(242, 246, 252));
        customerBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/customers.png"))); // NOI18N
        customerBTN.setText("Customers");
        customerBTN.addActionListener(this::customerBTNActionPerformed);

        productBTN.setBackground(new java.awt.Color(242, 246, 252));
        productBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        productBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/products.png"))); // NOI18N
        productBTN.setText("Products");
        productBTN.addActionListener(this::productBTNActionPerformed);

        inventoryBTN.setBackground(new java.awt.Color(242, 246, 252));
        inventoryBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        inventoryBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/inventory.png"))); // NOI18N
        inventoryBTN.setText("Inventory");
        inventoryBTN.addActionListener(this::inventoryBTNActionPerformed);

        supplierBTN.setBackground(new java.awt.Color(242, 246, 252));
        supplierBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        supplierBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/suppliers.png"))); // NOI18N
        supplierBTN.setText("Suppliers");
        supplierBTN.addActionListener(this::supplierBTNActionPerformed);

        purchaseBTN.setBackground(new java.awt.Color(242, 246, 252));
        purchaseBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        purchaseBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/purchases.png"))); // NOI18N
        purchaseBTN.setText("Purchases");
        purchaseBTN.addActionListener(this::purchaseBTNActionPerformed);

        salesBTN.setBackground(new java.awt.Color(242, 246, 252));
        salesBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        salesBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sales.png"))); // NOI18N
        salesBTN.setText("Sales");
        salesBTN.addActionListener(this::salesBTNActionPerformed);

        billingBTN.setBackground(new java.awt.Color(242, 246, 252));
        billingBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        billingBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/invoices.png"))); // NOI18N
        billingBTN.setText("Billing & Invoices");
        billingBTN.addActionListener(this::billingBTNActionPerformed);

        paymentBTN.setBackground(new java.awt.Color(242, 246, 252));
        paymentBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        paymentBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/payments.png"))); // NOI18N
        paymentBTN.setText("Payments");
        paymentBTN.addActionListener(this::paymentBTNActionPerformed);

        reportsBTN.setBackground(new java.awt.Color(242, 246, 252));
        reportsBTN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        reportsBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reports.png"))); // NOI18N
        reportsBTN.setText("Reports");
        reportsBTN.addActionListener(this::reportsBTNActionPerformed);

        reportsBTN1.setBackground(new java.awt.Color(255, 51, 51));
        reportsBTN1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        reportsBTN1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        reportsBTN1.setText("Logout");
        reportsBTN1.addActionListener(this::reportsBTN1ActionPerformed);

        javax.swing.GroupLayout BorderLayoutLayout = new javax.swing.GroupLayout(BorderLayout);
        BorderLayout.setLayout(BorderLayoutLayout);
        BorderLayoutLayout.setHorizontalGroup(
            BorderLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BorderLayoutLayout.createSequentialGroup()
                .addGroup(BorderLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BorderLayoutLayout.createSequentialGroup()
                        .addGroup(BorderLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BorderLayoutLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(BorderLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(reportsBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(paymentBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(salesBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(purchaseBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(supplierBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(inventoryBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(productBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(customerBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(billingBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(dashboardBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(BorderLayoutLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(reportsBTN1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BorderLayoutLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        BorderLayoutLayout.setVerticalGroup(
            BorderLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BorderLayoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(dashboardBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customerBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inventoryBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(supplierBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(purchaseBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salesBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(billingBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paymentBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reportsBTN)
                .addGap(18, 18, 18)
                .addComponent(reportsBTN1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contentPanel.setBackground(new java.awt.Color(244, 246, 249));

        welcomeLBL.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        welcomeLBL.setForeground(new java.awt.Color(25, 49, 83));
        welcomeLBL.setText("Welcome to S M Chemicals ");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 225, 232), 1, true));

        customerCountLBL.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        customerCountLBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card_customers_40_clean.png"))); // NOI18N
        customerCountLBL.setText("Total Customers");

        customerCardTitleLBL.setFont(new java.awt.Font("SansSerif", 1, 26)); // NOI18N
        customerCardTitleLBL.setForeground(new java.awt.Color(25, 49, 83));
        customerCardTitleLBL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        customerCardTitleLBL.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(customerCardTitleLBL)
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(customerCountLBL)
                        .addGap(14, 14, 14))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(customerCountLBL)
                .addGap(18, 18, 18)
                .addComponent(customerCardTitleLBL)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 225, 232)));

        productCountLBL.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productCountLBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card_products_40_clean.png"))); // NOI18N
        productCountLBL.setText("Total Products");

        productCardTitleLBL.setFont(new java.awt.Font("SansSerif", 1, 26)); // NOI18N
        productCardTitleLBL.setForeground(new java.awt.Color(25, 49, 83));
        productCardTitleLBL.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(productCountLBL))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(productCardTitleLBL)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(productCountLBL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productCardTitleLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 225, 232)));

        supplierCountLBL.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        supplierCountLBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card_suppliers_40_clean.png"))); // NOI18N
        supplierCountLBL.setText("Total Suppliers");

        supplierCardTitleLBL.setFont(new java.awt.Font("SansSerif", 1, 26)); // NOI18N
        supplierCardTitleLBL.setForeground(new java.awt.Color(25, 49, 83));
        supplierCardTitleLBL.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(supplierCountLBL)
                .addGap(15, 15, 15))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(supplierCardTitleLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(supplierCountLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(supplierCardTitleLBL)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 225, 232)));

        salesTotalLBL.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salesTotalLBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card_sales_40_clean.png"))); // NOI18N
        salesTotalLBL.setText("Total sales ");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(25, 132, 91));
        jLabel9.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(salesTotalLBL)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(salesTotalLBL)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(700, 150));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        chartsContainer.setBackground(new java.awt.Color(255, 255, 255));
        chartsContainer.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jPanel7.setBackground(new java.awt.Color(204, 255, 255));

        customerCountLBL1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        customerCountLBL1.setText("Sales analytics");

        customerCardTitleLBL1.setText("Chart");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerCardTitleLBL1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerCountLBL1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(306, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(customerCountLBL1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customerCardTitleLBL1)
                .addContainerGap(196, Short.MAX_VALUE))
        );

        chartsContainer.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 255, 255));

        customerCountLBL2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        customerCountLBL2.setText("Inventory status");

        customerCardTitleLBL2.setText("chart");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerCountLBL2)
                    .addComponent(customerCardTitleLBL2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(308, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(customerCountLBL2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customerCardTitleLBL2)
                .addContainerGap(196, Short.MAX_VALUE))
        );

        chartsContainer.add(jPanel8);

        welcomeLBL1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        welcomeLBL1.setForeground(new java.awt.Color(82, 97, 116));
        welcomeLBL1.setText("Business Management Dashboard");

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(chartsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE))
                            .addGroup(contentPanelLayout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addComponent(welcomeLBL))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(welcomeLBL1)))
                .addGap(492, 492, 492))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(welcomeLBL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(welcomeLBL1)
                .addGap(9, 9, 9)
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chartsContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BorderLayout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BorderLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardBTNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dashboardBTNActionPerformed

    private void customerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerBTNActionPerformed
        // TODO add your handling code here:
         CustomerForm customerForm = new CustomerForm();
         customerForm.setLocationRelativeTo(this);
         customerForm.setVisible(true);
    }//GEN-LAST:event_customerBTNActionPerformed

    private void productBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productBTNActionPerformed
        // TODO add your handling code here:
        ProductForm productForm = new ProductForm();
        productForm.setLocationRelativeTo(this);
        productForm.setVisible(true);
    }//GEN-LAST:event_productBTNActionPerformed

    private void inventoryBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryBTNActionPerformed
        // TODO add your handling code here:
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setLocationRelativeTo(this);
        inventoryForm.setVisible(true);
    }//GEN-LAST:event_inventoryBTNActionPerformed

    private void supplierBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierBTNActionPerformed
        // TODO add your handling code here:
        SupplierForm SupplierForm = new SupplierForm();
        SupplierForm.setLocationRelativeTo(this);
        SupplierForm.setVisible(true);
    }//GEN-LAST:event_supplierBTNActionPerformed

    private void purchaseBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchaseBTNActionPerformed
        // TODO add your handling code here:
        PurchaseForm purchaseForm = new PurchaseForm();
        purchaseForm.setLocationRelativeTo(this);
        purchaseForm.setVisible(true);
    }//GEN-LAST:event_purchaseBTNActionPerformed

    private void salesBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesBTNActionPerformed
        // TODO add your handling code here:
        SalesForm SalesForm = new SalesForm();
        SalesForm.setLocationRelativeTo(this);
        SalesForm.setVisible(true);
    }//GEN-LAST:event_salesBTNActionPerformed

    private void billingBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billingBTNActionPerformed
        // TODO add your handling code here:
        InvoiceForm invoiceForm = new InvoiceForm();
        invoiceForm.setLocationRelativeTo(this);
        invoiceForm.setVisible(true);
    }//GEN-LAST:event_billingBTNActionPerformed

    private void paymentBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentBTNActionPerformed
        // TODO add your handling code here:
        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setLocationRelativeTo(this);
        paymentForm.setVisible(true);
    }//GEN-LAST:event_paymentBTNActionPerformed

    private void reportsBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsBTNActionPerformed
        // TODO add your handling code here:
        InventoryReportForm reportForm = new InventoryReportForm();
        reportForm.setLocationRelativeTo(this);
        reportForm.setVisible(true);
    }//GEN-LAST:event_reportsBTNActionPerformed

    private void reportsBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsBTN1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_reportsBTN1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Dashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BorderLayout;
    private javax.swing.JButton billingBTN;
    private javax.swing.JPanel chartsContainer;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton customerBTN;
    private javax.swing.JLabel customerCardTitleLBL;
    private javax.swing.JLabel customerCardTitleLBL1;
    private javax.swing.JLabel customerCardTitleLBL2;
    private javax.swing.JLabel customerCountLBL;
    private javax.swing.JLabel customerCountLBL1;
    private javax.swing.JLabel customerCountLBL2;
    private javax.swing.JButton dashboardBTN;
    private javax.swing.JButton inventoryBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JButton paymentBTN;
    private javax.swing.JButton productBTN;
    private javax.swing.JLabel productCardTitleLBL;
    private javax.swing.JLabel productCountLBL;
    private javax.swing.JButton purchaseBTN;
    private javax.swing.JButton reportsBTN;
    private javax.swing.JButton reportsBTN1;
    private javax.swing.JButton salesBTN;
    private javax.swing.JLabel salesTotalLBL;
    private javax.swing.JButton supplierBTN;
    private javax.swing.JLabel supplierCardTitleLBL;
    private javax.swing.JLabel supplierCountLBL;
    private javax.swing.JLabel welcomeLBL;
    private javax.swing.JLabel welcomeLBL1;
    // End of variables declaration//GEN-END:variables
}
