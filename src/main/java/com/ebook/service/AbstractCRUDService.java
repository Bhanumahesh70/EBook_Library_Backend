package com.ebook.service;

import com.ebook.domain.Book;
import com.ebook.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class AbstractCRUDService<Entity,EntityDTO, ID> {

    private final JpaRepository<Entity, ID> repository;

    public AbstractCRUDService(JpaRepository<Entity, ID> repository) {
        this.repository = repository;
    }

    public Entity create(Entity entity) {
        return repository.save(entity);
    }

    public List<Entity> findAll() {
        return repository.findAll();
    }

    public Entity findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
    }

    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entity not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public Entity update(ID id, Entity updatedEntity) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entity not found with id: " + id);
        }
        return repository.save(updatedEntity);

    }

    public abstract Entity patchUpdate(ID id, EntityDTO updatedEntityDTO);

    public abstract EntityDTO convertToDTO(Entity entity);

    public abstract Entity convertToEntity(EntityDTO entityDTO);
}
