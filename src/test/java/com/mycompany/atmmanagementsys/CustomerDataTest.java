package com.mycompany.atmmanagementsys;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerDataTest {

    @Test
    void testEmptyFields() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(null, "", "", "", "", null)
        );
    }

    @Test
    void testBoundaryCustomerId() {
        CustomerData customer = new CustomerData(1, "John", "123 Main St", "john.doe@example.com", "1234567890", 0);
        assertEquals(1, customer.getCustomerId());
        assertEquals(0, customer.getCustomerBalance());
    }

    @Test
    void testMaxCustomerId() {
        CustomerData customer = new CustomerData(9999, "Jane", "456 Elm St", "jane.doe@example.com", "0987654321", 500);
        assertEquals(9999, customer.getCustomerId());
        assertEquals(500, customer.getCustomerBalance());
    }

    @Test
    void testInvalidCustomerIdTooLarge() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(10000, "John", "123 Main St", "john.doe@example.com", "1234567890", 500)
        );
    }

    @Test
    void testInvalidNegativeCustomerId() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(-1, "John", "123 Main St", "john.doe@example.com", "1234567890", 500)
        );
    }

    @Test
    void testInvalidNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(1, "John", "123 Main St", "john.doe@example.com", "1234567890", -500)
        );
    }

    @Test
    void testZeroBalance() {
        CustomerData customer = new CustomerData(1, "John", "123 Main St", "john.doe@example.com", "1234567890", 0);
        assertEquals(0, customer.getCustomerBalance());
    }

    @Test
    void testMaxCustomerBalance() {
        CustomerData customer = new CustomerData(1, "John", "123 Main St", "john.doe@example.com", "1234567890", 1000000);
        assertEquals(1000000, customer.getCustomerBalance());
    }

    @Test
    void testNullCustomerId() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(null, "John", "123 Main St", "john.doe@example.com", "1234567890", 500)
        );
    }

    @Test
    void testEmptyNameField() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(1, "", "123 Main St", "john.doe@example.com", "1234567890", 500)
        );
    }

    @Test
    void testEmptyAddressField() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(1, "John", "", "john.doe@example.com", "1234567890", 500)
        );
    }

    @Test
    void testEmptyEmailField() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(1, "John", "123 Main St", "", "1234567890", 500)
        );
    }

    @Test
    void testEmptyPhoneField() {
        assertThrows(IllegalArgumentException.class, () -> 
            new CustomerData(1, "John", "123 Main St", "john.doe@example.com", "", 500)
        );
    }

    @Test
    void testValidCustomer() {
        CustomerData customer = new CustomerData(1, "John", "123 Main St", "john.doe@example.com", "1234567890", 1000);
        assertEquals(1, customer.getCustomerId());
        assertEquals("John", customer.getCustomerName());
        assertEquals("123 Main St", customer.getCustomerAddress());
        assertEquals("john.doe@example.com", customer.getCustomerEmail());
        assertEquals("1234567890", customer.getCustomerPhone());
        assertEquals(1000, customer.getCustomerBalance());
    }
}
