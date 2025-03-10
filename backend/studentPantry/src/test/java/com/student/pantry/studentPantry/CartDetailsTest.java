package com.student.pantry.studentPantry.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartDetailsTest {

    @Test
    void testCartDetailsConstructorAndGetters() {
        // Arrange
        Long cartProductID = 1L;
        Long userID = 100L;
        Long productID = 200L;
        String productName = "Apple";
        int productQuantity = 5;
        String productImageURL = "http://example.com/apple.jpg";

        // Act
        CartDetails cartDetails = new CartDetails(cartProductID, userID, productID, productName, productQuantity, productImageURL);

        // Assert
        assertEquals(cartProductID, cartDetails.getCartProductID());
        assertEquals(userID, cartDetails.getUserID());
        assertEquals(productID, cartDetails.getProductID());
        assertEquals(productName, cartDetails.getProductName());
        assertEquals(productQuantity, cartDetails.getProductQuantity());
        assertEquals(productImageURL, cartDetails.getProductImageURL());
    }

    @Test
    void testCartDetailsSetters() {
        // Arrange
        CartDetails cartDetails = new CartDetails();

        // Act
        cartDetails.setCartProductID(2L);
        cartDetails.setUserID(101L);
        cartDetails.setProductID(201L);
        cartDetails.setProductName("Banana");
        cartDetails.setProductQuantity(10);
        cartDetails.setProductImageURL("http://example.com/banana.jpg");

        // Assert
        assertEquals(2L, cartDetails.getCartProductID());
        assertEquals(101L, cartDetails.getUserID());
        assertEquals(201L, cartDetails.getProductID());
        assertEquals("Banana", cartDetails.getProductName());
        assertEquals(10, cartDetails.getProductQuantity());
        assertEquals("http://example.com/banana.jpg", cartDetails.getProductImageURL());
    }

    @Test
    void testToStringMethod() {
        // Arrange
        CartDetails cartDetails = new CartDetails(3L, 102L, 202L, "Grapes", 15, "http://example.com/grapes.jpg");
        String expectedString = "CartDetails{cartProductID=3, userID=102, productID=202, productName='Grapes', productQuantity=15, productImageURL='http://example.com/grapes.jpg'}";

        // Act & Assert
        assertEquals(expectedString, cartDetails.toString());
    }
}
