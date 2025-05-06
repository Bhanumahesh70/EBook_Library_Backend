# 📚 Ebook Management Backend (Spring Boot)

This is the backend of an **Ebook Management System**, built using **Spring Boot**, **Spring Security**, **JPA**, and **JWT**. It supports user authentication, book borrowing, fine tracking, and reservation functionalities.

---

##  Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [API Overview](#api-overview)
- [Testing](#testing)
- [License](#license)

---

## Features

- User Authentication using JWT
- Role-based access control
- Manage:
    - Users, Authors, Books, Categories, Publishers
    - Book Borrowing and Reservations
    - Fines and Payment Statuses
- RESTful APIs for each entity
- DTO-based data transfer
- Startup fine update job
- Comprehensive validation and JPA tests

---

##  Tech Stack

- Java 23
- Spring Boot 3.4.0
- Spring Security
- Spring Data JPA + Hibernate
- MySQL
- JWT for Authentication
- Maven for Build
- JUnit for Testing

---

## Project Structure

```
com.ebook
├── configuration          # Security, startup jobs
├── controller             # REST Controllers
├── domain                 # Entities & Enums
├── dto                    # Data Transfer Objects
├── repository             # Spring Data Repositories
├── security               # JWT Auth logic
├── service                # Business logic
└── test                   # Validation & JPA Tests
```

---

## Setup Instructions

### Prerequisites
- Java 23+
- Maven 3.9+
- MySQL database

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/EbookWebsite.git
   cd EbookWebsite
   ```

2. Configure your `application.properties` or `application.yml`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ebook_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

##  API Overview

- `/api/auth/login` – Authenticate user
- `/api/users` – Manage users
- `/api/books` – Manage books
- `/api/borrowed-books` – Borrowing logic
- `/api/reservations` – Handle book reservations
- `/api/fines` – Fine tracking
---

##  Testing

Tests are organized into:
- `BeanValidationTest` – Validates entity constraints
- `JPATest` – Validates JPA mappings and repository queries

Run tests using:
```bash
mvn test
```

---

## License

This project is open source and available under the [MIT License](LICENSE).
