package com.ebook.service;

import com.ebook.Repository.BorrowedBookRepository;
import com.ebook.Repository.FineRepository;
import com.ebook.Repository.ReservationRepository;
import com.ebook.Repository.UserRepository;
import com.ebook.domain.*;
import com.ebook.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService extends AbstractCRUDService<User,UserDTO,Long>{

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;

    private final BorrowedBookRepository borrowedBookRepository;
    private final ReservationRepository reservationRepository;
    private final FineRepository fineRepository;

    @Autowired
    public UserService(UserRepository userRepository, BorrowedBookRepository borrowedBookRepository,
                       ReservationRepository reservationRepository, FineRepository fineRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.borrowedBookRepository = borrowedBookRepository;
        this.reservationRepository = reservationRepository;
        this.fineRepository = fineRepository;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    //Check if the user enters correct email and password for login authorization
    public boolean authenticateUser(String email, String rawPassword){
      User  user = findByEmail(email);
      if(user ==null) {
          return false;
      }
      //check if password matches with databases
     return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // Partial Update (PATCH)
    @Override
    public User patchUpdate(Long id, UserDTO updatedUserDTO) {
        logger.info("Running UserService.patchUpdate()");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update only provided fields
        if (updatedUserDTO.getName() != null) user.setName(updatedUserDTO.getName());
        if (updatedUserDTO.getEmail() != null) user.setEmail(updatedUserDTO.getEmail());
        if (updatedUserDTO.getPassword() != null) user.setPassword(updatedUserDTO.getPassword());
        if (updatedUserDTO.getPhoneNumber() != null) user.setPhoneNumber(updatedUserDTO.getPhoneNumber());
        if (updatedUserDTO.getAddress() != null) user.setAddress(updatedUserDTO.getAddress());
        if (updatedUserDTO.getRole() != null) user.setRole(UserRole.valueOf(updatedUserDTO.getRole()));

        // Handle Borrowed Books, Reservations, and Fines by IDs
        if (updatedUserDTO.getBorrowedBookIds() != null) {
            List<BorrowedBook> borrowedBooks = borrowedBookRepository.findAllById(updatedUserDTO.getBorrowedBookIds());
            user.setBorrowedBooks(borrowedBooks);
        }
        if (updatedUserDTO.getReservationIds() != null) {
            List<Reservation> reservations = reservationRepository.findAllById(updatedUserDTO.getReservationIds());
            user.setReservations(reservations);
        }
        if (updatedUserDTO.getFineIds() != null) {
            List<Fine> fines = fineRepository.findAllById(updatedUserDTO.getFineIds());
            user.setFines(fines);
        }

        return userRepository.save(user);
    }

    // Convert User entity to UserDTO
    @Override
    public UserDTO convertToDTO(User user) {
        logger.info("Converting User entity to UserDTO");

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole().toString());

        // Add IDs for related entities (BorrowedBooks, Reservations, Fines)
        if (user.getBorrowedBooks() != null) {
            List<Long> borrowedBookIds = user.getBorrowedBooks().stream()
                    .map(BorrowedBook::getId)
                    .collect(Collectors.toList());
            dto.setBorrowedBookIds(borrowedBookIds);
        }

        if (user.getReservations() != null) {
            List<Long> reservationIds = user.getReservations().stream()
                    .map(Reservation::getId)
                    .collect(Collectors.toList());
            dto.setReservationIds(reservationIds);
        }

        if (user.getFines() != null) {
            List<Long> fineIds = user.getFines().stream()
                    .map(Fine::getId)
                    .collect(Collectors.toList());
            dto.setFineIds(fineIds);
        }

        return dto;
    }

    // Convert UserDTO to User entity
    @Override
    public User convertToEntity(UserDTO userDTO) {
        logger.info("Converting UserDTO to User entity");

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        //Encode the password instead of storing plain raw password in the database
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setRole(UserRole.valueOf(userDTO.getRole().toUpperCase()));

        // Set BorrowedBooks, Reservations, and Fines by IDs
        if (userDTO.getBorrowedBookIds() != null) {
            List<BorrowedBook> borrowedBooks = borrowedBookRepository.findAllById(userDTO.getBorrowedBookIds());
            user.setBorrowedBooks(borrowedBooks);
        }

        if (userDTO.getReservationIds() != null) {
            List<Reservation> reservations = reservationRepository.findAllById(userDTO.getReservationIds());
            user.setReservations(reservations);
        }

        if (userDTO.getFineIds() != null) {
            List<Fine> fines = fineRepository.findAllById(userDTO.getFineIds());
            user.setFines(fines);
        }

        return user;
    }
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update fields
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setRole(updatedUser.getRole());

            // Update relationships (BorrowedBooks, Reservations, Fines)
            if (updatedUser.getBorrowedBooks() != null) {
                existingUser.setBorrowedBooks(updatedUser.getBorrowedBooks());
            }
            if (updatedUser.getReservations() != null) {
                existingUser.setReservations(updatedUser.getReservations());
            }
            if (updatedUser.getFines() != null) {
                existingUser.setFines(updatedUser.getFines());
            }

            // Save and return updated user
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
}
