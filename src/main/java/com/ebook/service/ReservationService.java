package com.ebook.service;

import Repository.ReservationRepository;
import com.ebook.domain.Reservation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService extends AbstractCRUDService<Reservation,Long>{

    private final ReservationRepository reservationRepository;

    public ReservationService(JpaRepository<Reservation, Long> repository, ReservationRepository reservationRepository) {
        super(repository);
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Optional<Reservation> existingReservationOptional = reservationRepository.findById(id);

        if (existingReservationOptional.isPresent()) {
            Reservation existingReservation = existingReservationOptional.get();

            // Update fields
            existingReservation.setReservation_date(updatedReservation.getReservation_date());
            existingReservation.setStatus(updatedReservation.getStatus());

            // Update relationships
            if (updatedReservation.getUser() != null) {
                existingReservation.setUser(updatedReservation.getUser());
            }

            if (updatedReservation.getBook() != null) {
                existingReservation.setBook(updatedReservation.getBook());
            }

            // Save and return updated reservation
            return reservationRepository.save(existingReservation);
        } else {
            throw new RuntimeException("Reservation not found with ID: " + id);
        }
    }
}
