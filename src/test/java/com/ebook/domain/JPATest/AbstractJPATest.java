package com.ebook.domain.JPATest;
import com.ebook.domain.User;
import com.ebook.domain.UserRole;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
public class AbstractJPATest {
    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction tx;
    protected static final Logger logger = LoggerFactory.getLogger(UserJPATest.class);

    @BeforeAll
    public static void initEntityManagerFactory(){
        emf = Persistence.createEntityManagerFactory("ebooktestPU");
    }

    @BeforeEach
    public void initEntityManager(){
        em = emf.createEntityManager();
        tx = em.getTransaction();
        logger.info("EntityManager initialized.");
    }

    @AfterEach
    public void closeEntityManager(){
        if (em != null) {
            em.close();
            logger.info("EntityManager closed.");
        }
    }

    protected <T> void persistEntity(T entity){
        tx.begin();
        em.persist(entity);
        tx.commit();
        logger.info("Entity persisted: {}", entity);
    }

    protected <T> T findEntity(Class<T> entityClass, Object primaryKey){
        return em.find(entityClass,primaryKey);
    }

    protected <T> void removeEntity(T entity){
        tx.begin();
        em.remove(entity);
        tx.commit();
        logger.info("Entity removed: {}", entity);
    }
}
