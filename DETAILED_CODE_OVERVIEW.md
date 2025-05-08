# 🔍 Detailed Code Overview

This document provides a deeper dive into the **backend logic, domain design, and entity relationships**.

---

## Domain Model

### Base Class: `AbstractClass`
- Shared by all entities
- Fields: `id`, `createdAt`, `modifiedAt`
- Uses `@MappedSuperclass` and JPA lifecycle hooks

### 📘 Book Entity

- Fields: title, isbn, language, totalCopies, availableCopies, publicationYear
- Field validation with:
  - `@NotBlank`, `@Min`, `@Max`, `@Pattern`
- Entity relationships:
  - `@OneToMany`: BorrowedBooks, Reservations
  - `@ManyToMany`: Authors, Categories
  - `@ManyToOne`: Publisher
- Bidirectional management with helper methods: `addAuthor()`, `addCategory()`, etc.

###  Relationships Summary

| Entity              | Relationship | Type           |
|---------------------|--------------|----------------|
| Book → Author       | Many to Many | Bidirectional  |
| Book → Category     | Many to Many | Bidirectional  |
| Book → Publisher    | Many to One  | Unidirectional |
| Book → BorrowedBook | One to Many  | Bidirectional  |
| Book → Reservation  | One to Many  | Bidirectional  |
---

### 👨‍💼 Author Entity

- **Fields**: `name`, `bio`, `nationality`, `birthDate`
- **Field validation**:
  - `@NotBlank`: name
  - `@Size`: bio (max 1000), nationality (max 50), name (max 100)
  - `@Past`: birthDate (must be in the past)
- **Entity relationships**:
  - `@ManyToMany`: Books (bidirectional)

- **Bidirectional management**:
  - Uses helper methods `addBook()` and `removeBook()` to manage relationship with `Book` entity.
  - Maintains consistency by ensuring both `Author.books` and `Book.authors` are synchronized.

### 🔗 Relationships Summary

| Entity        | Relationship | Type          |
|---------------|--------------|---------------|
| Author → Book | Many to Many | Bidirectional |
---
### 📘 BorrowedBook Entity

- **Fields**: `borrowDate`, `expectedReturnDate`, `returnedOn`, `status`, `bookBorrowCost`
- **Field validation**:
  - `@NotNull`: all fields except `returnedOn`
  - `@FutureOrPresent`: `expectedReturnDate`
- **Entity relationships**:
  - `@ManyToOne`: `User`, `Book`
  - `@OneToOne`: `Fine`, `Reservation`

- **Serialization Management**:
  - Uses `@JsonBackReference` and `@JsonManagedReference` to handle serialization and prevent infinite recursion.

- **Usage Notes**:
  - Represents the borrowing activity for a book by a user.
  - Tracks return status and associated fines/reservations.

### 🔗 Relationships Summary

| Entity                     | Relationship | Type           |
|----------------------------|--------------|----------------|
| BorrowedBook → User        | Many to One  | Unidirectional |
| BorrowedBook → Book        | Many to One  | Unidirectional |
| BorrowedBook → Fine        | One to One   | Unidirectional |
| BorrowedBook → Reservation | One to One   | Unidirectional |
---

### 🗂 Category Entity

- **Fields**: `categoryName`, `description`
- **Field validation**:
  - `@NotBlank`: `categoryName` (required and unique)
  - `@Size`: `categoryName` (max 255), `description` (max 2000)
- **Entity relationships**:
  - `@ManyToMany`: Books (bidirectional)

- **Bidirectional management**:
  - Managed using helper methods `addBook()` and `removeBook()`
  - Maintains consistency between `Category.books` and `Book.categories`

- **Serialization Notes**:
  - Uses `@JsonIgnore` to avoid circular references during JSON serialization

### 🔗 Relationships Summary

| Entity          | Relationship | Type          |
|-----------------|--------------|---------------|
| Category → Book | Many to Many | Bidirectional |
---

### 💸 Fine Entity

- **Fields**: `amount`, `status`, `paidDate`
- **Field validation**:
  - `@NotNull`: `amount`, `status`
  - `@Positive`: `amount`
  - `@PastOrPresent`: `paidDate` (ensures payment isn't in the future)

- **Entity relationships**:
  - `@OneToOne`: `BorrowedBook` (bidirectional)
  - `@ManyToOne`: `User` (unidirectional)

- **Serialization Notes**:
  - Uses `@JsonBackReference` to prevent circular references when serializing related entities

- **Usage Notes**:
  - Tracks the fine incurred from a borrowed book
  - Indicates whether the fine is paid and when


### 🔗 Relationships Summary

| Entity              | Relationship | Type           |
|---------------------|--------------|----------------|
| Fine → BorrowedBook | One to One   | Bidirectional  |
| Fine → User         | Many to One  | Unidirectional |

---

### 🏢 Publisher Entity

- **Fields**: `name`, `address`, `email`, `phoneNumber`
- **Field validation**:
  - `@NotBlank`: `name`, `email`
  - `@Size`: `name` (max 100), `address` (max 255), `email` (max 100)
  - `@Email`: `email` (must be a valid format)
  - `@Pattern`: `phoneNumber` (10-15 digits, optionally starts with `+`)

- **Entity relationships**:
  - `@OneToMany`: `Book` (unidirectional from `Publisher` to `Book`)
  - Managed using `@JsonManagedReference` to control JSON serialization and avoid infinite recursion

- **Bidirectional Management**:
  - Methods `addBook()` and `removeBook()` ensure consistent link between books and their publisher
  - Also managed within `setBooks()` to reassign all books to a new publisher

### 🔗 Relationships Summary

| Entity           | Relationship | Type           |
|------------------|--------------|----------------|
| Publisher → Book | One to Many  | Unidirectional |

---

### 📅 Reservation Entity

- **Fields**: `reservationDate`, `numberOfDays`, `status`
- **Field validation**:
  - `@NotNull`: All fields required
  - *(Commented)* `@PastOrPresent`: `reservationDate` can be restricted to not be in the future if enforced

- **Entity relationships**:
  - `@ManyToOne`: `User`, `Book` (bidirectional from `Reservation`)
  - Uses `@JsonBackReference` to avoid circular references in JSON serialization

- **Usage Notes**:
  - Represents a reservation of a book by a user
  - Tracks reservation period and status (`PENDING`, `APPROVED`, etc.)


### 🔗 Relationships Summary

| Entity             | Relationship | Type           |
|--------------------|--------------|----------------|
| Reservation → User | Many to One  | Unidirectional |
| Reservation → Book | Many to One  | Unidirectional |

---

### 👤 User Entity

- **Fields**: `name`, `email`, `password`, `phoneNumber`, `address`, `role`
- **Field validation**:
  - `@NotBlank`: `name`
  - `@Email`: `email`
  - `@Size(min=8)`: `password` (minimum 8 characters)
  - `@NotNull`: `address`, `role`

- **Security Integration**:
  - Implements `UserDetails` from Spring Security
  - Passwords are encoded using `BCryptPasswordEncoder`
  - `@JsonIgnore` hides password from API responses

- **Entity relationships**:
  - `@OneToMany`: `BorrowedBook`, `Reservation`, `Fine` (bidirectional via mappedBy)
  - Uses `@JsonManagedReference` to prevent serialization loops

- **Bidirectional Management**:
  - Methods: `addBorrowedBook()`, `addReservation()`, `addFine()` and their `remove` counterparts
  - Maintains consistent link between `User` and related entities


### 🔗 Relationships Summary

| Entity              | Relationship | Type          |
|---------------------|--------------|---------------|
| User → BorrowedBook | One to Many  | Bidirectional |
| User → Reservation  | One to Many  | Bidirectional |
| User → Fine         | One to Many  | Bidirectional |

---
### 🧾 Enum Definitions

This section outlines the enums used throughout the domain model for type-safe field values.

---

####  `BorrowStatus`
Represents the status of a borrowed book.

```java
public enum BorrowStatus {
    BORROWED,
    RETURNED,
    LATE
}
```

| Value     | Description                     |
|-----------|---------------------------------|
| BORROWED  | Book is currently borrowed      |
| RETURNED  | Book has been returned          |
| LATE      | Book return is overdue          |

---

####  `FinePaidStatus`
Represents the payment status of a fine.

```java
public enum FinePaidStatus {
    PAID,
    UNPAID
}
```

| Value   | Description           |
|---------|-----------------------|
| PAID    | Fine has been paid    |
| UNPAID  | Fine is still due     |

---

####  `ReservationStatus`
Represents the current status of a reservation.

```java
public enum ReservationStatus {
    REQUESTED,
    APPROVED,
    REJECTED,
    CANCELED,
}
```

| Value       | Description                         |
|-------------|-------------------------------------|
| REQUESTED   | User has requested reservation      |
| APPROVED    | Reservation approved                |
| REJECTED    | Reservation rejected                |
| CANCELED    | Reservation cancelled by user/admin |

---

####  `UserRole`
Defines user access roles in the system.

```java
public enum UserRole {
    ROLE_LIBRARIAN,
    ROLE_USER,
    ROLE_ADMIN
}
```

| Value          | Description              |
|----------------|--------------------------|
| ROLE_LIBRARIAN | Librarian privileges     |
| ROLE_USER      | Regular user access      |
| ROLE_ADMIN     | Admin access and control |

---
###  Validation in Domain Entities

The application uses `jakarta.validation.constraints` (formerly `javax.validation`) to enforce data integrity and prevent invalid data from being persisted.

---

#### 📌 Commonly Used Annotations

| Annotation                 | Applied To         | Purpose                                             |
|----------------------------|--------------------|-----------------------------------------------------|
| `@NotBlank`                | String             | Ensures a string is not null and trimmed length > 0 |
| `@NotNull`                 | All types          | Ensures field is not null                           |
| `@Size`                    | String, Collection | Limits the min/max length of a string or collection |
| `@Min` / `@Max`            | Numeric types      | Sets minimum and maximum acceptable values          |
| `@Pattern`                 | String             | Enforces regex format (e.g., ISBN, phone number)    |
| `@Email`                   | String             | Validates standard email format                     |
| `@Positive`                | Numeric types      | Ensures value is greater than zero                  |
| `@Past` / `@PastOrPresent` | Date/Time types    | Restricts values to the past (or including now)     |
| `@FutureOrPresent`         | Date/Time types    | Ensures the value is today or a future date         |

---

####  Examples From the Project

- **Author Entity**:
  - `@NotBlank`, `@Size(max=100)`: `name`
  - `@Past`: `birthDate`

- **Book Entity**:
  - `@Pattern(regexp = "\\d{13}")`: `isbn`
  - `@Min(1)`, `@Min(0)`: `totalCopies`, `availableCopies`
  - `@Max(2025)`, `@Min(1000)`: `publicationYear`

- **BorrowedBook Entity**:
  - `@NotNull`: `borrowDate`, `expectedReturnDate`, `status`, `bookBorrowCost`
  - `@FutureOrPresent`: `expectedReturnDate`

- **Category Entity**:
  - `@NotBlank`, `@Size(max=255)`: `categoryName`
  - `@Size(max=2000)`: `description`

- **Fine Entity**:
  - `@NotNull`, `@Positive`: `amount`
  - `@PastOrPresent`: `paidDate`

- **Publisher Entity**:
  - `@NotBlank`, `@Email`, `@Size`: `email`
  - `@Pattern`: `phoneNumber`

- **Reservation Entity**:
  - `@NotNull`: `reservationDate`, `status`, `numberOfDays`

- **User Entity**:
  - `@NotBlank`: `name`
  - `@Email`: `email`
  - `@Size(min=8)`: `password`
  - `@NotNull`: `address`, `role`

  
#### Purpose

Validation annotations:
- Ensure data correctness before hitting the database
- Provide meaningful error messages for clients
- Reduce backend logic for sanity checks

These are enforced via:
- **Hibernate Validator** (JPA)
- **Spring Boot’s automatic validation** with `@Valid` and exception handling



---

##  Security
The system implements robust security using **Spring Security** and **JWT-based authentication**. Here are the key components:

---

####  Key Features

- **JWT Authentication**:
  - Secure token generation using `JWTService` with claims like `subject` (email) and `role`
  - Tokens are signed with HMAC-SHA256 and have a 24-hour expiration window
  - `JWTAuthenticationFilter` validates and parses tokens on every secured request

- **Login Endpoint**:
  - `POST /ebook/authorization/login` accepts credentials and returns a JWT token
  - The token must be included in `Authorization` header as `Bearer <token>`

- **Token Validation**:
  - `POST /ebook/authorization/validate` endpoint checks the validity of a token
  - Parses user role and identity from token claims

- **Security Configuration**:
  - Configured via `SecurityConfig.java`
  - All endpoints require authentication except `/ebook/authorization/login`
  - CORS configured for specific frontend dev origins
  - CSRF disabled (common in stateless APIs)

- **Spring Security Filter Chain**:
  - JWT filter added *before* `UsernamePasswordAuthenticationFilter`
  - Uses `SecurityContextHolder` to bind JWT-authenticated user context

- **Password Security**:
  - Passwords are encrypted using `BCryptPasswordEncoder`
  - Authentication provider uses encoded passwords and `UserDetailsService`

- **Role-based Access Control**:
  - Claims extracted from JWT tokens determine authorities (e.g., `ROLE_ADMIN`)
  - Enforced via `@EnableMethodSecurity` and `GrantedAuthority` configuration

---

####  Components Summary

| Component                 | Purpose                                                  |
|---------------------------|----------------------------------------------------------|
| `SecurityConfig`          | Configures filter chain, CORS, CSRF, endpoint rules      |
| `JWTService`              | Creates and validates signed JWT tokens                  |
| `JWTAuthenticationFilter` | Parses JWT from requests and loads user context          |
| `JWTAuthenticationToken`  | Custom token with user roles and claims                  |
| `LoginController`         | Handles login and token validation                       |
| `BCryptPasswordEncoder`   | Secure password hashing and comparison                   |

---

#### Best Practices Implemented

- Stateless authentication (no server-side sessions)
- Token signature verification using a securely generated secret key
- Custom claims in tokens (`email`, `role`)
- Enforced CORS policies for specific frontends
- Logs at every step to trace auth events



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
