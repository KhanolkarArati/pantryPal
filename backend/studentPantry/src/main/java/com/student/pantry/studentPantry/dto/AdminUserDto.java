package com.student.pantry.studentPantry.dto;

import java.util.Objects;

public class AdminUserDto extends UserDto {
    private Long adminId;

    // Default constructor
    public AdminUserDto() {
    }

    // Constructor that takes only adminId (existing constructor)
    public AdminUserDto(Long adminId) {
        this.adminId = adminId;
    }

    // New constructor with userRole, username, email, and adminId
    public AdminUserDto(String userRole, String username, String email, Long adminId) {
    super(username, email, UserRole.valueOf(userRole)); // Call the new UserDto constructor
    this.adminId = adminId;
}

    // Getters and Setters
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    // Equals and hashCode methods
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof AdminUserDto)) return false;
        if (!super.equals(object)) return false;
        AdminUserDto adminDTO = (AdminUserDto) object;
        return java.util.Objects.equals(getAdminId(), adminDTO.getAdminId());
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), getAdminId());
    }

    // toString method
    @Override
    public String toString() {
        return "AdminDTO{" +
                "adminId=" + adminId +
                '}';
    }
}
