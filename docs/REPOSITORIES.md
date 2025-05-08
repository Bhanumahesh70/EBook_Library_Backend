##  Repositories

Spring Data JPA repositories abstract away low-level CRUD and query logic, simplifying data access.

### Features

- Inherit from `JpaRepository<Entity, Long>` for full CRUD support
- Auto-implemented methods like `save()`, `findAll()`, `findById()`, `deleteById()`
- Custom queries with:
    - JPQL via `@Query`
    - Parameters via `@Param`
    - Derived query methods (e.g., `findByEmail()`)

### 🔎 Custom Queries (Examples)

| Repository            | Method                                       | Query Purpose                   |
|-----------------------|----------------------------------------------|---------------------------------|
| `AuthorRepository`    | `findByName(String)`                         | Fetch author by exact name      |
| `BookRepository`      | `findByName(String)`                         | Retrieve book by title          |
| `CategoryRepository`  | `findByName(String)`                         | Match category by name          |
| `PublisherRepository` | `findByName(String)`                         | Lookup publisher using name     |
| `UserRepository`      | `findByName(String)` / `findByEmail(String)` | Fetch user based on credentials |

---
