# 🔍 Detailed Code Overview

This document provides a deeper dive into the **backend logic, domain design, and entity relationships**.

---

## Domain Model

### Base Class: `AbstractClass`
- Shared by all entities
- Fields: `id`, `createdAt`, `modifiedAt`
- Uses `@MappedSuperclass` and JPA lifecycle hooks

###  Book Entity

- Fields: title, isbn, language, totalCopies, availableCopies, publicationYear
- Field validation with:
  - `@NotBlank`, `@Min`, `@Max`, `@Pattern`
- Entity relationships:
  - `@OneToMany`: BorrowedBooks, Reservations
  - `@ManyToMany`: Authors, Categories
  - `@ManyToOne`: Publisher
- Bidirectional management with helper methods: `addAuthor()`, `addCategory()`, etc.

###  Relationships Summary

| Entity        | Relationship           | Type         |
|---------------|------------------------|--------------|
| Book → Author | Many to Many           | Bidirectional |
| Book → Category | Many to Many         | Bidirectional |
| Book → Publisher | Many to One         | Unidirectional |
| Book → BorrowedBook | One to Many     | Bidirectional |
| Book → Reservation | One to Many       | Bidirectional |

---

## Validation

- Applied via `jakarta.validation.constraints`
- Commonly used:
  - `@NotBlank` on strings
  - `@Min` and `@Max` on numbers
  - `@Pattern` for ISBN (13-digit check)

---

##  Security

- JWT-based authentication (`JWTAuthenticationFilter`, `JWTService`)
- Login endpoint issues token
- `SecurityConfig` restricts access to authenticated users

---

##  Tests

### Validation Tests
- `BeanValidationTest` ensures annotations work (e.g., missing title or invalid ISBN fails)

### JPA Tests
- Test persistence logic using Spring Data JPA
- Example: `BookJPATest`, `AuthorJPATest`

---

## DTOs

Used to decouple persistence layer from controller responses:
- `BookDTO`, `UserDTO`, `ReservationDTO`, etc.
- Mapping handled in services

---

##  Repositories

Spring Data interfaces:
- `BookRepository`, `AuthorRepository`, etc.
- Custom queries defined where needed

---

##  Service Layer

Business logic (e.g., book borrowing, user management) is encapsulated in:
- `BookService`, `UserService`, etc.
- Promotes separation of concerns and testability

---

##  Notes

- Cascade types are set carefully to manage orphan entities.
- `@JsonManagedReference` and `@JsonBackReference` handle serialization loops.
- Logging via `SLF4J` is included in key methods for traceability.
