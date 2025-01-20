package com.mycompany.atmmanagementsys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ectAddCustomerTest {

    private AddCustomerController controller;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    private MockedStatic<DbConnection> mockedStaticDbConnection;

    @BeforeEach
    void setUp() throws SQLException {
        new JFXPanel();
        MockitoAnnotations.openMocks(this);

        mockedStaticDbConnection = mockStatic(DbConnection.class);
        mockedStaticDbConnection.when(DbConnection::Connection).thenReturn(mockConnection);

        controller = new AddCustomerController();

        Platform.runLater(() -> {
            controller.setCusid(new TextField());
            controller.setCuspass(new TextField());
            controller.setCusname(new TextField());
            controller.setCusaddress(new TextArea());
            controller.setCusemail(new TextField());
            controller.setCusphone(new TextField());
            controller.setBalance(new TextField());
            controller.setAddcusconfirm(new Label());
        });

        try {
            Thread.sleep(100); //  thread processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void tearDown() {
        if (mockedStaticDbConnection != null) {
            mockedStaticDbConnection.close();
        }
    }

    @Test
    void testAddCustomerSuccess() throws SQLException, FileNotFoundException {
        Platform.runLater(() -> {
            try {
                controller.getCusid().setText("1");
                controller.getCuspass().setText("password123");
                controller.getCusname().setText("John Doe");
                controller.getCusaddress().setText("123 Main St");
                controller.getCusemail().setText("john@example.com");
                controller.getCusphone().setText("1234567890");
                controller.getBalance().setText("500");
                controller.cf = 1; //  file has been uploaded
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(1); 
                controller.AddCustomer(null);

                assertEquals("New Customer Added Successfully", controller.getAddcusconfirm().getText());
            } catch (SQLException | FileNotFoundException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        });
    }

    @Test
    void testMissingFields() {
        Platform.runLater(() -> {
            controller.getCusid().setText("");
            controller.getCuspass().setText("");
            controller.getCusname().setText("");
            controller.getCusaddress().setText("123 Main St");
            controller.getCusemail().setText("john@example.com");
            controller.getCusphone().setText("1234567890");
            controller.getBalance().setText("500");

            try {
                controller.AddCustomer(null);
            } catch (SQLException | FileNotFoundException e) {
                fail("Unexpected exception: " + e.getMessage());
            }

            assertEquals("Please Fill Up Everything", controller.getAddcusconfirm().getText());
        });
    }

    @Test
    void testBalanceBelowMinimum() {
        Platform.runLater(() -> {
            controller.getCusid().setText("1");
            controller.getCuspass().setText("password123");
            controller.getCusname().setText("John Doe");
            controller.getCusaddress().setText("123 Main St");
            controller.getCusemail().setText("john@example.com");
            controller.getCusphone().setText("1234567890");
            controller.getBalance().setText("499"); 
            controller.cf = 1; 

            try {
                controller.AddCustomer(null);
            } catch (SQLException | FileNotFoundException e) {
                fail("Unexpected exception: " + e.getMessage());
            }

            assertEquals("Minimum Balance Requirement Is 500 Tk.", controller.getAddcusconfirm().getText());
        });
    }

    @Test
    void testImageNotUploaded() {
        Platform.runLater(() -> {
            controller.getCusid().setText("1");
            controller.getCuspass().setText("password123");
            controller.getCusname().setText("John Doe");
            controller.getCusaddress().setText("123 Main St");
            controller.getCusemail().setText("john@example.com");
            controller.getCusphone().setText("1234567890");
            controller.getBalance().setText("500");
            controller.cf = 0; // no file uploaded

            try {
                controller.AddCustomer(null);
            } catch (SQLException | FileNotFoundException e) {
                fail("Unexpected exception: " + e.getMessage());
            }

            assertEquals("Please Upload An Image To Add New Customer", controller.getAddcusconfirm().getText());
        });
    }

    @Test
    void testInvalidCustomerIDOrSQLException() {
        Platform.runLater(() -> {
            try {
                controller.getCusid().setText("InvalidID"); //invalid ID
                controller.getCuspass().setText("password123");
                controller.getCusname().setText("John Doe");
                controller.getCusaddress().setText("123 Main St");
                controller.getCusemail().setText("john@example.com");
                controller.getCusphone().setText("1234567890");
                controller.getBalance().setText("500");
                controller.cf = 1; // file uploaded

                when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

                controller.AddCustomer(null);

                assertEquals("Invalid Customer ID or ID Is Not Available", controller.getAddcusconfirm().getText());
            } catch (SQLException | FileNotFoundException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        });
    }
}