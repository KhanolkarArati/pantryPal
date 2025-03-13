package com.student.pantry.studentPantry.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.student.pantry.studentPantry.dto.UserDto;
import com.student.pantry.studentPantry.dto.UserRole;

class AdminLoginManagerTest {

    private AdminLoginManager adminLoginManager;
    private UserDto userDto;

    @BeforeEach
    void setUp() {

        AdminLoginManager.resetInstance();

        // Initialize AdminLoginManager before each test
        adminLoginManager = AdminLoginManager.getInstance();
        userDto = mock(UserDto.class); // Mock UserDto
    }

    @Test
    void testLogin_FirstAdminLogin() {
        // Arrange
        when(userDto.getUserrole()).thenReturn(UserRole.ADMIN);

        // Act
        boolean result = adminLoginManager.login(userDto);

        // Assert
        assertTrue(result, "First admin should be able to log in.");
    }

    @Test
    void testLogin_SecondAdminLogin() {
        // Arrange
        when(userDto.getUserrole()).thenReturn(UserRole.ADMIN);
        
        // First admin logs in
        adminLoginManager.login(userDto);

        // Act
        boolean result = adminLoginManager.login(userDto);

        // Assert
        assertFalse(result, "Second admin should not be able to log in.");
    }

}
