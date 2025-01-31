package com.ebook.service;

import com.ebook.Repository.UserRepository;
import com.ebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService extends AbstractCRUDService<User,Long>{

    private final UserRepository userRepository;

    public UserService(JpaRepository<User, Long> repository, UserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
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
