package com.ebook.service;

import com.ebook.Repository.FineRepository;
import com.ebook.domain.Fine;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FineService extends AbstractCRUDService<Fine,Long>{

    private final FineRepository fineRepository;

    public FineService( FineRepository fineRepository) {
        super(fineRepository);
        this.fineRepository = fineRepository;
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

