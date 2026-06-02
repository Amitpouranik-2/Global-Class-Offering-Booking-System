# Global-Class-Offering-Booking-System


## Project Overview

Global Class Offering Booking System is a Spring Boot based backend application that allows teachers to create and manage courses, offerings, and sessions while enabling parents/students to browse available offerings and book them.

The system supports:

* Role-based authentication and authorization
* Teacher course management
* Offering and session scheduling
* Parent booking management
* Timezone-aware session handling
* Booking conflict detection
* Concurrent booking protection

---

## Features

### Teacher Features

* Register and Login
* Create Courses
* Update Courses
* Delete Courses
* Create Offerings
* Manage Offerings
* Delete Offerings
* Fetch Offerings
* Create Sessions for Offerings
* Update Sessions for Offerings
* View Upcoming Sessions
* View Upcoming Offerings



### Parent Features

* Register and Login
* View Available Offerings
* View Available Sessions
* Book Offerings
* View My Bookings
* Cancel Bookings

---

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate
* JWT Authentication

### Database

* PostgreSQL 15

### API Documentation

* OpenAPI 3
* Swagger UI

### Build Tool

* Maven

---

## Project Architecture

```text
Controller Layer
       ↓
Service Layer
       ↓
Repository Layer
       ↓
Postgres SQL Database
```

---

## Authentication

JWT-based authentication is implemented.

After login:

```text
Authorization: Bearer <jwt-token>
```

must be sent with every protected API request.

---

## User Roles

### TEACHER

Can:

* Create Courses
* Create Offerings
* Create Sessions
* View Own Courses
* View Own Offerings

### PARENT

Can:

* View Available Offerings
* Book Offerings
* View Own Bookings
* Cancel Bookings

---

## Database Schema Overview

### users

| Column     | Type    |
| ---------- | ------- |
| id         | BIGINT  |
| name       | VARCHAR |
| email      | VARCHAR |
| password   | VARCHAR |
| role       | VARCHAR |
| timezone   | VARCHAR |

---

### course

| Column      | Type    |
| ----------- | ------- |
| id          | BIGINT  |
| name        | VARCHAR |
| description | TEXT    |
| user_id     | BIGINT  |

Relationship:

```text
User (Teacher)
    1
    |
    N
 Course
```

---

### offering

| Column      | Type    |
| ----------- | ------- |
| id          | BIGINT  |
| name        | VARCHAR |
| description | VARCHAR |
| maxCapacity | BIGINT  |
| course_id   | BIGINT  |


Relationship:

```text
Course
   1
   |
   N
Offering
```

---

### sessions

| Column      | Type     |
| ----------- | -------- |
| id          | BIGINT   |
| offering_id | BIGINT   |
| start_time  | DATETIME |
| end_time    | DATETIME |

Relationship:

```text
Offering
   1
   |
   N
Session
```

---

### booking

| Column      | Type     |
| ----------- | -------- |
| id          | BIGINT   |
| user_id     | BIGINT   |
| offering_id | BIGINT   |
| booked_at   | DATETIME |

Relationship:

```text
Parent(User)
     N
     |
 Booking
     |
     N
 Offering
```

---

## Timezone Handling Approach

### Teacher Side

Teachers create sessions using their local timezone.

Example:

```text
Teacher Timezone = Asia/Kolkata

Session:
6:00 PM - 7:00 PM
```

Before storing:

```text
Asia/Kolkata
        ↓
Convert to UTC
        ↓
Store in Database
```

Example:

```text
6:00 PM IST
=
12:30 PM UTC
```

Database stores:

```text
2026-06-14 12:30:00
```

---

### Parent Side

Parent may belong to a different timezone.

Example:

```text
Parent Timezone = America/New_York
```

When fetching sessions:

```text
UTC Time
     ↓
Convert to Parent Timezone
     ↓
Return Response
```

Example:

```text
12:30 PM UTC
=
8:30 AM New York
```

Thus every parent sees session timings in their own local timezone.

---

## Booking Conflict Handling

Parents cannot book overlapping offerings.

Example:

Already Booked:

```text
June 14
5 PM - 6 PM
```

Attempting:

```text
June 14
5:30 PM - 6:30 PM
```

Result:

```text
Booking Rejected
```

Conflict Detection Rule:

```text
existingStart < newEnd
AND
existingEnd > newStart
```

If both are true, session overlap exists.

---

## Concurrency Handling Approach

The application prevents invalid concurrent bookings.

### Problem

Multiple requests may arrive simultaneously.

Example:

```text
Request A
Request B
```

Both attempt booking at the same time.

Without protection:

```text
Both pass validation
Both create bookings
```

Result:

```text
Data inconsistency
```

### Solution

Pessimistic Locking is used.

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
```

During booking:

```text
Parent Row Locked
       ↓
Conflict Validation
       ↓
Booking Creation
       ↓
Transaction Commit
```

This ensures:

* No duplicate bookings
* No overlapping bookings
* Consistent data

---

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI Docs:

```text
http://localhost:8080/v3/api-docs
```

---

## Main APIs

### Authentication

```http
POST /api/v1/auth/register
POST /api/v1/auth/login
```

### Teacher

```http
POST   /api/v1/teacher/courses
PUT    /api/v1/teacher/courses/{id}
GET    /api/v1/teacher/courses
DELETE /api/v1/teacher/courses/{id}
GET    /api/v1/teacher/courses/{id}

POST   /api/v1/teacher/offerings
PUT    /api/v1/teacher/offerings/{id}
GET    /api/v1/teacher/offerings
DELETE /api/v1/teacher/offerings/{id}
GET    /api/v1/teacher/offerings/{id}
GET    /api/v1/teacher/offerings/offering/upcoming

POST   /api/v1/teacher/offerings/{offeringId}/sessions
PUT    /api/v1/teacher/sessions/{sessionId}
GET    /api/v1/teacher/sessions/upcoming
DELETE /api/v1/teacher/sessions/{sessionId}


```

### Parent

```http
GET    /api/v1/parent/offerings
POST   /api/v1/parent/bookings/{offeringId}
GET   /api/v1/parent/bookings
GET    /api/v1/parent/bookings/{id}
DELETE /api/v1/parent/bookings/{id}
```

---

## Environment Variables

Create an application.properties file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/booking_system
spring.datasource.username=root
spring.datasource.password=password

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

---

## Setup Instructions

### Clone Repository

```bash
git clone https://github.com/your-username/global-class-offering-booking-system.git
```

### Move into Project

```bash
cd global-class-offering-booking-system
```

### Configure Database

Create:

```sql
CREATE DATABASE booking_system;
```

Update datasource credentials.

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

Application starts on:

```text
http://localhost:8080
```

---

## Assumptions Made

* A Teacher is represented by a User having role = TEACHER.
* A Parent is represented by a User having role = PARENT.
* Session times are stored in UTC.
* User timezone is mandatory.
* A Parent cannot book the same offering twice.
* A Parent cannot book overlapping session timings.
* An Offering may contain multiple sessions.
* JWT authentication is required for all secured APIs.
---
