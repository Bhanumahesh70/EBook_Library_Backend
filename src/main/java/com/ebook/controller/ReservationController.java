package com.ebook.controller;

import com.ebook.domain.Reservation;
import com.ebook.dto.ReservationDTO;
import com.ebook.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebook/reservations")
public class ReservationController extends AbstractController<Reservation, ReservationDTO,Long> {

    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        super(reservationService, Reservation.class);
        this.reservationService = reservationService;
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Reservation> createEntity(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.convertToEntity(reservationDTO);
        Reservation createdReservation = reservationService.create(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }
    @Override
    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReservationDTO> patchUpdateBook(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        Reservation updatedReservation = reservationService.patchUpdate(id, reservationDTO);
        ReservationDTO updatedDTO = reservationService.convertToDTO(updatedReservation);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }
}
