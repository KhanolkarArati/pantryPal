package com.student.pantry.studentPantry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // Ensures all tests use application-test.properties
class StudentPantryManagementApplicationTests {

    @Test
    void contextLoads() {
    }

}
