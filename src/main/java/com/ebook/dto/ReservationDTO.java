package com.ebook.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public class ReservationDTO {

    @NotNull(message = "Reservation date is mandatory")
    @PastOrPresent(message = "Reservation date must be in the past or present")
    private LocalDateTime reservationDate;

    @NotNull(message = "Reservation status is mandatory")
    private String status; // ReservationStatus as a String (can be changed to an Enum if needed)

    private Long userId; // User ID instead of full User object
    private Long bookId; // Book ID instead of full Book object

    public ReservationDTO() {
    }

    public ReservationDTO(LocalDateTime reservationDate, String status, Long userId, Long bookId) {
        this.reservationDate = reservationDate;
        this.status = status;
        this.userId = userId;
        this.bookId = bookId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}