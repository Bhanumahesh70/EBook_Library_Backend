package com.ebook.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractService<T> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> entityClass ;

    public AbstractService(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    public void create(T entity){
        em.persist(entity);
    }

   public void read(long id){
        em.find(entityClass,id);

   }
   public void update(T entity){
        em.merge(entity);
   }

   @Transactional
   public void delete(T entity){
        em.remove(em.merge(entity));
   }

   public List<T> findAll(String namedQuery){
       return em.createNamedQuery(namedQuery,entityClass).getResultList();
   }
}

