# Secure Payment Service

A high-consistency financial microservice designed to handle wallet management, deposits, and peer-to-peer transfers. This service acts as the transactional core for a distributed architecture, prioritizing ACID compliance, data integrity, and fault tolerance.

## Key Features

* **Concurrency Control:** Implements **Optimistic Locking** (`@Version`) to prevent race conditions and ensure balance consistency during simultaneous transaction requests.
* **Idempotency:** Enforces transaction uniqueness via `Idempotency-Key` headers and database constraints, preventing double-spending during network retries or distributed system failures.
* **Hexagonal Architecture:** Strict decoupling of the Domain layer from Infrastructure (Persistence, Web), following Ports and Adapters patterns for maintainability and testability.
* **Standardized Error Handling:** API error responses comply with **RFC 7807 (Problem Details)**, providing structured and parseable error information to clients.
* **Database Migrations:** Automated schema management using **Flyway**.

## Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 4
* **Database:** PostgreSQL 16
* **Versioning:** Flyway
* **Containerization:** Docker & Docker Compose

## Getting Started

### Prerequisites
* Docker and Docker Compose installed.

### Execution
Run the application and database containers using Docker Compose:

```bash
docker-compose up --build -d
```
The application will start on port 8080.

###  API Reference
#### 1. Create Wallet
Creates a new user wallet with an initial balance of zero.

> POST /api/wallets

Body:

```JSON
{
  "userId": "uuid-v4",
  "document": "unique-document-string"
}
```
Response: 201 Created

### 2. Deposit (Cash-In)
Adds funds to a specific wallet.

> POST /api/wallets/{id}/deposit

Body:

```JSON
{
  "amount": 1000.00
}
```
Response: 200 OK

#### 3. Transfer
Executes a financial transfer between two wallets. Requires an idempotency key.

> POST /api/transfers

- Headers:

> Idempotency-Key: unique-string-per-request (Required)

Body:

```JSON
{
  "payerId": "uuid-wallet-a",
  "payeeId": "uuid-wallet-b",
  "amount": 100.00
}
```
Response: 200 OK

```JSON
{
  "transactionId": "uuid",
  "status": "COMPLETED",
  "amount": 100.00,
  "timestamp": "iso-8601-date"
}
```

## Architecture
The project follows a strict Hexagonal Architecture structure:
```
src/main/java/io/github/pedrozaz/paymentcore
├── application      # Application Business Rules (Use Cases & Gateways)
├── domain           # Enterprise Logic (Entities & Exceptions) - No Frameworks
└── infra   # Frameworks, Drivers, & Adapters (Controllers, Persistence)
```