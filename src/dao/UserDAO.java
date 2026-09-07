/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Pasindu
 */
public class UserDAO {
    public String login(String username, String password) throws Exception {

        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";

        Connection con = DBConnection.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, username);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getString("role");
        }

        return null;
    }
}
