# 🏨 Hotel Management System

A **Java-based Hotel Management System** developed to manage hotel and cafe operations using structured programming techniques.  
The project integrates **PostgreSQL** for database management, applies **Data Structures & Algorithms (DSA)** for data handling, and follows **Object-Oriented Programming (OOP)** principles.

The system manages guest records, room allocation, cafe menu operations, food ordering, and billing with persistent database storage.

---

## 📌 Project Objectives

- Manage hotel and cafe operations using Java  
- Apply Data Structures & Algorithms for sorting and searching data  
- Store and retrieve hotel and cafe data using PostgreSQL  

---

## 🚀 Project Features

### 🏨 Hotel Features
- Guest registration and record management  
- Room allocation and availability handling  
- Check-in and check-out process  

### ☕ Cafe Features
- Display food menu  
- Sort menu items by price  
- Search food items by name  
- Place food orders  
- Generate order bills  

### 🗄️ Database Features
- Persistent storage using PostgreSQL  
- Relational database design  
- SQL-based data operations  

---

## 🛠️ Technology Stack

| Technology | Description |
|---------|-------------|
| Java | Core application development |
| PostgreSQL | Database management |
| XAMPP | Database environment |
| DBMS | Data storage & relationships |
| DSA | Sorting and searching operations |
| JDBC | Java–Database connectivity |

---

## 🧠 Data Structures & Algorithms (DSA)

### Data Structures Used
- **ArrayList**
  - Stores cafe menu items
  - Stores customer food orders

### Algorithms Used
- Sorting menu items by price  
- Searching food items by name  
- Iteration and aggregation for billing  

### Cafe Menu Operations
- Show Menu
- Show Menu Sorted by Price
- Search Item by Name
- Order Food

---

## 🧩 Object-Oriented Programming Concepts

- Encapsulation  
- Abstraction  
- Modularity  
- Reusability  

---

## 🗄️ Database Design (SQL)

### Database Used
- **PostgreSQL**

### SQL File
- `hotel.sql`

### Tables Store
- Guest information  
- Room and booking data  
- Cafe order details  
- Billing records  

### SQL Concepts Used
- `CREATE TABLE`
- `INSERT INTO`
- Primary key constraints
- Relational table structure
- Procedures
- Routines
The Java application connects to the database using **JDBC**.

---

## 📂 Project File Structure

```bash
Hotel-Management-System/
│
├── src/
│   ├── UIHotel.java             # Main application controller
│   ├── Guesthouse.java          # Guest and room management
│   ├── Cafe.java                # Cafe module logic
│   ├── cafeordersystem.java     # Menu display, ordering & billing
│   └── OrderItem1.java          # Food item data structure
│
├── sql/
│   └── hotel.sql                # PostgreSQL database schema
│
├── README.md
└── .gitignore
