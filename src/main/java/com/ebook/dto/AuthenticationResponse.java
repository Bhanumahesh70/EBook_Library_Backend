package com.ebook.dto;

public class AuthenticationResponse {

    private Long  id;
    private String token;
    private String role;

    public AuthenticationResponse(Long id, String token, String role) {
        this.id = id;
        this.token = token;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
