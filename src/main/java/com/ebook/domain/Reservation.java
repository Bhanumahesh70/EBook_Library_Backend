package com.ebook.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reservation")
@NamedQuery(name="Reservation.findAll",query="select r from Reservation r")
public class Reservation extends AbstractClass{

    @Column(name = "reservation_date", nullable = false)
    @NotNull(message = "Reservation date is mandatory")
    @PastOrPresent(message = "Reservation date must be in the past or present")
    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Reservation status is mandatory")
    private ReservationStatus status;

    /**
     * Entity RelationShips
     */
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    @JsonBackReference("user-reservations")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookId",nullable = false)
    @JsonBackReference("book-reservations")
    private Book book;

    public Reservation() {
    }

    public Reservation(LocalDateTime reservationDate, ReservationStatus status) {
        this.reservationDate = reservationDate;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_date=" + reservationDate +
                ", status=" + status +
//                ", user=" + user +
//                ", book=" + book +
                '}';
    }

    /**
     * Getters and Setters
     * @return
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getReservation_date() {
        return reservationDate;
    }

    public void setReservation_date(LocalDateTime reservation_date) {
        this.reservationDate = reservation_date;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
