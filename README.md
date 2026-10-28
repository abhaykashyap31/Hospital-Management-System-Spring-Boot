# Hospital Management API

A Spring Boot REST API for managing doctors, patients, and appointment schedules. The project uses constructor-based dependency injection, Spring Data JPA, request validation, and a centralized exception handler.

## Requirements

- Java 17 or later

## Run

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`. It uses an in-memory H2 database, so data is cleared when the application stops.

On Windows, use `mvnw.cmd spring-boot:run`.

## API endpoints

Each resource supports `GET /api/{resource}`, `GET /api/{resource}/{id}`, `POST /api/{resource}`, `PUT /api/{resource}/{id}`, and `DELETE /api/{resource}/{id}`:

- `/api/doctors`
- `/api/patients`
- `/api/appointments`

Appointment request fields:

```json
{
  "doctorId": 1,
  "patientId": 1,
  "startAt": "2026-10-27T10:00:00",
  "endAt": "2026-10-27T10:30:00",
  "status": "SCHEDULED",
  "notes": "Initial consultation"
}
```

`status` defaults to `SCHEDULED`. Appointment time ranges must have an end after their start. Active appointments may not overlap for the same doctor or patient; cancelled appointments do not reserve a time slot.

## Response format

All successful and failed responses use the same envelope:

```json
{
  "timestamp": "2026-10-26T17:00:00Z",
  "success": true,
  "message": "Doctor created",
  "data": {
    "id": 1,
    "name": "Avery Chen",
    "specialization": "Cardiology",
    "email": "avery.chen@example.com",
    "phone": "555-0100"
  },
  "errors": {}
}
```

## Test

```bash
./mvnw test
```

The Maven Wrapper downloads and uses the project's configured Maven version, so a separate Maven installation is not required.
