package com.ebook.domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class Reservation {

    private LocalDateTime reservation_date;
    private ReservationStatus status;

    /**
     * Entity RelationShips
     */
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookId",nullable = false)
    private Book book;

    public Reservation() {
    }

    public Reservation(LocalDateTime reservation_date, ReservationStatus status) {
        this.reservation_date = reservation_date;
        this.status = status;
    }

    public LocalDateTime getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(LocalDateTime reservation_date) {
        this.reservation_date = reservation_date;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
