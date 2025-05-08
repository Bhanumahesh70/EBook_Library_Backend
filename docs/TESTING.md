## Testing Strategy

The system follows a structured testing approach to ensure both domain-level validation and data persistence work as expected.

###  Validation Tests

- **Framework**: Uses `jakarta.validation.Validator` with JUnit 5
- **Base Class**: `AbstractBeanValidationTest` provides shared validator setup
- **Scope**:
    - Test annotations like `@NotBlank`, `@Min`, `@Max`, `@Size`, `@Pattern`
    - Verify failure messages match expected constraints
- **Examples**:
    - `AuthorValidationTest`: Ensures fields like `name`, `bio`, `birthDate` are validated
    - `BookValidationTest`: Covers `isbn`, `totalCopies`, `availableCopies`, `publicationYear`
    - `BorrowedBookValidationTest`: Ensures date constraints and required fields are checked
- **Best Practices**:
    - Asserts both failure presence and specific violation messages
    - Also includes tests for **valid objects** to ensure constraints allow correct data

---

### JPA Tests

- **Framework**: Spring Data JPA / Hibernate (with in-memory or configured test DB)
- **Base Class**: `AbstractJPATest` sets up shared `EntityManager` and transactions
- **Purpose**: To verify that entity mappings and repository methods behave correctly with a real or embedded database
- **Test Scenarios**:
    - CRUD operations (`create`, `read`, `update`, `delete`)
    - Relationship mapping checks (Many-to-One, Many-to-Many, etc.)
    - Bidirectional association integrity (e.g., `Author <-> Book`)
    - Cascade behavior during persist/remove
    - Query correctness (Named Queries and JPQL)
- **Examples**:
    - `BookJPATest`
    - `AuthorJPATest`
    - `UserJPATest`
    - `ReservationJPATest`
    - `BorrowedBookJPATest`
    - `FineJPATest`

---

###  Key Testing Goals

| Goal                          | Covered in Tests                        |
|-------------------------------|-----------------------------------------|
| Bean field constraints        | ✅ Validation tests                      |
| Entity relationship integrity | ✅ JPA & logic tests                     |
| Authentication logic          | 🔜 (Future Integration/Unit Tests)      |
| Boundary case handling        | ✅ Constraint violations                 |
| Valid data acceptance         | ✅ Valid input tests                     |
| Entity lifecycle callbacks    | ✅ `@PrePersist`, `@PreUpdate` behavior  |
| NamedQuery behavior           | ✅ Usage tested in JPA tests             |
| Uniqueness/constraints        | ✅ via annotations and persistence layer |

---

###  Tools and Frameworks

- JUnit 5 (`org.junit.jupiter`)
- Hibernate Validator (JSR 380)
- Spring Boot Test / Embedded H2 or configured test DB
- SLF4J Logging
- Secure Random generation for unique test values
