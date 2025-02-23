package com.ebook.service;
import java.util.logging.Logger;
import com.ebook.Repository.BookRepository;
import com.ebook.Repository.ReservationRepository;
import com.ebook.Repository.UserRepository;
import com.ebook.domain.Book;
import com.ebook.domain.Reservation;
import com.ebook.domain.ReservationStatus;
import com.ebook.domain.User;
import com.ebook.dto.ReservationDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class ReservationService extends AbstractCRUDService<Reservation,Long>{

    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());
    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository) {
        super(reservationRepository);
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }
    // Partial Update (PATCH)
    public Reservation patchUpdate(Long id, ReservationDTO updatedReservationDTO) {
        logger.info("Running ReservationService.patchUpdate()");

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        // Update only provided fields
        if (updatedReservationDTO.getReservationDate() != null) reservation.setReservation_date(updatedReservationDTO.getReservationDate());
        if (updatedReservationDTO.getStatus() != null) reservation.setStatus(updatedReservationDTO.getStatus());

        // Handle User relationship
        if (updatedReservationDTO.getUserId() != null) {
            User user = userRepository.findById(updatedReservationDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + updatedReservationDTO.getUserId()));
            reservation.setUser(user);
        }

        // Handle Book relationship
        if (updatedReservationDTO.getBookId() != null) {
            Book book = bookRepository.findById(updatedReservationDTO.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + updatedReservationDTO.getBookId()));
            reservation.setBook(book);
        }

        return reservationRepository.save(reservation);
    }

    // Convert Reservation entity to ReservationDTO
    public ReservationDTO convertToDTO(Reservation reservation) {
        logger.info("Converting Reservation entity to ReservationDTO");

        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setReservationDate(reservation.getReservation_date());
        dto.setStatus(reservation.getStatus().toString());

        // Only store User ID and Book ID
        if (reservation.getUser() != null) {
            dto.setUserId(reservation.getUser().getId());
        }
        if (reservation.getBook() != null) {
            dto.setBookId(reservation.getBook().getId());
        }

        return dto;
    }

    // Convert ReservationDTO to Reservation entity
    public Reservation convertToEntity(ReservationDTO reservationDTO) {
        logger.info("Converting ReservationDTO to Reservation entity");

        Reservation reservation = new Reservation();
        reservation.setReservation_date(reservationDTO.getReservationDate());
        reservation.setStatus(ReservationStatus.valueOf(reservationDTO.getStatus()));

        // Set User if ID is provided
        if (reservationDTO.getUserId() != null) {
            User user = userRepository.findById(reservationDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + reservationDTO.getUserId()));
            reservation.setUser(user);
        }

        // Set Book if ID is provided
        if (reservationDTO.getBookId() != null) {
            Book book = bookRepository.findById(reservationDTO.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + reservationDTO.getBookId()));
            reservation.setBook(book);
        }

        return reservation;
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
