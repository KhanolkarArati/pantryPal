package com.student.pantry.studentPantry.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.student.pantry.studentPantry.dto.StudentUserDto;
import com.student.pantry.studentPantry.dto.UserDto;
import com.student.pantry.studentPantry.entity.StudentUser;
import com.student.pantry.studentPantry.entity.User;
import com.student.pantry.studentPantry.factory.UserDtoFactory;
import com.student.pantry.studentPantry.factory.UserEntityFactory;
import com.student.pantry.studentPantry.repository.UserJpa;
import com.student.pantry.studentPantry.dto.UserRole;

class StudentUserServiceImplTest {

    @InjectMocks
    private StudentUserServiceImpl studentUserService;

    @Mock
    private UserJpa userJpa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetUserByEmailAndPassword_ValidUser() {

        //Arrange
        User mockUser = new User();
        mockUser.setEmail("john@example.com");
        mockUser.setUserPasswd("password123");
        
        when(userJpa.findByEmailAndUserPasswd("john@example.com", "password123")).thenReturn(mockUser);
        
        //Act
        User result = studentUserService.getUserByEmailAndPassword("john@example.com", "password123");
        
        //Assert
        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void testGetUserByEmailAndPassword_InvalidUser() {
        
        //Arrange
        when(userJpa.findByEmailAndUserPasswd("invalid@example.com", "wrongpass")).thenReturn(null);
        
        //Act
        User result = studentUserService.getUserByEmailAndPassword("invalid@example.com", "wrongpass");
        
        //Assert
        assertNull(result);
    }
}