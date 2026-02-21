# Multi Currency Wallet API

A multi-currency wallet REST API built with Spring Boot 4, designed to manage user accounts, handle currency conversions, and process international transactions.

## Features

- **User Authentication**: Secure registration and login with JWT token-based authentication
- **Multi-Currency Wallets**: Create and manage wallets in supported currencies (USD, GBP, INR, AUD)
- **Currency Exchange**: Real-time exchange rates via ExchangeRate API integration with in-memory caching
- **Transaction Management**: Track deposits and cross-currency transfers with full transaction history + pagination
- **Secure Transfers**: Transfer funds between wallets with cross-currency conversion and proper authorization
- **Input Validation**: Comprehensive request validation using Jakarta Validation
- **Spring Security**: JWT-secured stateless endpoints with BCrypt password hashing
- **Custom Exception Handling**: Global exception handler with structured error responses

## API Endpoints

### Authentication

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT |

**Register request body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secure_password"
}
```

**Login request body:**
```json
{
  "email": "john@example.com",
  "password": "secure_password"
}
```

**Auth response:**
```json
{
  "token": "<jwt_token>",
  "name": "John Doe",
  "email": "john@example.com",
  "id": 1
}
```

---

### Wallet Management

> All wallet endpoints require `Authorization: Bearer <token>` header.

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/wallets/create` | Create a new wallet |
| GET | `/api/wallets` | Get all wallets for authenticated user |
| GET | `/api/wallets/{id}` | Get a specific wallet by ID |
| POST | `/api/wallets/deposit` | Deposit funds into a wallet |

**Create wallet request:**
```json
{ "currency": "USD" }
```

**Deposit request:**
```json
{
  "walletId": 1,
  "amount": 500.00
}
```

---

### Transfers

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/transfers` | Transfer funds between wallets |

**Transfer request body:**
```json
{
  "fromWalletId": 1,
  "recipentEmail": "recipient@example.com",
  "amount": 100.00,
  "currency": "GBP"
}
```

> If `currency` is omitted, the transfer uses the source wallet's currency. Cross-currency transfers are automatically converted using live exchange rates.

---

### FX Rates

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/fx-rates/current?base=USD` | Get all rates for a base currency |
| GET | `/api/fx-rates/convert?from=USD&to=GBP&amount=100` | Convert a specific amount |

---

### Transactions

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/transactions` | Get paginated transaction history |

**Query parameters:**
- `page` (default: `0`)
- `size` (default: `20`)
- `sort` (default: `timestamp,desc`)

---