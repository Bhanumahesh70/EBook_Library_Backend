package com.ebook.service;
import java.util.logging.Logger;

import com.ebook.Repository.BorrowedBookRepository;
import com.ebook.Repository.FineRepository;
import com.ebook.Repository.UserRepository;
import com.ebook.domain.BorrowedBook;
import com.ebook.domain.Fine;
import com.ebook.domain.FinePaidStatus;
import com.ebook.domain.User;
import com.ebook.dto.FineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FineService extends AbstractCRUDService<Fine,Long>{

    private static final Logger logger = Logger.getLogger(FineService.class.getName());

    private final FineRepository fineRepository;
    private final BorrowedBookRepository borrowedBookRepository;
    private final UserRepository userRepository;

    @Autowired
    public FineService(FineRepository fineRepository, BorrowedBookRepository borrowedBookRepository, UserRepository userRepository) {
        super(fineRepository);
        this.fineRepository = fineRepository;
        this.borrowedBookRepository = borrowedBookRepository;
        this.userRepository = userRepository;
    }

    // Partial Update (Patch)
    public Fine patchUpdate(Long id, FineDTO updatedFineDTO) {
        logger.info("Running FineService.patchUpdate()");

        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fine not found with id: " + id));

        // Update only provided fields
        if (updatedFineDTO.getAmount() > 0) fine.setAmount(updatedFineDTO.getAmount());
        if (updatedFineDTO.getStatus() != null) fine.setStatus(FinePaidStatus.valueOf(updatedFineDTO.getStatus()));
        if (updatedFineDTO.getPaidDate() != null) fine.setPaidDate(updatedFineDTO.getPaidDate());

        // Handle relationships
        if (updatedFineDTO.getBorrowedBookId() != null) {
            BorrowedBook borrowedBook = borrowedBookRepository.findById(updatedFineDTO.getBorrowedBookId())
                    .orElseThrow(() -> new RuntimeException("BorrowedBook not found with id: " + updatedFineDTO.getBorrowedBookId()));
            fine.setBorrowedBook(borrowedBook);
        }

        if (updatedFineDTO.getUserId() != null) {
            User user = userRepository.findById(updatedFineDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + updatedFineDTO.getUserId()));
            fine.setUser(user);
        }

        return fineRepository.save(fine);
    }

    // Convert Fine entity to FineDTO
    public FineDTO convertToDTO(Fine fine) {
        logger.info("Converting Fine entity to FineDTO");

        FineDTO dto = new FineDTO();
        dto.setId(fine.getId());
        dto.setAmount(fine.getAmount());
        dto.setStatus(fine.getStatus().toString());
        dto.setPaidDate(fine.getPaidDate());
        dto.setBorrowedBookId(fine.getBorrowedBook() != null ? fine.getBorrowedBook().getId() : null);
        dto.setUserId(fine.getUser() != null ? fine.getUser().getId() : null);

        return dto;
    }

    // Convert FineDTO to Fine entity
    public Fine convertToEntity(FineDTO fineDTO) {
        logger.info("Converting FineDTO to Fine entity");

        Fine fine = new Fine();
        fine.setAmount(fineDTO.getAmount());
        fine.setStatus(FinePaidStatus.valueOf(fineDTO.getStatus()));
        fine.setPaidDate(fineDTO.getPaidDate());

        // Set relationships if IDs are provided
        if (fineDTO.getBorrowedBookId() != null) {
            BorrowedBook borrowedBook = borrowedBookRepository.findById(fineDTO.getBorrowedBookId())
                    .orElseThrow(() -> new RuntimeException("BorrowedBook not found with id: " + fineDTO.getBorrowedBookId()));
            fine.setBorrowedBook(borrowedBook);
        }

        if (fineDTO.getUserId() != null) {
            User user = userRepository.findById(fineDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + fineDTO.getUserId()));
            fine.setUser(user);
        }

        return fine;
    }

        @Override
        public void update(Long id, Fine updatedFine) {
            Optional<Fine> existingFineOpt = fineRepository.findById(id);

            if (existingFineOpt.isPresent()) {
                Fine existingFine = existingFineOpt.get();

                // Update fields
                existingFine.setAmount(updatedFine.getAmount());
                existingFine.setStatus(updatedFine.getStatus());
                existingFine.setPaidDate(updatedFine.getPaidDate());
                existingFine.setBorrowedBook(updatedFine.getBorrowedBook());
                existingFine.setUser(updatedFine.getUser());

                // Save updated entity
                fineRepository.save(existingFine);
            } else {
                throw new IllegalArgumentException("Fine with ID " + id + " not found");
            }
        }
    }

