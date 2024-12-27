package com.ebook.domain;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserJPATest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private static final Logger logger = LoggerFactory.getLogger(UserJPATest.class);
    private static final Marker USER_TEST_MARKER = MarkerFactory.getMarker("USER_TEST");
    @BeforeAll
    public static void beforeAll() {
        logger.info(USER_TEST_MARKER,"Initializing EntityManagerFactory...");
        emf = Persistence.createEntityManagerFactory("ebooktestPU");
    }

    @BeforeEach
    public void beforeEach() {
        logger.info("Setting up EntityManager and creating a sample User...");
        em = emf.createEntityManager();
        tx = em.getTransaction();

        // Creating a sample User
        User user1 = new User("John Doe", "john.doe@email.com", "password123", "123456789", "123 Main St", UserRole.USER);

        tx.begin();
        em.persist(user1);
        tx.commit();
        logger.info("Sample User persisted successfully.");
    }

    @Test
    public void createTest() {
        logger.info(USER_TEST_MARKER,"Running createTest...");
        User user2 = new User("Jane Doe", "jane.doe@email.com", "password456", "987654321", "456 Oak St", UserRole.LIBRARIAN);

        tx.begin();
        em.persist(user2);
        tx.commit();
        logger.info("User successfully persisted in createTest.");
        User readBackFromDatabaseForAssertion = em.find(User.class, user2.getId());
        assertNotNull(readBackFromDatabaseForAssertion);
        assertEquals(user2.getId(), readBackFromDatabaseForAssertion.getId());
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");
        // Retrieve the user created in the beforeEach method
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = 'john.doe@email.com'", User.class)
                .getSingleResult();

        assertNotNull(user);
        assertEquals("John Doe", user.getName());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest(){

        logger.info("Running updateTest...");
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = 'john.doe@email.com'", User.class)
                .getSingleResult();
        String newAddress = "420 kansas st";
        tx.begin();
        user.setAddress(newAddress);
        tx.commit();
        logger.info("User address updated successfully in updateTest.");
        User updatedUser = em.find(User.class,user.getId());
        assertEquals(newAddress,updatedUser.getAddress());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = 'john.doe@email.com'", User.class)
                    .getSingleResult();

            tx.begin();
            em.remove(user);
            tx.commit();
            logger.info("User successfully deleted in deleteTest.");
            // Verify deletion
            User deletedUser = em.find(User.class, user.getId());
            assertNull(deletedUser, "User was not successfully deleted");
        } catch (NoResultException e) {
            logger.warn("No user found with the specified email in deleteTest.");
        }
    }

    @AfterEach
    public void afterEach() {
        logger.info("Cleaning up after each test...");
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = 'john.doe@email.com'", User.class)
                    .getSingleResult();

            tx.begin();
            em.remove(user);
            tx.commit();
            logger.info("Sample User removed successfully in afterEach.");
        } catch (NoResultException e) {
            logger.warn("No user found with the specified email in afterEach.");
        } finally {
            em.close();
            logger.info("EntityManager closed in afterEach.");
        }
    }

    @AfterAll
    public static void afterAll(){
        logger.info("Closing EntityManagerFactory...");
        emf.close();
        logger.info("EntityManagerFactory closed successfully.");
    }
}
