package com.ebook.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BorrowedBookDTO {

    private Long id;
    @NotNull(message = "Borrow date is mandatory")
    private LocalDateTime borrowedDate;

    @NotNull(message = "Return date is mandatory")
    @FutureOrPresent(message = "Return date must be today or in the future")
    private LocalDateTime returnDate;

    private LocalDateTime returnedOn;

    @NotNull(message = "Borrow status is mandatory")
    private String status; // BorrowStatus enum will be converted to a String in the DTO

    @NotNull(message = "Cost is mandatory")
    private Double totalCost;
    private Long userId; // Referring to user ID, not the full User object
    private UserDTO userDetails;

    //private Long bookId; // Referring to book ID, not the full Book object
    private BookDTO bookDetails;

    private Long fineId; // Referring to fine ID, not the full Fine object

    public BorrowedBookDTO() {
    }

    public BorrowedBookDTO(LocalDateTime borrowedDate, LocalDateTime returnDate, LocalDateTime returnedOn, String status, Long userId, BookDTO bookDetails, Double totalCost, Long fineId) {
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        this.returnedOn = returnedOn;
        this.status = status;
        this.totalCost = totalCost;
        this.userId = userId;
        this.bookDetails = bookDetails;
        this.fineId = fineId;
    }

    // Getters and setters
    public LocalDateTime getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDateTime borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDateTime getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(LocalDateTime returnedOn) {
        this.returnedOn = returnedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BookDTO getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDTO bookDetails) {
        this.bookDetails = bookDetails;
    }
//    public Long getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Long bookId) {
//        this.bookId = bookId;
//    }

    public Long getFineId() {
        return fineId;
    }

    public void setFineId(Long fineId) {
        this.fineId = fineId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Cost is mandatory") Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(@NotNull(message = "Cost is mandatory") Double totalCost) {
        this.totalCost = totalCost;
    }

    public UserDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDTO userDetails) {
        this.userDetails = userDetails;
    }
}