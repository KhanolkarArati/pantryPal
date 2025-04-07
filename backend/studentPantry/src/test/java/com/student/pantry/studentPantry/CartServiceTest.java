package com.student.pantry.studentPantry.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.student.pantry.studentPantry.entity.Products;
import com.student.pantry.studentPantry.entity.ShoppingCart;
import com.student.pantry.studentPantry.repository.ProductJpa;
import com.student.pantry.studentPantry.repository.ShoppingCartRepository;

class CartServiceTest {
    
    @InjectMocks
    private CartService cartService;
    
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    
    @Mock
    private ProductJpa productJpa;
    
    @Mock
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    void testAddProductToCart_NewProduct() {
        
        Long userID = 1L;
        int quantity = 2;
        
        //Arrange
        when(shoppingCartRepository.findByUserIDAndProductID(eq(userID), anyLong())).thenReturn(null);

        Products product = new Products();
        product.setProductName("Product A");
        product.setProductQuantity(10);
        product.setProductImageURL("image.jpg");

        when(productJpa.findById(anyLong())).thenReturn(Optional.of(product));
        
        //Act
        cartService.addProductToCart(userID, 101L, quantity);
        
        //Assert
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
        verify(productJpa, times(1)).save(any(Products.class));
    }
    
    @Test
    void testRemoveProductFromCart() {
        Long userID = 1L;

        //Arrange
        ShoppingCart cartItem = new ShoppingCart();
        cartItem.setCartProductID(1L);
        cartItem.setUserID(userID);
        cartItem.setProductQuantity(2);

        Products product = new Products();
        product.setProductName("Product A");
        product.setProductQuantity(10);
        product.setProductImageURL("image.jpg");
        
        when(shoppingCartRepository.findByUserIDAndProductID(eq(userID), anyLong())).thenReturn(cartItem);
        when(productJpa.findById(anyLong())).thenReturn(Optional.of(product));
        
        //Act
        cartService.removeProductFromCart(userID, 101L);
        
        //Assert
        verify(shoppingCartRepository, times(1)).delete(cartItem);
        verify(productJpa, times(1)).save(any(Products.class));
    }
    
    @Test
    void testUpdateProductQuantity() {
        Long userID = 1L;
        int newQuantity = 5;

        //Arrange
        ShoppingCart cartItem = new ShoppingCart();
        cartItem.setCartProductID(1L);
        cartItem.setUserID(userID);
        cartItem.setProductQuantity(2);
        
        when(shoppingCartRepository.findByUserIDAndProductID(eq(userID), anyLong())).thenReturn(cartItem);
        
        //Act
        cartService.updateProductQuantity(userID, 101L, newQuantity);
        
        //Assert
        assertEquals(newQuantity, cartItem.getProductQuantity());
        verify(shoppingCartRepository, times(1)).save(cartItem);
    }
    
    @Test
    void testViewCart() {
      

    }
}
