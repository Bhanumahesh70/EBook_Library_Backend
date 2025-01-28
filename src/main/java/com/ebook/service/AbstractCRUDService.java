package com.ebook.service;

import com.ebook.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class AbstractCRUDService<T, ID> {

    private final JpaRepository<T, ID> repository;

    public AbstractCRUDService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
    }

    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entity not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public void update(ID id, T updatedEntity) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entity not found with id: " + id);
        }
        repository.save(updatedEntity);

    }


}
