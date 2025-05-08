## 🔗 API Overview

Here’s a list of the main endpoints grouped by resource:

### 📘 Authentication
- `POST /ebook/authorization/login` – Authenticate user and return JWT token
- `POST /ebook/authorization/validate` – Validate the provided JWT token

### 👤 Users
- `GET /ebook/users` – Get all users
- `GET /ebook/users/{id}` – Get user by ID
- `DELETE /ebook/users/{id}` – Delete user by ID
- `PATCH /ebook/users/{id}` – Partially update user
- `POST /ebook/users/{id}/borrowedBooks` – Add a borrowed book to a user
- `GET /ebook/users/{id}/borrowedBooks` – Get all borrowed books for a user
- `GET /ebook/users/{id}/reservations` – Get all reservations for a user

### 👨‍💼 Authors
- `GET /ebook/authors` – Get all authors
- `GET /ebook/authors/{id}` – Get author by ID
- `POST /ebook/authors` – Create new author
- `PUT /ebook/authors/{id}` – Fully update an author
- `PATCH /ebook/authors/{id}` – Partially update an author
- `DELETE /ebook/authors/{id}` – Delete author by ID
- `GET /ebook/authors/{id}/books` – Get books written by author

### 📚 Books
- `GET /ebook/books` – Get all books
- `GET /ebook/books/{id}` – Get book by ID
- `POST /ebook/books` – Create a new book
- `PUT /ebook/books/{id}` – Fully update a book
- `PATCH /ebook/books/{id}` – Partially update a book
- `DELETE /ebook/books/{id}` – Delete book

### 🏷️ Categories
- `GET /ebook/categories` – Get all categories
- `GET /ebook/categories/{id}` – Get category by ID
- `GET /ebook/categories/{id}/books` – Get books in a category
- `POST /ebook/categories` – Create a new category
- `PUT /ebook/categories/{id}` – Update a category
- `PATCH /ebook/categories/{id}` – Partially update a category
- `DELETE /ebook/categories/{id}` – Delete category

### 🏢 Publishers
- `GET /ebook/publishers` – Get all publishers
- `GET /ebook/publishers/{id}` – Get publisher by ID
- `GET /ebook/publishers/{id}/books` – Get books published by a publisher
- `POST /ebook/publishers` – Create a new publisher
- `PUT /ebook/publishers/{id}` – Update a publisher
- `PATCH /ebook/publishers/{id}` – Partially update a publisher
- `DELETE /ebook/publishers/{id}` – Delete publisher

### 📖 Borrowed Books
- `GET /ebook/borrowedBooks` – Get all borrowed book records
- `GET /ebook/borrowedBooks/{id}` – Get borrowed book record by ID
- `POST /ebook/borrowedBooks` – Create a new borrowed book record
- `PATCH /ebook/borrowedBooks/{id}` – Partially update borrowed book
- `PUT /ebook/borrowedBooks/{id}` – Fully update borrowed book
- `DELETE /ebook/borrowedBooks/{id}` – Delete borrowed book
- `POST /ebook/borrowedBooks/returnBook` – Return a borrowed book

### 📅 Reservations
- `GET /ebook/reservations` – Get all reservations
- `GET /ebook/reservations/{id}` – Get reservation by ID
- `POST /ebook/reservations` – Create new reservation
- `PATCH /ebook/reservations/{id}` – Partially update reservation
- `PUT /ebook/reservations/{id}` – Fully update reservation
- `DELETE /ebook/reservations/{id}` – Delete reservation

### 💰 Fines
- `GET /ebook/fines` – Get all fines
- `GET /ebook/fines/{id}` – Get fine by ID
- `POST /ebook/fines` – Create a new fine
- `PATCH /ebook/fines/{id}` – Partially update fine
- `PUT /ebook/fines/{id}` – Update fine
- `DELETE /ebook/fines/{id}` – Delete fine
- `POST /ebook/fines/payFine` – Pay a fine