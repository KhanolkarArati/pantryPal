package com.student.pantry.studentPantry.dto;

import java.util.Objects;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String userPasswd;
    private UserRole userrole;

    // No-arg constructor
    public UserDto() {
    }

    // Constructor with id
    public UserDto(Long id, String username, String email, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userPasswd = password;
        this.userrole = role;
    }

    // Constructor without id
    public UserDto(String username, String email, UserRole role) {
        this.username = username;
        this.email = email;
        this.userrole = role;
    }

    // ✅ Added constructor to support username, email, password, and role
    public UserDto(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.userPasswd = password;
        this.userrole = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String password) {
        this.userPasswd = password;
    }

    public UserRole getUserrole() {
        return userrole;
    }

    public void setUserrole(UserRole role) {
        this.userrole = role;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UserDto)) return false;
        UserDto userDTO = (UserDto) object;
        return Objects.equals(getId(), userDTO.getId()) &&
               Objects.equals(getUsername(), userDTO.getUsername()) &&
               Objects.equals(getEmail(), userDTO.getEmail()) &&
               Objects.equals(getUserPasswd(), userDTO.getUserPasswd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getUserPasswd());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + userPasswd + '\'' +
                ", role='" + userrole + '\'' +
                '}';
    }
}
