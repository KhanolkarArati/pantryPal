package com.student.pantry.studentPantry;

import com.student.pantry.studentPantry.dto.AdminUserDto;
import com.student.pantry.studentPantry.entity.AdminUser;
import com.student.pantry.studentPantry.entity.UserRole;
import com.student.pantry.studentPantry.repository.AdminUserJpa;
import com.student.pantry.studentPantry.factory.UserDtoFactory;
import com.student.pantry.studentPantry.service.AdminUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;

class AdminUserServiceImplTest {

    @InjectMocks
    private AdminUserServiceImpl adminUserServiceImpl;

    @Mock
    private AdminUserJpa adminUserJpa;

    @Mock
    private UserDtoFactory userDtoFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Using initMocks() for older versions of Mockito
    }

   // @Test
    //void testGetUserByEmail_Success() {
        // Arrange
     //   String email = "admin@example.com";
       // AdminUser adminUser = new AdminUser();
     //   adminUser.setUsername("admin");
       // adminUser.setEmail(email);
        //adminUser.setAdminId(1L);

        // Mock repository call
      //  when(adminUserJpa.findByEmail(email)).thenReturn(adminUser);
      //  when(userDtoFactory.createUserDTO(eq(UserRole.ADMIN.name()), eq(adminUser.getUsername()), eq(adminUser.getEmail()), eq(adminUser.getAdminId())))
           //     .thenReturn(new AdminUserDto(UserRole.ADMIN.name(), adminUser.getUsername(), adminUser.getEmail(), adminUser.getAdminId()));

        // Act
       // AdminUserDto result = adminUserServiceImpl.getUserByEmail(email);

        // Assert
      //  assertNotNull(result);
      //  assertEquals("admin", result.getUsername());
       // assertEquals(email, result.getEmail());
   // }
}
