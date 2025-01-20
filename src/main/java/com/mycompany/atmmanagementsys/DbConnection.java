package com.mycompany.atmmanagementsys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection Connection() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Establish a connection to the database
            String url = "jdbc:sqlite:C:\\Users\\frash\\OneDrive\\Desktop\\Bank-Account-Simulation-master\\atmmanagementsys.sqlite";
            Connection con = DriverManager.getConnection(url);
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection to SQLite database failed.");
            e.printStackTrace();
        }
        return null;
    }
}
