/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.Dashboard;

/**
 *
 * @author Pasindu
 */
public class LoginController {
    public static void login(String username, String password, JFrame loginFrame) {

        UserDAO userDAO = new UserDAO();

        try {

            String role = userDAO.login(username, password);

            if (role != null) {

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "Login successful!"
                );

                loginFrame.dispose();

                Dashboard dashboard = new Dashboard();
                dashboard.setLocationRelativeTo(null);
                dashboard.setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "Invalid username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    loginFrame,
                    "Database error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}
