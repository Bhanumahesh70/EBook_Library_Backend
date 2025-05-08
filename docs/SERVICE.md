##  Service Layer

The service layer in this application encapsulates all **business logic**, promoting:

* **Separation of concerns** between controllers and persistence
* **Ease of testing and maintenance**
* **Centralized handling** of DTO conversions and validation
* **Improved security integration** (Spring Security & JWT)

---

### ⛏ Design Principles

* **AbstractCRUDService**:

    * Generic service providing `create`, `findAll`, `findById`, `update`, `patchUpdate`, and `delete` operations
    * Promotes **code reuse**, consistency, and DRY principles
    * Enforces custom DTO-entity conversion with abstract methods `convertToDTO()` and `convertToEntity()`

---

###  Core Services

| Service               | Responsibilities                                                         |
|-----------------------|--------------------------------------------------------------------------|
| `AuthorService`       | Manage authors, support patching, handle `AuthorDTO` conversion          |
| `BookService`         | Book lifecycle operations, author/category/publisher association syncing |
| `UserService`         | User management, login verification, implements `UserDetailsService`     |
| `ReservationService`  | Handle reservation lifecycle, approval triggers borrowed book creation   |
| `BorrowedBookService` | Calculate borrow cost, manage return logic, patch updates                |

---

###  Key Features

* **DTO Conversion**

    * Every service includes custom `convertToDTO()` and `convertToEntity()`
    * Supports deep/nested conversion (e.g., user DTO includes reservations, borrowed books)

* **Patch Updates**

    * `patchUpdate()` selectively updates only non-null fields
    * Minimizes data overwriting and improves API flexibility

* **Business Logic Implementation**:

    * `BorrowedBookService` calculates cost based on prior borrow history
    * `ReservationService` auto-creates a borrow record when reservation is approved

* **Security Integration**:

    * `UserService` enables Spring Security via `UserDetailsService`
    * Authentication logic encapsulated in service and reused by JWT layer

* **Association Management**:

    * `syncAssociations()` in `BookService` dynamically handles relationship updates (add/remove authors, categories)

---

###  Extensibility & Scalability

* Add new services easily by extending `AbstractCRUDService`
* Complex conversions isolated within services, keeping controllers thin
* Aligns with **SOLID principles**:

    * **Single Responsibility**: services own logic
    * **Open/Closed**: services extensible without modifying core

---

###  Tools & Patterns

* `@Service` for Spring Dependency Injection
* `@Transactional` used where consistency is critical (e.g., update cascades)
* SLF4J `Logger` for debug-friendly and traceable logs
* Composition over inheritance (services inject other services via constructor)
* Interfaces and repository abstraction via Spring Data JPA
