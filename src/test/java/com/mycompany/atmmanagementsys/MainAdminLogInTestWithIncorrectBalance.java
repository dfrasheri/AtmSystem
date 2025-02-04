package com.mycompany.atmmanagementsys;
//Deni
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainAdminLogInTestWithIncorrectBalance extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainApp().start(stage); 
    }

    @Test
    public void testAdminEntranceWithIncorrectBalance() {
        //  valid admin credentials from the database with userId 1 and password 1
        clickOn("#useridtf").write("1");
        clickOn("#passwordtf").write("1");
        clickOn("#adminrb");
        clickOn("#loginb");
        sleep(2000);
        clickOn("#EditCustomer");
        sleep(200);
        clickOn("#cusid").write("1");
        clickOn("#cusname").write("1");
        clickOn("#load");
        sleep(5000);
        clickOn("#cusbalance").write("");
        clickOn("#cusbalance").write("12345");
        clickOn("#Save");
        sleep(5000);

        String url = "jdbc:sqlite:C:\\Users\\frash\\OneDrive\\Desktop\\Bank-Account-Simulation-master\\atmmanagementsys.sqlite";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(url);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT balance FROM users WHERE id = '1'");
            
            // Check if the balance is different from the expected value
            assertTrue(rs.next());
            String actualBalance = rs.getString("balance");
            String expectedBalance = "12345";
            assertNotEquals(expectedBalance, actualBalance, "Balance in the database is different from the expected value.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQLException was thrown: " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}