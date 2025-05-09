package com.ebook.controller;

import com.ebook.service.AbstractCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractController<Entity, EntityDTO, IdType> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    private final AbstractCRUDService<Entity, EntityDTO, IdType> abstractService;

    private final Class<Entity> entityClass; // Class type for T

    // Constructor to accept the service and the entity class
    public AbstractController(AbstractCRUDService<Entity, EntityDTO, IdType> abstractService, Class<Entity> entityClass) {
        this.abstractService = abstractService;
        this.entityClass = entityClass;
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityDTO>> displayAllEntities() {
        logger.info("Displaying all {}", getEntityName());
        List<Entity> entities = abstractService.findAll();
        List<EntityDTO> entityDTOs = entities.stream().map(abstractService::convertToDTO).toList();
        return new ResponseEntity<>(entityDTOs, HttpStatus.OK);
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityDTO> displayEntity(@PathVariable("id") IdType id) {
        logger.info("Displaying {} with id: {}", getEntityName(), id);
        Entity entity = abstractService.findById(id);
        EntityDTO entityDTO = abstractService.convertToDTO(entity);
        return new ResponseEntity<>(entityDTO, HttpStatus.OK);
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<EntityDTO> createEntity(@RequestBody EntityDTO entityDTO) {
//        logger.info("Creating a new {}", getEntityName());
//        Entity entity = abstractService.convertToEntity(entityDTO);
//        Entity createdEntity = abstractService.create(entity);
//        return new ResponseEntity<>(abstractService.convertToDTO(createdEntity), HttpStatus.CREATED);
//    }

@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Entity> createEntity(@RequestBody EntityDTO entityDTO) {
    logger.info("Creating a new {}", getEntityName());
    Entity entity = abstractService.convertToEntity(entityDTO);
    Entity createdEntity = abstractService.create(entity);
    return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
}

   // @PreAuthorize("hasRole('ROLE_ADMIN')" )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entity> updateEntity(@PathVariable IdType id, @RequestBody Entity entity) {
        logger.info("Updating {} with id: {}",getEntityName(),id);
        return new ResponseEntity<>(abstractService.update(id,entity), HttpStatus.CREATED);
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')" )
    @PatchMapping(value="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityDTO> patchUpdateBook(@PathVariable IdType id, @RequestBody EntityDTO entityDTO) {
        logger.info("Patch Updating {} with id: {}",getEntityName(),id);
        Entity updatedEntity =  abstractService.patchUpdate(id, entityDTO);
        return new ResponseEntity<>(abstractService.convertToDTO(updatedEntity), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')" )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable IdType id) {
        logger.info("Deleting {} with id: {}", getEntityName(), id);
        abstractService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Helper method to get the entity name
    private String getEntityName() {
        return entityClass.getSimpleName(); // Use the class passed in the constructor
    }
}