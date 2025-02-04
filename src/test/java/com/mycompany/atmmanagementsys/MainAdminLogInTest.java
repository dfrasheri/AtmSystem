package com.mycompany.atmmanagementsys;
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
//Deni
public class MainAdminLogInTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainApp().start(stage); 
    }
    @Test
    public void testAdminEntrance() throws SQLException {
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
        Connection con = DriverManager.getConnection(url);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT balance FROM users WHERE id = '1'");

//control if the password is correctly changed AND saved inside hte database as updated
        assertTrue(rs.next());
        assertEquals("12345", rs.getString("balance"));
    }
      

}
