package com.ebook.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public class ReservationDTO {

    private Long id;
    @NotNull(message = "Reservation date is mandatory")
    //@PastOrPresent(message = "Reservation date must be in the past or present")
    private LocalDateTime reservationDate;

    @NotNull(message = "No of Reservation days is mandatory")
    private int numberOfDays;
    @NotNull(message = "Reservation status is mandatory")
    private String status; // ReservationStatus as a String (can be changed to an Enum if needed)

    //private Long userId;
    private UserDTO userDetails;
    private Long bookId;
    private BookDTO bookDetails;

    public ReservationDTO() {
    }

    public ReservationDTO(Long id,Long bookId,String status,LocalDateTime reservationDate ) {
        this.bookId = bookId;
        this.id = id;
        this.status = status;
        this.reservationDate = reservationDate;
    }


    public ReservationDTO(LocalDateTime reservationDate, String status,UserDTO userDetails, BookDTO bookDetails , int numberOfDays) {
        this.reservationDate = reservationDate;
        this.status = status;
        //this.userId = userId;
       // this.bookId = bookId;
        this.userDetails = userDetails;
        this.bookDetails = bookDetails;
        this.numberOfDays = numberOfDays;
    }

    // Getters and Setters
    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public UserDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDTO userDetails) {
        this.userDetails = userDetails;
    }

    public BookDTO getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDTO bookDetails) {
        this.bookDetails = bookDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

}