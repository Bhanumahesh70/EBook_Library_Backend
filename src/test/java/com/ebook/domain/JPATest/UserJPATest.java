package com.ebook.domain.JPATest;
import com.ebook.domain.User;
import com.ebook.domain.UserRole;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class UserJPATest extends AbstractJPATest {

    @Test
    public void createTest() {
        logger.info("Running createTest...");
        User user = new User("Jane Doe", "jane.doe@email.com", "password456", "987654320", "456 Oak St", UserRole.LIBRARIAN);
        persistEntity(user);
        logger.info("User successfully persisted in createTest.");
        User readBackFromDatabaseForAssertion = findEntity(User.class,user.getId());
        assertNotNull(readBackFromDatabaseForAssertion);
        assertEquals(user.getId(), readBackFromDatabaseForAssertion.getId());
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");
        User user = new User("Jane Doe 1", "jane.doe1@email.com", "password456", "987654321", "456 Oak St", UserRole.LIBRARIAN);
        persistEntity(user);
        // Retrieve the user created in the beforeEach method
        User findUser = em.createQuery("SELECT u FROM User u WHERE u.email = 'jane.doe1@email.com'", User.class)
                .getSingleResult();

        assertNotNull(findUser);
        assertEquals("Jane Doe 1", findUser.getName());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest(){

        logger.info("Running updateTest...");
        User user = new User("Jane Doe 2", "jane.doe2@email.com", "password456", "987654324", "456 Oak St", UserRole.LIBRARIAN);
        persistEntity(user);
        String newAddress = "420 kansas st";
        tx.begin();
        user.setAddress(newAddress);
        tx.commit();
        logger.info("User address updated successfully in updateTest.");
        User updatedUser = findEntity(User.class,user.getId());
        assertEquals(newAddress,updatedUser.getAddress());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");
        User user = new User("Jane Doe3", "jane.doe3@email.com", "password456", "987654325", "456 Oak St", UserRole.LIBRARIAN);
        persistEntity(user);
        User findUser = findEntity(User.class, user.getId());
        assertNotNull(findUser);

        removeEntity(findUser);
        User deletedUser = findEntity(User.class, user.getId());
        assertNull(deletedUser);


    }
}
