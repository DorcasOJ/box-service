# Box Delivery Service

A Spring Boot REST API for managing delivery boxes and the items loaded into them.

The service provides APIs for creating boxes, loading items, checking loaded items, checking available boxes, and monitoring battery levels.

The physical communication between the backend and the delivery box is outside the scope of this application.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Domain Model](#domain-model)
- [Box States](#box-states)
- [Business Rules](#business-rules)
- [API Endpoints](#api-endpoints)
- [API Examples](#api-examples)
- [Database](#database)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Building the Application](#building-the-application)
- [Design Decisions](#design-decisions)
- [Assumptions](#assumptions)
- [Out of Scope](#out-of-scope)

---

# Overview

The Box Delivery Service manages delivery boxes used to transport small items to remote locations.

Each box has:

- A unique transaction/reference number
- A maximum carrying capacity
- A current loaded weight
- A remaining carrying capacity
- A battery level
- A delivery state
- A camera capability

Items loaded into a box contain:

- A name
- A weight
- A unique code

The service validates loading operations to ensure that boxes never exceed their maximum carrying capacity and that boxes with insufficient battery cannot enter the loading process.

---

# Features

- Create a delivery box
- Load multiple items into a box
- Validate item weight and capacity
- Retrieve items loaded into a box
- Retrieve available boxes
- Retrieve box battery level
- Track box state
- Track current and remaining carrying capacity
- Validate minimum battery requirements
- Transactional loading operations
- Database migrations using Flyway
- PostgreSQL persistence
- Bean Validation
- Global exception handling
- Unit and integration testing

---

# Technology Stack

- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
- Gradle
- JUnit 5
- Mockito
- Spring Boot Test
- Docker / Docker Compose

---

# Domain Model

## Box

A box represents a physical delivery container.

| Field | Description |
|---|---|
| id | UUID identifier |
| txref | Unique box reference, maximum 20 characters |
| maxWeight | Maximum carrying capacity in grams |
| currentWeight | Current weight of loaded items in grams |
| remainingCapacity | maxWeight - currentWeight |
| batteryLevel | Battery percentage from 0 to 100 |
| state | Current box state |
| cameraEnabled | Whether the box has an available camera |

Example:

```json
{
  "id": "8b7c...",
  "txref": "BOX-001",
  "maxWeight": 5000,
  "currentWeight": 1200,
  "remainingCapacity": 3800,
  "batteryLevel": 85,
  "state": "IDLE",
  "cameraEnabled": true
}


# Prerequisites

Before running the application, ensure the following are installed:

### Java 21
### PostgreSQL

Verify your Java installation:

```bash
### java -version

How to crete PostgreSQL Database, run the following
### psql -U postgres
### CREATE DATABASE box_delivery

create a local PostgreSQL, create a ```.env file, edit
### POSTGRES_USER=postgres
### POSTGRES_PASS=your_password
### POSTGRES_URL=jdbc:postgresql://localhost:5432/box_delivery

Then run
### .\gradlew clean build

When build is complete, run
### java -jar build/libs/box-delivery-service-0.0.1-SNAPSHOT.jar

And run this on your browser
### http://localhost:8080/swagger-ui/index.html#/
