package com.ebook.domain.BeanValidationTest;
import com.ebook.domain.Reservation;
import com.ebook.domain.ReservationStatus;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.time.LocalDateTime;
public class ReservationValidationTest extends AbstractBeanValidationTest{
    @Test
    public void validateReservationDateNotNullAndPastOrPresent() {
        // Invalid Reservation (Reservation date is null)
        Reservation reservation = new Reservation(null, ReservationStatus.ACTIVE);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null reservation date.");

        for (ConstraintViolation<Reservation> violation : violations) {
            if ("reservationDate".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Reservation date is mandatory", violation.getMessage());
            }
        }

        // Invalid Reservation (Reservation date in the future)
        reservation.setReservation_date(LocalDateTime.now().plusDays(1));

        violations = validator.validate(reservation);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to future reservation date.");

        for (ConstraintViolation<Reservation> violation : violations) {
            if ("reservationDate".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Reservation date must be in the past or present", violation.getMessage());
            }
        }
    }

    @Test
    public void validateReservationStatusNotNull() {
        // Invalid Reservation (Status is null)
        Reservation reservation = new Reservation(LocalDateTime.now(), null);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null reservation status.");

        for (ConstraintViolation<Reservation> violation : violations) {
            if ("status".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Reservation status is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validateValidReservation() {
        // Valid Reservation
        Reservation reservation = new Reservation(LocalDateTime.now(), ReservationStatus.CANCELLED);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);

        if (!violations.isEmpty()) {
            violations.forEach(violation -> System.out.println(
                    "Violation: Property - " + violation.getPropertyPath() +
                            ", Message - " + violation.getMessage()));
        }

        Assertions.assertTrue(violations.isEmpty(), "Validation should pass for a valid Reservation object.");
    }
}
