##  Data Transfer Objects (DTOs)

DTOs serve as lightweight, serializable carriers of data between the **controller**, **service**, and **view** layers without exposing your JPA entities directly. This promotes **loose coupling**, **data hiding**, and **clean separation of concerns**.


###  Benefits of Using DTOs

- Prevents exposing internal JPA models to external API clients
- Simplifies or flattens complex relationships for API output
- Enhances validation by using only relevant fields
- Helps structure response formats (e.g., nested objects, selective fields)

---

### ️ Mapping Strategy

- **Manual Mapping**:
    - Implemented inside service layer methods (e.g., `mapToDTO()`, `mapFromDTO()`)
    - Enables fine-grained control over transformations

---

###  Example DTO Types

| DTO Class             | Purpose                                                           |
|-----------------------|-------------------------------------------------------------------|
| `BookDTO`             | Excludes full entity relationships (e.g., `CategoryDTO`, `AuthorDTO`) |
| `UserDTO`             | Sends only basic user info with optional nested DTOs (`FineDTO`)  |
| `AuthorDTO`           | Hides JPA relations, returns optional `bookDetails` as nested DTOs|
| `AuthenticationDTOs` | Used for login and token exchange                                |

---

###  DTO Validation

- DTOs support validation annotations just like entities:

  ```java
  @Email(message = "Invalid email")
  @Size(min = 8, message = "Minimum length required")
  ```

- Validated via `@Valid` in controller method arguments:

  ```java
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO)
  ```

---

###  Bi-Directional vs. Uni-Directional Mapping

- Most DTOs (e.g., `BookDTO`, `AuthorDTO`, `UserDTO`) **support bidirectional nesting** via `List<...DTO>` or detail fields
- This allows UI clients to render full object graphs in one request

---

###  DTO Test Coverage Suggestion

To ensure DTOs are reliable:

- Unit test mappings with mock entities
- Validate JSON serialization/deserialization
- Check error messages from constraint violations

---

###  Sample Mapping 

```java
public static AuthorDTO mapToDTO(Author author) {
    AuthorDTO dto = new AuthorDTO();
    dto.setName(author.getName());
    dto.setBio(author.getBio());
    return dto;
}
```


