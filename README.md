# Spring Transaction Propagation Demo

A Spring Boot project demonstrating all **7 transaction propagation types** using dual transaction log tables (`transaction_log1` and `transaction_log2`).

---

## 📁 Project Structure

```
src/main/java/
├── controller/
│   └── TransactionLogController.java       # REST endpoints for each propagation type
├── facade/
│   ├── TransactionLogFacade.java           # Facade interface
│   └── TransactionLogFacadeImpl.java       # Propagation logic per method
├── service/
│   ├── TransactionLog1Service.java
│   ├── TransactionLog1ServiceImpl.java     # Saves to transaction_log1
│   ├── TransactionLog2Service.java
│   └── TransactionLog2ServiceImpl.java     # Saves to transaction_log2 (has intentional bug)
├── entity/
│   ├── TransactionLog1.java                # Maps to transaction_log1 table
│   └── TransactionLog2.java                # Maps to transaction_log2 table
├── repository/
│   ├── TransactionLog1Repository.java
│   └── TransactionLog2Repository.java
└── dto/
    └── TransactionLogDto.java              # Request payload (message1, message2)
```

---

## 🗄️ Database Tables

### `transaction_log1`
| Column       | Type          | Constraints          |
|--------------|---------------|----------------------|
| `id`         | BIGINT        | PK, Auto-increment   |
| `message`    | VARCHAR(500)  | NOT NULL             |
| `created_at` | DATETIME      | NOT NULL             |

### `transaction_log2`
| Column       | Type          | Constraints          |
|--------------|---------------|----------------------|
| `id`         | BIGINT        | PK, Auto-increment   |
| `message`    | VARCHAR(500)  | NOT NULL             |
| `created_at` | DATETIME      | NOT NULL             |

---

## 🔗 API Endpoints

Base URL: `/api/transaction-logs`

| Method | Endpoint          | Propagation     | Description                                      |
|--------|-------------------|-----------------|--------------------------------------------------|
| POST   | `/required`       | `REQUIRED`      | Joins existing or creates new transaction        |
| POST   | `/requires-new`   | `REQUIRES_NEW`  | Always creates a new independent transaction     |
| POST   | `/nested`         | `NESTED`        | Creates savepoint within existing transaction    |
| POST   | `/supports`       | `SUPPORTS`      | Joins if exists, else runs non-transactionally   |
| POST   | `/not-supported`  | `NOT_SUPPORTED` | Always runs non-transactionally                  |
| POST   | `/mandatory`      | `MANDATORY`     | Requires existing transaction or throws          |
| POST   | `/never`          | `NEVER`         | Fails if transaction exists                      |

### Request Body (all endpoints)
```json
{
  "message1": "Log entry for table 1",
  "message2": "Log entry for table 2"
}
```

### Response
```
200 OK
"<PROPAGATION_TYPE>: TransactionLog added successfully"
```

---

## ⚙️ Transaction Propagation Behaviour

### 1. `REQUIRED` *(Default)*
```
POST /api/transaction-logs/required
```
- Joins an existing transaction if one is active.
- Creates a new transaction if none exists.
- If **either** service fails → **full rollback** of both logs.

---

### 2. `REQUIRES_NEW`
```
POST /api/transaction-logs/requires-new
```
- Suspends any active transaction.
- Always creates a **fresh independent** transaction.
- Each service commits/rolls back independently.

---

### 3. `NESTED`
```
POST /api/transaction-logs/nested
```
- Creates a **savepoint** inside the outer transaction.
- Inner failure rolls back to the savepoint only.
- Outer transaction can still commit.

---

### 4. `SUPPORTS`
```
POST /api/transaction-logs/supports
```
- Joins an existing transaction if one is active.
- Runs **non-transactionally** if no transaction exists.
- Suitable for read-heavy operations.

---

### 5. `NOT_SUPPORTED`
```
POST /api/transaction-logs/not-supported
```
- Suspends any existing transaction.
- Always runs **non-transactionally**.
- No rollback support — each save is immediately committed.

---

### 6. `MANDATORY`
```
POST /api/transaction-logs/mandatory
```
- **Requires** an active transaction from the caller.
- Throws `IllegalTransactionStateException` if no transaction exists.
- Use this to enforce transactional callers.

---

### 7. `NEVER`
```
POST /api/transaction-logs/never
```
- **Must not** be called within an active transaction.
- Throws `IllegalTransactionStateException` if a transaction is detected.
- Opposite of `MANDATORY`.

---

## 📊 Quick Reference

| Propagation     | Transaction Exists       | No Transaction       |
|-----------------|--------------------------|----------------------|
| `REQUIRED`      | Joins it                 | Creates new          |
| `REQUIRES_NEW`  | Suspends, creates new    | Creates new          |
| `NESTED`        | Creates savepoint        | Creates new          |
| `SUPPORTS`      | Joins it                 | Runs without         |
| `NOT_SUPPORTED` | Suspends it              | Runs without         |
| `MANDATORY`     | Joins it                 | ❌ Throws exception  |
| `NEVER`         | ❌ Throws exception      | Runs without         |

---

## ⚠️ Known Bug (Intentional for Demo)

In `TransactionLog2ServiceImpl`, the `createdAt` field is set using an **invalid date-time string**:

```java
transactionLog2.setCreatedAt(LocalDateTime.parse("Genara")); // ❌ Invalid
```

This intentionally throws a `DateTimeParseException` at runtime to **trigger a rollback** and demonstrate how propagation types handle failures differently.

To fix this for production use, replace with:

```java
transactionLog2.setCreatedAt(LocalDateTime.now()); // ✅ Valid
```

---

## 🛠️ Tech Stack

| Technology        | Version      |
|-------------------|--------------|
| Java              | 17+          |
| Spring Boot       | 3.x          |
| Spring Data JPA   | 3.x          |
| Hibernate         | 6.x          |
| Lombok            | Latest       |
| MySQL / H2        | Any          |
| Maven / Gradle    | Any          |

---

## 🚀 Running the Project

```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd spring-transaction-propagation-demo

# Run with Maven
./mvnw spring-boot:run

# Run with Gradle
./gradlew bootRun
```

---

## 🧪 Testing with cURL

```bash
# Test REQUIRED propagation
curl -X POST http://localhost:8080/api/transaction-logs/required \
  -H "Content-Type: application/json" \
  -d '{"message1": "Log 1", "message2": "Log 2"}'

# Test REQUIRES_NEW propagation
curl -X POST http://localhost:8080/api/transaction-logs/requires-new \
  -H "Content-Type: application/json" \
  -d '{"message1": "Log 1", "message2": "Log 2"}'

# Test NESTED propagation
curl -X POST http://localhost:8080/api/transaction-logs/nested \
  -H "Content-Type: application/json" \
  -d '{"message1": "Log 1", "message2": "Log 2"}'
```

---

## 📝 Logging

Each facade method logs its propagation context using SLF4J:

```
[REQUIRED] Starting transaction log persistence
[REQUIRED] TransactionLog1 saved successfully
[REQUIRED] TransactionLog2 saved successfully
```

Logs are prefixed with the propagation type in square brackets for easy filtering.