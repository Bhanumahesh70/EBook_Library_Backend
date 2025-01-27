package com.ebook.service;

import Repository.FineRepository;
import com.ebook.domain.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FineService extends AbstractCRUDService<Fine,Long>{

    private final FineRepository fineRepository;

    public FineService(JpaRepository<Fine, Long> repository, FineRepository fineRepository) {
        super(repository);
        this.fineRepository = fineRepository;
    }
}
