package com.ebook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class UserDTO {

    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Invalid Email address")
    private String email;

    @Size(min = 8, message = "Password should be minimum 8 characters")
    private String password;

    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    private String role; // UserRole as String (or you can convert to enum if needed)

    private List<Long> borrowedBookIds; // List of borrowedBook IDs
    private List<Long> reservationIds;   // List of reservation IDs
    private List<Long> fineIds;          // List of fine IDs

    public UserDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDTO(){

    }
    public UserDTO(String name, String email, String password, String phoneNumber, String address, String role,
                   List<Long> borrowedBookIds, List<Long> reservationIds, List<Long> fineIds) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.borrowedBookIds = borrowedBookIds;
        this.reservationIds = reservationIds;
        this.fineIds = fineIds;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Long> getBorrowedBookIds() {
        return borrowedBookIds;
    }

    public void setBorrowedBookIds(List<Long> borrowedBookIds) {
        this.borrowedBookIds = borrowedBookIds;
    }

    public List<Long> getReservationIds() {
        return reservationIds;
    }

    public void setReservationIds(List<Long> reservationIds) {
        this.reservationIds = reservationIds;
    }

    public List<Long> getFineIds() {
        return fineIds;
    }

    public void setFineIds(List<Long> fineIds) {
        this.fineIds = fineIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}