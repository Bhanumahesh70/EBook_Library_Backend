# 📚 Ebook Management Backend (Spring Boot)

This is the backend of an **Ebook Management System**, built using **Spring Boot**, **Spring Security**, **JPA**, and **JWT**. It supports user authentication, book borrowing, fine tracking, and reservation functionalities.

---

##  Table of Contents
- [Detailed Code OverView](#-detailed-code-overview) 
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [API Overview](#api-overview)
- [Testing](#testing)
- [License](#license)

---


## 📖 Detailed Code Overview

For deeper insights into each backend layer, refer to the following documents in the [`docs/`](docs) folder:

- [`DOMAIN.md`](docs/DOMAIN.md) – Entity definitions, relationships, and validation
- [`SECURITY.md`](docs/SECURITY.md) – JWT security configuration and roles
- [`DTO.md`](docs/DTO.md) – Data Transfer Objects and mappings
- [`CONTROLLER.md`](docs/CONTROLLER.md) – REST API structure and controller logic
- [`SERVICE.md`](docs/SERVICE.md) – Business logic and service patterns
- [`REPOSITORIES.md`](docs/REPOSITORIES.md) – JPA repositories and custom query examples
- [`SECURITY.md`](docs/SECURITY.md) – JWT security configuration and roles
- [`TESTING.md`](docs/TESTING.md) – Unit and integration testing breakdown
- [`API Endpoints`](docs/API_OVERVIEW.md) - Detailed API Endpoints
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
    spring.datasource.url=jdbc:mysql://localhost:3306/ebook?createDatabaseIfNotExist=true
    spring.datasource.username=ebookAdmin
    spring.datasource.password=ebookAdmin
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=create
   ```

3. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

##  API Overview
For a complete list of API endpoints, see the [API_OVERVIEW.md](docs/API_OVERVIEW.md) file.

- `/ebook/authorization/login` – Authenticate user
- `/ebook/users` – Manage users
- `/ebook/books` – Manage books
- `/ebook/borrowedBooks` – Borrowing logic
- `/ebook/reservations` – Handle book reservations
- `/ebook/fines` – Fine tracking
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


