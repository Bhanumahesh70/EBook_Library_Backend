#  Security
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