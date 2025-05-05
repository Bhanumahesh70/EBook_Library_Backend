package com.ebook.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class FineDTO {

    private Long id;
    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private double amount;

    @NotNull(message = "Fine status is mandatory")
    private String status; // FinePaidStatus enum as a String in the DTO

    @PastOrPresent(message = "Paid date must be in the past or present")
    private LocalDateTime paidDate;

    private Long borrowedBookId;
    private BorrowedBookDTO borrowedBookDetails;

    private Long userId;
    private UserDTO userDetails;

    public FineDTO() {
    }
    public FineDTO(double amount, String status, LocalDateTime paidDate) {
        this.amount = amount;
        this.status = status;
        this.paidDate = paidDate;
    }

    public FineDTO(double amount, String status, LocalDateTime paidDate, Long borrowedBookId, Long userId) {
        this.amount = amount;
        this.status = status;
        this.paidDate = paidDate;
        this.borrowedBookId = borrowedBookId;
        this.userId = userId;
    }

    // Getters and setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }

    public Long getBorrowedBookId() {
        return borrowedBookId;
    }

    public void setBorrowedBookId(Long borrowedBookId) {
        this.borrowedBookId = borrowedBookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BorrowedBookDTO getBorrowedBookDetails() {
        return borrowedBookDetails;
    }

    public void setBorrowedBookDetails(BorrowedBookDTO borrowedBookDetails) {
        this.borrowedBookDetails = borrowedBookDetails;
    }

    public UserDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDTO userDetails) {
        this.userDetails = userDetails;
    }

}