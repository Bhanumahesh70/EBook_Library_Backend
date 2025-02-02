package com.ebook.controller;

import com.ebook.domain.Reservation;
import com.ebook.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController extends AbstractController<Reservation, Long> {

    @Autowired
    public ReservationController(ReservationService reservationService) {
        super(reservationService, Reservation.class);
    }
}
