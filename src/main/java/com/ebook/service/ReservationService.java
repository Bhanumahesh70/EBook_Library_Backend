package com.ebook.service;

import Repository.ReservationRepository;
import com.ebook.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends AbstractCRUDService<Reservation,Long>{

    private final ReservationRepository reservationRepository;

    public ReservationService(JpaRepository<Reservation, Long> repository, ReservationRepository reservationRepository) {
        super(repository);
        this.reservationRepository = reservationRepository;
    }
}
