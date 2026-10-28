# Run and test the Hospital Management API

Run these commands from the project root.

## Start the application

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows Command Prompt:

```bat
mvnw.cmd spring-boot:run
```

The API listens at `http://localhost:8080`. Leave this terminal running while you test. The app uses an in-memory H2 database, so its records are cleared when the app stops.

## Run automated tests

Linux/macOS:

```bash
./mvnw test
```

Windows Command Prompt:

```bat
mvnw.cmd test
```

## Build a runnable JAR

Linux/macOS:

```bash
./mvnw clean package
java -jar target/hospital-management-0.0.1-SNAPSHOT.jar
```

Windows Command Prompt:

```bat
mvnw.cmd clean package
java -jar target\hospital-management-0.0.1-SNAPSHOT.jar
```

## Run every CRUD request in Postman

1. Start the application using the command above.
2. In Postman, choose **Import** and select [`postman/Hospital-Management-API.postman_collection.json`](./postman/Hospital-Management-API.postman_collection.json).
3. If this collection should be shared with your team, select the team's Postman workspace before importing it (or move the imported collection into that workspace).
4. Open **Hospital Management API - CRUD Tests**, select **Run**, and run the complete collection in order.
5. Check the run results. The collection tests create, list, retrieve, update, and delete each resource, then verify that deleted records return HTTP 404. It captures doctor and patient IDs from the create responses for appointment requests.

The collection's `baseUrl` variable defaults to `http://localhost:8080`. Change it in the collection variables if the app runs on another host or port. Run the complete collection against a fresh application/database session because the tests delete the records they create.

## Quick check with curl

With the application running, list doctors:

```bash
curl http://localhost:8080/api/doctors
```

Create a doctor:

```bash
curl -X POST http://localhost:8080/api/doctors \
  -H "Content-Type: application/json" \
  -d '{"name":"Jordan Lee","specialization":"Family Medicine","email":"jordan.lee@example.com","phone":"555-0101"}'
```
