package com.mycompany.atmmanagementsys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
//bvt
class ChangePasswordTest {

    private AccountInfoController controller;

    @BeforeAll
    static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> latch.countDown());

        latch.await(); 
    }

    @BeforeEach
    void setupController() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // Ensure Controller and UI setup is in the JavaFX Application Thread
        Platform.runLater(() -> {
            controller = new AccountInfoController();
            controller.setOldpass(new PasswordField());
            controller.setNewpass(new PasswordField());
            controller.setPassretype(new PasswordField());
            controller.setChangepassconf(new Label());
            latch.countDown(); // Signal completion
        });

        latch.await(); 
    }

    @Test
    void testEmptyNewPassword() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.setOldPass("oldpassword");
            controller.setNewPass("");
            controller.setPassRetype("");

            try {
                controller.ChangePassword(null);
                assertEquals("Please Fill Up Everything Correctly.", controller.getChangepassconf().getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });

        latch.await(); 
    }

    @Test
    void testEmptyOldPassword() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.setOldPass("");
            controller.setNewPass("123456");
            controller.setPassRetype("123456");

            try {
                controller.ChangePassword(null);
                assertEquals("Please Fill Up Everything Correctly.", controller.getChangepassconf().getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });

        latch.await(); 
    }

    @Test
    void testPasswordMismatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.setOldPass("oldpassword");
            controller.setNewPass("123456");
            controller.setPassRetype("654321");

            try {
                controller.ChangePassword(null);
                assertEquals("Password Confirmation Failed", controller.getChangepassconf().getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });

        latch.await(); 
    }

    @Test
    void testWrongOldPassword() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.setOldPass("wrongpassword");
            controller.setNewPass("123456");
            controller.setPassRetype("123456");

            try (MockedStatic<DbConnection> mockedDbConnection = mockStatic(DbConnection.class)) {
                Connection mockConnection = mock(Connection.class);
                PreparedStatement mockStatement = mock(PreparedStatement.class);

                mockedDbConnection.when(DbConnection::Connection).thenReturn(mockConnection);
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
                when(mockStatement.executeUpdate()).thenReturn(0); 

                controller.ChangePassword(null);
                assertEquals("Wrong Password.", controller.getChangepassconf().getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });

        latch.await(); 
    }

    @Test
    void testCorrectPasswordChange() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.setOldPass("oldpassword");
            controller.setNewPass("123456");
            controller.setPassRetype("123456");

            try (MockedStatic<DbConnection> mockedDbConnection = mockStatic(DbConnection.class)) {
                Connection mockConnection = mock(Connection.class);
                PreparedStatement mockStatement = mock(PreparedStatement.class);

                mockedDbConnection.when(DbConnection::Connection).thenReturn(mockConnection);
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
                when(mockStatement.executeUpdate()).thenReturn(1); // Simulate success

                controller.ChangePassword(null);
                assertEquals("Password Changed.", controller.getChangepassconf().getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });

        latch.await(); 
    }
}