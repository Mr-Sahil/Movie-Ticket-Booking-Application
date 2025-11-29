Movie Ticket Booking — Full-stack Project

Production-ready starter full-stack movie ticket booking app (backend + frontend) built with Java + Jakarta EE (REST APIs), Payara, MySQL, and React.js.
This repository contains a working backend service (Jakarta EE / JAX-RS) and a simple React frontend that lets users view shows, select seats, and place bookings.

1. Project summary
This project implements a minimal but production-minded movie ticket booking system:
Shows (movie + screen + start time) are created in the DB.
Each show has seats (ShowSeat) that can be booked.
Backend uses pessimistic locking for booking to avoid double booking.
Frontend is a React app that lists shows, renders a seat grid, and posts bookings.
The goal: a simple end-to-end demo you can run locally or containerize for demos / interviews.

2. Tech stack
Java 17
Jakarta EE (Jakarta Enterprise Edition) — JAX-RS for REST endpoints, JPA (Java Persistence API) for ORM
Payara (Payara Server / Payara Micro) as the application server
MySQL as the relational database (via JDBC — Java Database Connectivity)
React.js for frontend (Create React App style)
Docker & Docker Compose for local containerized run
Lombok for DTOs and entity boilerplate reduction
JSON-B (JSON Binding) for JSON serialization/deserialization
Note: acronyms are expanded where used (example: JDBC = Java Database Connectivity).

3. Repo layout (important files)
movie-ticket-booking/
├── backend/
│   ├── pom.xml
│   ├── Dockerfile
│   ├── create-resources.asadmin
│   └── src/
│       ├── main/java/com/example/booking/...
│       │   ├── ApplicationConfig.java
│       │   ├── entities/        (JPA entities)
│       │   ├── dao/             (DAO / repository EJBs)
│       │   ├── services/        (business logic)
│       │   ├── resources/       (JAX-RS resources / controllers)
│       │   └── dto/             (Lombok DTOs & mappers)
│       └── main/resources/META-INF/persistence.xml
├── frontend/
│   ├── package.json
│   ├── Dockerfile
│   └── src/
│       ├── index.js
│       ├── App.js
│       ├── api.js
│       └── components/SeatSelector.jsx
└── docker-compose.yml
└── sql/
    └── init.sql

4. Quick start — run with Docker Compose (recommended)
Make sure Docker Desktop (or Docker Engine) is installed & running.
From project root:
# ensure docker daemon is running, then:
docker compose up --build
Services started:
MySQL: <db-host>:3306 (DB: ticketdb, user: ticketuser, password: ticketpass)
Backend (Payara): http://<backend-host>:8080/api
Frontend (React + Nginx): http://<frontend-host>:3000
If you want logs in the terminal, omit -d to run in foreground. To stop:
docker compose down

5. Manual build & run (without Docker)
Backend
Build JAR (from backend folder):
cd backend
mvn clean package
Deploy JAR to Payara server or run Payara Micro:
java -jar target/ticket-backend.jar
Note: if running outside Docker, configure the JDBC resource / persistence.xml to point at your MySQL and create the database.
Frontend (dev)
cd frontend
npm install
npm start
# frontend dev server opens at http://<frontend-host>:3000
To build production frontend bundle:
npm run build

6. Configuration & environment variables
docker-compose.yml passes these env vars to backend and MySQL:
Backend (Payara):
DB_HOST (e.g. db)
DB_PORT (e.g. 3306)
DB_NAME (e.g. ticketdb)
DB_USER (e.g. ticketuser)
DB_PASS (e.g. ticketpass)
Frontend:
REACT_APP_API_URL — base URL the React app uses (set to http://backend:8080/api in Compose).
If running outside Docker, set the same variables or use a configuration mechanism appropriate for your runtime.

7. API — endpoints & sample requests
All endpoints are under the JAX-RS root /api (see ApplicationConfig.java).
Shows
GET /api/shows
Response: 200 OK — list of shows (ShowDto)
GET /api/shows/{id}
Response: 200 OK — single show detail
GET /api/shows/{id}/seats
Response: 200 OK — list of show seats (ShowSeatDto with fields: id, seatNumber, booked)
Bookings
POST /api/bookings — create booking
Request body example (JSON):
{
  "userId": 1,
  "showId": 3,
  "seatIds": [12, 13]
}
Responses:
201 Created → booking created (Booking object)
409 Conflict → if seat already booked or concurrency conflict (returns { "error": "..."} )
Users / Movies / Screens (CRUD stubs)
GET /api/movies
POST /api/movies
GET /api/users/{id}
POST /api/users

8. Database schema (high-level)
See sql/init.sql for the exact schema. Important tables:
users — id, name, email, password_hash
movies — id, title, language, genre, duration_minutes
screens — id, name, total_seats
shows — id, movie_id, screen_id, start_time
seats — id, seat_number, type, screen_id
show_seats — id, show_id, seat_id, booked (boolean), locked_by, lock_expires_at, version (for optimistic locking)
bookings — id, user_id, show_id, booked_at, status
booking_items — id, booking_id, seat_id, price
We use unique index on (show_id, seat_id) to avoid duplicate show-seat rows.

9. Lombok, DTOs & design notes
Lombok is used to reduce boilerplate (@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor).
Important: We avoid @Data on JPA entities (it auto-generates equals/hashCode which can break proxies). @Data is used for DTOs only.
DTOs (Data Transfer Objects) are in backend/src/.../dto and annotated with Lombok @Data for convenience.
DAO layer uses @Stateless EJBs (Session Beans) with @PersistenceContext.
BookingService uses @Transactional and pessimistic locking on ShowSeat rows to ensure atomic seat reservations.

10. Local development tips (IntelliJ / Maven / Docker)
Enable Lombok annotation processing in IntelliJ: Settings → Build, Execution, Deployment → Compiler → Annotation Processors → enable.
Maven: use IntelliJ bundled Maven or install maven and set MAVEN_HOME. Run mvn clean package to build backend jar.
Docker: Start Docker Desktop on Windows, then run docker compose up --build.
Keep docker-compose.yml at project root (so build: ./backend and build: ./frontend paths work).

11. Testing, logging & concurrency
Add unit tests for DAO and service methods (use an embedded DB or Dockerized test container).
Add an integration test that simulates concurrent booking requests to validate locking behavior.
Add structured logs (SLF4J / Logback) for observability.

12. Security & production considerations
Authentication & Authorization: integrate JWT (JSON Web Token) or OAuth2 (Keycloak) for user accounts.
Password storage: always store salted hashed passwords with bcrypt/Argon2.
Input validation: use Jakarta Validation (jakarta.validation) on DTOs.
Rate limiting: protect booking endpoint to avoid abuse.
DB migrations: use Flyway or Liquibase (do not rely on SQL scripts in production).
Secrets: don’t store DB credentials in plain docker-compose.yml for production — use Vault / environment configs.
TLS / HTTPS: terminate TLS at load balancer or use ALB / ingress.

13. Next steps & improvements
Add login/signup frontend pages and user session.
Improve UI with Material UI / Tailwind + seat map like BookMyShow.
Add admin panel for shows/movies/screens.
Add CI/CD (GitHub Actions) to build, test, and push container images.
Add fallback and retry logic for database deadlocks.

14. Author
Author: Sahil Soni
Project for Learning Purpose
