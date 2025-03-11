package com.ebook.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

public class AuthenticationRequest {

    @Email(message = "Invalid Email address")
    private String email;
    @Size(min= 8, message = "Password Should be minimum 8 characters")
    @Column(name = "password", nullable = false)
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
