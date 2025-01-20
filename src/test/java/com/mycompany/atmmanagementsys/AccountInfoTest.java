package com.mycompany.atmmanagementsys;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountInfoTest {
	//bvt

    private AccountInfoController controller;

    // Mocked dependencies
    private static Connection mockConnection;
    private static PreparedStatement mockPreparedStatement;
    private static ResultSet mockResultSet;

    // Static mock for DbConnection
    private static MockedStatic<DbConnection> mockedDbConnection;

    @BeforeAll
    static void initToolkitAndStaticMocks() throws InterruptedException {
        // Initialize JavaFX Toolkit once globally for all tests
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();

        // Set up static mocking for DbConnection
        mockedDbConnection = mockStatic(DbConnection.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock DbConnection.Connection() behavior to return the mocked connection
        mockedDbConnection.when(DbConnection::Connection).thenReturn(mockConnection);
    }

    @BeforeEach
    void setupController() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // Initialize controller and UI components on JavaFX thread
        Platform.runLater(() -> {
            controller = new AccountInfoController();
            controller.setUname(new TextField());
            controller.setUaddress(new TextArea());
            controller.setUemail(new TextField());
            controller.setUphone(new TextField());
            controller.setUbalance(new TextField());
            latch.countDown();
        });

        latch.await(); 
    }

    @AfterAll
    static void closeStaticMocks() {
        mockedDbConnection.close();
    }

    @Test
    void testEmptyUserID() throws Exception {
        controller.UserID = "";

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                controller.AccountInfo(null);
            } catch (Exception e) {
                fail("Method should handle empty UserID gracefully.");
            }

            // Ensure that no data is loaded into the text fields
            assertEquals("", controller.getUname().getText());
            assertEquals("", controller.getUaddress().getText());
            assertEquals("", controller.getUemail().getText());
            assertEquals("", controller.getUphone().getText());
            assertEquals("", controller.getUbalance().getText());

            latch.countDown();
        });

        latch.await(); 
    }

    @Test
    void testValidUserID() throws Exception {
        controller.UserID = "1";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Mock ResultSet with a valid record
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("address")).thenReturn("123 Main St");
        when(mockResultSet.getString("email")).thenReturn("john.doe@example.com");
        when(mockResultSet.getString("phone")).thenReturn("1234567890");
        when(mockResultSet.getString("balance")).thenReturn("500");

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                controller.AccountInfo(null);
            } catch (Exception e) {
                fail("No exception should be thrown for a valid user ID.");
            }

            assertEquals("John Doe", controller.getUname().getText());
            assertEquals("123 Main St", controller.getUaddress().getText());
            assertEquals("john.doe@example.com", controller.getUemail().getText());
            assertEquals("1234567890", controller.getUphone().getText());
            assertEquals("500", controller.getUbalance().getText());

            latch.countDown();
        });

        latch.await(); 
    }

    @Test
    void testNonexistentUserID() throws Exception {
        controller.UserID = "9999";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No record found

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                controller.AccountInfo(null);
            } catch (Exception e) {
                fail("Method should handle nonexistent UserID gracefully.");
            }

            assertEquals("", controller.getUname().getText());
            assertEquals("", controller.getUaddress().getText());
            assertEquals("", controller.getUemail().getText());
            assertEquals("", controller.getUphone().getText());
            assertEquals("", controller.getUbalance().getText());

            latch.countDown();
        });

        latch.await(); 
    }
}