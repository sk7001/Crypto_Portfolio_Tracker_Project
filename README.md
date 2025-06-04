# 🪙 Crypto Portfolio Tracker (Spring Boot Backend)

A backend service to help users manage and monitor their cryptocurrency portfolios. Users can track holdings, monitor real-time/simulated crypto prices, and receive alerts based on price thresholds.

---

## 📌 Project Objective

Build a secure and efficient platform that enables users to:

- Track their crypto asset holdings.
- View current asset values.
- Set and manage price alerts.
- Simulate or fetch real-time crypto price updates.

---

## 🧩 Core Functional Features

### 1. 👤 User Management

- User Registration and Login.
- Secure password encryption (BCrypt).
- Basic role setup (e.g., USER, ADMIN).


---

### 2. 💼 Portfolio Management

- Users can manage their portfolio with crypto assets.
- Each asset includes:
  - `coinName`, `symbol`, `quantityHeld`, `buyPrice`, `buyDate`.

**Supported Actions:**

- Add crypto asset to portfolio.
- View user's portfolio.
- Update quantity or buy price.
- Delete an asset.

---

### 3. 📈 Price Tracking & Valuation

- Simulates prices for popular coins like BTC, ETH, SOL, ADA.
- Calculates:
  - `currentValue = quantityHeld * currentPrice`
  - `PnL (Profit/Loss) = currentValue - (quantityHeld * buyPrice)`

> Optionally, you may integrate a third-party API like CoinGecko.

---

### 4. 🚨 Alerts Management

- Users can create alerts per coin (e.g., Alert when BTC < $30,000).
- A scheduler runs in the background every 30s–1min.
- Alerts are stored in the database when triggered.

---

## 🗃️ Database Entities

| Entity          | Key Fields                                                                 |
|-----------------|------------------------------------------------------------------------------|
| `User`          | `id`, `name`, `email`, `password`, `roles`                                  |
| `CryptoHolding` | `id`, `userId`, `coinName`, `symbol`, `quantityHeld`, `buyPrice`, `buyDate` |
| `CryptoPrice`   | `symbol`, `currentPrice`, `timestamp`                                       |
| `Alert`         | `id`, `userId`, `symbol`, `triggerPrice`, `direction`, `status`, `triggeredAt`|

---

## 🔁 API Overview

### 🧾 Authentication

- `POST /auth/register` – Register new user  
- `POST /auth/login` – Login existing user  

---

### 📦 Portfolio Management

- `POST /portfolio/add` – Add asset  
- `GET /portfolio/my` – Get user's holdings  
- `PUT /portfolio/update/{id}` – Update holding  
- `DELETE /portfolio/delete/{id}` – Delete holding  

---

### 📉 Price Simulation

- `GET /prices` – Get latest crypto prices  
- Internal scheduler updates coin prices periodically  

---

### 📢 Alerts

- `POST /alerts/create` – Set price alert  
- `GET /alerts/my` – View user alerts  
- `GET /alerts/triggered` – View triggered alerts  

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL Database
- Scheduled Tasks (Price Updates)
- BCrypt (Password Encryption)

---

## 🚀 Future Enhancements

- Integration with real-time APIs (e.g., CoinGecko).
- Email notifications for triggered alerts.
- Enhanced dashboard and analytics.
- Mobile or React frontend integration.

## 👥 Authors & Contributors

See [GitHub contributors list](../../contributors) for full details.

- [Swetha](https://github.com/SwethaJayasankar) – User Register and Login 
- [Surya](https://github.com/KillerVardhan8) – Crypto Holdings  
- [Abhinaya](https://github.com/Konduru12) – Crypto Price Simulation  
- [Shahid](https://github.com/Ss0556) – Crypto Profit and Loss  
- [Shobit](https://github.com/sk7001) – Crypto Price Alerts  
- [KumarVardhan](https://github.com/kumarvardhan-0) – Crypto Portfolio Loss  
- [Sukesh](https://github.com/Sukesh03) – Global Exceptions  

Thanks to all team members and contributors for their collaboration.

