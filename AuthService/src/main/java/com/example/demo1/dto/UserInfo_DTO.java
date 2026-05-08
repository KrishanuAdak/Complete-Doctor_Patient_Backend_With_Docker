package com.example.demo1.dto;

public class UserInfo_DTO {
    private String email;
    private String password;
    private String role;
    public UserInfo_DTO(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public UserInfo_DTO() {
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
     

}
