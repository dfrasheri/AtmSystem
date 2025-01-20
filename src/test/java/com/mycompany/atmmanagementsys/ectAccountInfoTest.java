package com.mycompany.atmmanagementsys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.PasswordField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ectAccountInfoTest {

    private AccountInfoController controller;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    private MockedStatic<DbConnection> mockedStaticDbConnection;

    @BeforeEach
    void setUp() throws SQLException {
        new JFXPanel();
        MockitoAnnotations.openMocks(this);
        mockedStaticDbConnection = Mockito.mockStatic(DbConnection.class);
        mockedStaticDbConnection.when(DbConnection::Connection).thenReturn(mockConnection);
        controller = new AccountInfoController();
        Platform.runLater(() -> {
            PasswordField oldPass = new PasswordField();
            PasswordField newPass = new PasswordField();
            PasswordField passRetype = new PasswordField();
            controller.setOldpass(oldPass);
            controller.setNewpass(newPass);
            controller.setPassretype(passRetype);
        });
        try {
            Thread.sleep(100);
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
    void testChangePasswordSuccess() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.setOldPass("current123");
                controller.setNewPass("newpass123");
                controller.setPassRetype("newpass123");
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(1);
                controller.ChangePassword(null);
                assertEquals("Password Changed.", controller.getChangepassconf().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testWrongOldPassword() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.setOldPass("wrongPassword");
                controller.setNewPass("newpass123");
                controller.setPassRetype("newpass123");
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(0);
                controller.ChangePassword(null);
                assertEquals("Wrong Password.", controller.getChangepassconf().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testPasswordConfirmationMismatch() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.setOldPass("current123");
                controller.setNewPass("newpass123");
                controller.setPassRetype("differentpass123");
                controller.ChangePassword(null);
                assertEquals("Password Confirmation Failed", controller.getChangepassconf().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testEmptyFields() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.setOldPass("");
                controller.setNewPass("");
                controller.setPassRetype("");
                controller.ChangePassword(null);
                assertEquals("Please Fill Up Everything Correctly.", controller.getChangepassconf().getText());
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }

    @Test
    void testSQLExceptionHandling() throws SQLException {
        Platform.runLater(() -> {
            try {
                controller.setOldPass("current123");
                controller.setNewPass("newpass123");
                controller.setPassRetype("newpass123");
                when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));
                assertThrows(SQLException.class, () -> controller.ChangePassword(null));
            } catch (SQLException e) {
                fail("Unexpected SQLException: " + e.getMessage());
            }
        });
    }
}
