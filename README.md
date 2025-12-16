<div align="center">

# 💳 TMS - Transaction Management System

**A lightweight Spring Boot application for managing financial transactions with automated settlement**

[![Java](https://img.shields.io/badge/Java-24-orange?style=flat&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen?style=flat&logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.x-blue?style=flat&logo=apache-maven)](https://maven.apache.org/)
[![H2 Database](https://img.shields.io/badge/H2-In--Memory-blue?style=flat)](https://www.h2database.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat)](LICENSE)

</div>

---

## 📋 Overview

**TMS (Transaction Management System)** is a RESTful Spring Boot application designed to demonstrate transaction lifecycle management with automated settlement capabilities. The system creates and tracks financial transactions, automatically settling pending transactions that exceed a 24-hour threshold through a scheduled background process.

### 🎯 Why TMS?

- **Educational Purpose**: Perfect for learning Spring Boot, JPA, and scheduled tasks
- **Production Patterns**: Demonstrates real-world transaction processing patterns
- **Automated Settlement**: Shows how to implement time-based business logic with schedulers
- **Lightweight**: Uses in-memory H2 database for quick setup and testing

---

## ✨ Features

- 🔄 **Create Transactions** - Generate new transactions with custom amounts
- 📊 **View All Transactions** - Retrieve complete transaction history
- ⏰ **Auto-Settlement** - Pending transactions automatically complete after 24 hours
- 🔍 **H2 Console** - Built-in database console for debugging and monitoring
- 🚀 **RESTful API** - Clean and intuitive HTTP endpoints
- 💾 **In-Memory Storage** - Fast H2 database for development and testing

---

## 🛠️ Tech Stack

| Technology | Purpose |
|-----------|---------|
| **Java 24** | Core programming language |
| **Spring Boot 3.5.4** | Application framework |
| **Spring Data JPA** | Database abstraction layer |
| **H2 Database** | In-memory database |
| **Lombok** | Reduces boilerplate code |
| **Maven** | Build and dependency management |
| **Spring Scheduler** | Automated task execution |

---

## 🚀 Quick Start

### Prerequisites

- Java 24 or higher
- Maven 3.x (or use included Maven wrapper)
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/AkshayKappala/tms.git
cd tms
```

2. **Run the application**
```bash
./mvnw spring-boot:run
```

Or on Windows:
```cmd
mvnw. cmd spring-boot:run
```

3. **Verify it's running**
```bash
curl http://localhost:8080/transactions
```

The application will start on `http://localhost:8080`

---

## 📡 API Reference

### Get All Transactions

Retrieve all transactions from the system. 

```http
GET /transactions
```

**Example Request:**
```bash
curl -X GET http://localhost:8080/transactions
```

**Example Response:**
```json
[
  {
    "id":  1,
    "amount":  100.50,
    "status": "PENDING",
    "createdAt": "2025-12-16T10:30:00",
    "updatedAt": "2025-12-16T10:30:00"
  },
  {
    "id": 2,
    "amount": 250.75,
    "status":  "COMPLETED",
    "createdAt": "2025-12-15T08:15:00",
    "updatedAt": "2025-12-16T08:15:00"
  }
]
```

### Create Transaction

Create a new transaction with a specified amount.

```http
POST /transactions? amount={decimal_value}
```

**Parameters:**
- `amount` (required): Decimal value representing the transaction amount

**Example Request:**
```bash
curl -X POST "http://localhost:8080/transactions?amount=100.50"
```

**Example Response:**
```json
{
  "id": 3,
  "amount": 100.50,
  "status": "PENDING",
  "createdAt": "2025-12-16T14:45:00",
  "updatedAt": "2025-12-16T14:45:00"
}
```

---

## 📂 Project Structure

```
tms/
├── src/
│   ├── main/
│   │   ├── java/com/kappala/tms/
│   │   │   ├── controller/
│   │   │   │   └── TransactionController.java       # REST endpoints
│   │   │   ├── persistence/
│   │   │   │   ├── model/
│   │   │   │   │   ├── Transaction.java            # Transaction entity
│   │   │   │   │   └── TransactionState.java       # PENDING/COMPLETED enum
│   │   │   │   └── repository/
│   │   │   │       └── ITransactionRepository.java # JPA repository
│   │   │   ├── scheduler/
│   │   │   │   └── SettlementScheduler.java        # Auto-settlement job
│   │   │   ├── service/
│   │   │   │   ├── ITransactionService.java        # Service interface
│   │   │   │   └── impl/
│   │   │   │       └── TransactionService.java     # Business logic
│   │   │   └── TmsApplication.java                 # Application entry point
│   │   └── resources/
│   │       └── application.properties               # Configuration
│   └── test/
│       └── java/com/kappala/tms/
│           └── TmsApplicationTests.java
├── pom.xml
└── README.md
```

---

## ⚙️ Configuration

The application is configured via `src/main/resources/application.properties`:

```properties
# Application Name
spring.application.name=tms

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring. datasource.driverClassName=org.h2.Driver
spring. datasource.username=sa
spring.datasource.password=

# Enable H2 Console
spring.h2.console.enabled=true

# JPA Configuration
spring.jpa.hibernate. ddl-auto=update
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

### Key Configuration Options

| Property | Default | Description |
|----------|---------|-------------|
| `server.port` | 8080 | Application port (add if needed) |
| `spring.h2.console.enabled` | true | Enable/disable H2 console |
| `spring.jpa.hibernate.ddl-auto` | update | Database schema management |

---

## 🔧 Customization Guide

### Modify Settlement Time Window

The default settlement period is 24 hours. To change this, edit `TransactionService.java`:

```java
@Override
@Transactional
public void settleOldTransactions() {
    // Change minusHours(24) to your desired time window
    LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
    List<Transaction> old = transactionRepository
        .findByStatusAndCreatedAtBefore(TransactionState. PENDING, cutoff);
    
    for (Transaction tx : old) {
        tx.setStatus(TransactionState. COMPLETED);
        tx.setUpdatedAt(LocalDateTime.now());
    }
    transactionRepository.saveAll(old);
}
```

### Adjust Scheduler Frequency

The scheduler runs every 60 seconds by default. To modify, edit `SettlementScheduler.java`:

```java
@Component
public class SettlementScheduler {
    @Autowired
    private ITransactionService transactionService;

    // Change fixedRate value (in milliseconds)
    @Scheduled(fixedRate = 60000) // 60 seconds = 60000ms
    public void run() {
        transactionService. settleOldTransactions();
    }
}
```

### Add Custom Transaction States

Extend `TransactionState.java` to add more states:

```java
public enum TransactionState {
    PENDING,
    COMPLETED,
    FAILED,      // Add new state
    CANCELLED    // Add new state
}
```

---

## 🗄️ Database Access

### H2 Console

Access the H2 database console for real-time data inspection:

**URL:** http://localhost:8080/h2-console

**Connection Details:**
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(leave empty)*

---

## 🚢 Deployment

### Build JAR File

```bash
./mvnw clean package
```

The executable JAR will be created at `target/tms-0.0.1-SNAPSHOT.jar`

### Run JAR File

```bash
java -jar target/tms-0.0.1-SNAPSHOT.jar
```

### Docker Deployment (Optional)

Create a `Dockerfile`:

```dockerfile
FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app
COPY target/tms-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run: 

```bash
docker build -t tms-app .
docker run -p 8080:8080 tms-app
```

---

## 🧪 Testing

Run the test suite:

```bash
./mvnw test
```

Run with coverage:

```bash
./mvnw test jacoco:report
```

---

## 🐛 Troubleshooting

### Port Already in Use

If port 8080 is occupied, specify a different port:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Java Version Mismatch

Ensure Java 24 is installed:

```bash
java -version
```

If using a different Java version, update `pom.xml`:

```xml
<properties>
    <java.version>21</java.version> <!-- Change to your version -->
</properties>
```

### Database Connection Issues

If H2 console fails to connect, verify `application.properties`:
- Ensure `spring.h2.console.enabled=true`
- Check JDBC URL matches:  `jdbc:h2:mem:testdb`

---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👤 Contact

**Akshay Kappala**

- GitHub: [@AkshayKappala](https://github.com/AkshayKappala)
- Repository: [TMS](https://github.com/AkshayKappala/tms)

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ☕ and Spring Boot

</div>
