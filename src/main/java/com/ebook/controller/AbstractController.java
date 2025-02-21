package com.ebook.controller;

import com.ebook.service.AbstractCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@CrossOrigin
public abstract class AbstractController<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    private final AbstractCRUDService<T, ID> abstractService;
    private final Class<T> entityClass; // Class type for T

    // Constructor to accept the service and the entity class
    public AbstractController(AbstractCRUDService<T, ID> abstractService, Class<T> entityClass) {
        this.abstractService = abstractService;
        this.entityClass = entityClass;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<T>> displayAllEntities() {
        logger.info("Fetching all {}", getEntityName());
        return new ResponseEntity<>(abstractService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> displayEntity(@PathVariable("id") ID id) {
        logger.info("Fetching {} with id: {}", getEntityName(), id);
        return new ResponseEntity<>(abstractService.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> createEntity(@RequestBody T entity) {
        logger.info("Creating a new {}", getEntityName());
        return new ResponseEntity<>(abstractService.create(entity), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> updateEntity(@PathVariable ID id, @RequestBody Map<String, Object> updatedData) {
        logger.info("Updating {} with id: {}", getEntityName(), id);
        T existingEntity = abstractService.findById(id);
        if(existingEntity==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedData.forEach((field,value)->{

                try {
                    Field declaredField  = entityClass.getDeclaredField(field);
                    declaredField.setAccessible(true);
                    declaredField.set(existingEntity,value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    logger.warn("Skipping field {}: {}", field, e.getMessage());
                    throw new RuntimeException(e);
                }

        });
        abstractService.update(id,existingEntity);
        return new ResponseEntity<>(abstractService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable ID id) {
        logger.info("Deleting {} with id: {}", getEntityName(), id);
        abstractService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Helper method to get the entity name
    private String getEntityName() {
        return entityClass.getSimpleName(); // Use the class passed in the constructor
    }
}