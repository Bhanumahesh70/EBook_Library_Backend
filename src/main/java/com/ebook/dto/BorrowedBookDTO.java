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
    private Double bookBorrowCost;
    private Long userId;
    private UserDTO userDetails;

    private Long bookId;
    private BookDTO bookDetails;

    private Long fineId;
    private FineDTO fineDetails;

    public BorrowedBookDTO() {
    }
    public BorrowedBookDTO(Long id,Long bookId,LocalDateTime borrowedDate, String status){
        this.id = id;
        this.status = status;
        this.bookId = bookId;
        this.borrowedDate = borrowedDate;
    }
    public BorrowedBookDTO(Long id,LocalDateTime borrowedDate, String status,BookDTO bookDetails ){
        this.id = id;
        this.status = status;
       this.bookDetails = bookDetails;
        this.borrowedDate = borrowedDate;

    }

    public BorrowedBookDTO(LocalDateTime borrowedDate, LocalDateTime returnDate, LocalDateTime returnedOn, String status, Long userId, BookDTO bookDetails, Double bookBorrowCost, Long fineId) {
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        this.returnedOn = returnedOn;
        this.status = status;
        this.bookBorrowCost = bookBorrowCost;
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
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

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

    public @NotNull(message = "Cost is mandatory") Double getBookBorrowCost() {
        return bookBorrowCost;
    }

    public void setBookBorrowCost(@NotNull(message = "Cost is mandatory") Double bookBorrowCost) {
        this.bookBorrowCost = bookBorrowCost;
    }

    public UserDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDTO userDetails) {
        this.userDetails = userDetails;
    }

    public FineDTO getFineDetails() {
        return fineDetails;
    }

    public void setFineDetails(FineDTO fineDetails) {
        this.fineDetails = fineDetails;
    }
}