/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author tiaraannisa
 */

public class DBConnection {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String namaDB = "jdbc:mysql://localhost/theMap";
    private String userDB = "root";
    private String passDB = "";

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getNamaDB() {
        return namaDB;
    }

    public void setNamaDB(String namaDB) {
        this.namaDB = namaDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public void setUserDB(String userDB) {
        this.userDB = userDB;
    }

    public String getPassDB() {
        return passDB;
    }

    public void setPassDB(String passDB) {
        this.passDB = passDB;
    }

    /**
     * open connection to database
     */
    public void openConnetion() {
        //memanggil driver connector database
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException x) {
            JOptionPane.showMessageDialog(null, x.getMessage());
        }

        //memanggil mysql server
        try {
            setConn(DriverManager.getConnection(namaDB, userDB, passDB));
            setStmt(conn.createStatement());
        } catch (SQLException x) {
            JOptionPane.showMessageDialog(null, x.getMessage());
        }

    }

    /**
     * used for getting the query result
     */
    public ResultSet getData(String query) {
        try {
            setRs(getStmt().executeQuery(query));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL 2 " + ex.getMessage());
        }
        return getRs();
    }

    /**
     * used for updating data
     */
    public int updateData(String query) {
        int jmlRecord = 0;
        try {
            jmlRecord = getStmt().executeUpdate(query);
//            JOptionPane.showMessageDialog(null, "Berhasil");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL 2 " + ex.getMessage());
        }
        return jmlRecord;
    }

    /**
     * close connection
     */
    public void closeConnection() {
        if (getRs() != null) {
            try {
                getRs().close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error SQL " + ex.getMessage());
            }
        }
    }
}
