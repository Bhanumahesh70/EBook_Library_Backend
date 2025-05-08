##  Controller Layer

The controller layer acts as the entry point for HTTP requests. It coordinates with the service layer to handle requests, process responses, enforce access control, and return JSON data via REST endpoints.

---

###  Architecture & Responsibilities

| Controller               | Responsibilities                                                           |
|--------------------------|----------------------------------------------------------------------------|
| `AbstractController`     | Generic controller with CRUD & PATCH endpoints + role-based access control |
| `BookController`         | Manages all book-related endpoints                                         |
| `AuthorController`       | Manages authors using abstract controller model                            |
| `UserController`         | Exposes user-specific operations (reservations, borrowed books)            |
| `ReservationController`  | Handles reservation lifecycle, creation, and patch update                  |
| `BorrowedBookController` | Allows return of borrowed books and book patching                          |
| `FineController`         | Endpoint to pay fines                                                      |
| `LoginController`        | JWT-based login and token validation                                       |
| `PublisherController`    | Manages publishers and fetches their books                                 |
| `CategoryController`     | Exposes category info and books under each category                        |

---

###  Role-Based Access Control

* Uses `@PreAuthorize` annotations to guard endpoints
* Sample checks:

    * `hasRole('ROLE_ADMIN')`
    * `hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')`

This ensures **secure endpoint-level access control** aligned with Spring Security roles.

---

###  Generic Controller Design

* **`AbstractController`**:

    * Implements generic REST endpoints for CRUD:

        * `GET /` – list all entities
        * `GET /{id}` – fetch single entity
        * `POST /` – create entity
        * `PUT /{id}` – full update
        * `PATCH /{id}` – partial update
        * `DELETE /{id}` – delete by ID
    * Leverages `AbstractCRUDService`
    * Converts entities to DTOs for cleaner external representation

---

### 📦 Custom Controller Logic

Some controllers override or extend the default methods:

* **`UserController`**

    * Custom endpoints to fetch borrowed books and reservations
    * Add borrowed books to users

* **`ReservationController`**

    * Overrides `POST` and `PATCH` for business logic integration (borrow creation)

* **`FineController`**

    * Exposes `/payFine` endpoint

* **`LoginController`**

    * Handles `/login` and `/validate` endpoints for JWT token generation and validation

* **`BorrowedBookController`**

    * Custom `/returnBook` endpoint

---

###  Integration & Standards

* All controllers:

    * Use `@RestController`, `@RequestMapping`, and `@ResponseEntity`
    * Return JSON (`MediaType.APPLICATION_JSON_VALUE`)
    * Log request actions via SLF4J

* Controllers handle only coordination and delegation; **business logic resides in services**.

---

###  Benefits

* Centralized control entry points for each domain
* Clean separation from persistence and logic
* Testable and maintainable structure
* Fine-grained security enforcement

---