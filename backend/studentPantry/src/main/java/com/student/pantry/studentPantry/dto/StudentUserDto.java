package com.student.pantry.studentPantry.dto;

import java.util.Objects;

public class StudentUserDto extends UserDto {
    private Long studentId;

    // No-arg constructor
    public StudentUserDto() {
    }

    // Constructor with only studentId
    public StudentUserDto(Long studentId) {
        this.studentId = studentId;
    }

    // ✅ Constructor for full initialization
    public StudentUserDto(String username, String email, String password, UserRole role, Long studentId) {
        super(username, email, password, role);  // Call parent class constructor
        this.studentId = studentId;
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof StudentUserDto)) return false;
        if (!super.equals(object)) return false;
        StudentUserDto that = (StudentUserDto) object;
        return Objects.equals(getStudentId(), that.getStudentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStudentId());
    }

    @Override
    public String toString() {
        return "StudentUserDto{" +
                "studentId=" + studentId +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + getUserrole() + '\'' +
                '}';
    }
}
