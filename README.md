# TMS - Simple Transaction Management System Demo

## Setup
```bash
mvn spring-boot:run
```

## API Endpoints

### Get All Transactions
```
GET /transactions
```

### Create Transaction
POST /transactions?amount={any_decimal_value}
```
POST /transactions?amount=100.50
```

## Notes
- Pending transactions auto-settle after 24 hours
- H2 console: http://localhost:8080/h2-console
