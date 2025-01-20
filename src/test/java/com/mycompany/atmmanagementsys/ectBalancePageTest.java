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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ectBalancePageTest {

    private BalancePageController controller;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    private MockedStatic<DbConnection> mockedStaticDbConnection;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize JavaFX platform
        new JFXPanel();
        MockitoAnnotations.openMocks(this);

        // Mock static DbConnection method
        mockedStaticDbConnection = mockStatic(DbConnection.class);
        mockedStaticDbConnection.when(DbConnection::Connection).thenReturn(mockConnection);

        // Initialize controller
        controller = new BalancePageController();

        // Setup JavaFX fields on the controller
        Platform.runLater(() -> {
            controller.setWithdrawamount(new TextField());
            controller.setReceiverid(new TextField());
            controller.setRetypepass(new PasswordField()); // Directly access retypepass
            controller.setWithdrawinfo(new Label()); // Label for withdrawal messages
            controller.setTransferconf(new Label()); // Label for transfer messages
        });

        try {
            Thread.sleep(100); // Allow JavaFX thread to initialize
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
    void testWithdrawSuccess() throws SQLException {
        Platform.runLater(() -> {
            try {
                // Setup test data
                controller.getWithdrawamount().setText("1000"); // Amount to withdraw
                controller.getRetypepass().setText("correctpassword"); // Enter valid password

                // Mock database behavior
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful transaction

                // Execute method
                controller.WithdrawMoney(null);

                // Verify success message
                assertEquals("Transaction Successfull", controller.getWithdrawinfo().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testWithdrawInsufficientBalance() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.getWithdrawamount().setText("1000");
                controller.getRetypepass().setText("correctpassword");

                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Simulate failure

                controller.WithdrawMoney(null);

                assertEquals("Your Account Balance Is Low", controller.getWithdrawinfo().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testWithdrawMissingPassword() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.getWithdrawamount().setText("1000");
                controller.getRetypepass().setText(""); // Missing password

                controller.WithdrawMoney(null);

                assertEquals("Invalid Database Or Number Format", controller.getWithdrawinfo().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testTransferSuccess() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.getWithdrawamount().setText("2000"); 
                controller.getReceiverid().setText("456"); 
                controller.getRetypepass().setText("correctpassword");

                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(1); // successful transaction

                controller.TransferMoney(null);

                assertEquals("Transfer Successfull", controller.getTransferconf().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testTransferInsufficientBalance() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.getWithdrawamount().setText("2000");
                controller.getReceiverid().setText("456");
                controller.getRetypepass().setText("correctpassword");

                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(0); //  failure

                controller.TransferMoney(null);

                assertEquals("You Dont Have Enough Money To Transfer.", controller.getTransferconf().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testTransferIncorrectPassword() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.getWithdrawamount().setText("2000");
                controller.getReceiverid().setText("456");
                controller.getRetypepass().setText("wrongpassword"); // Incorrect password

                controller.TransferMoney(null);

                assertEquals("Wrong Password Transaction Failed.", controller.getTransferconf().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }
}