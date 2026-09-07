# SMChemicals-System

## Integrated Business Automation System for S M CHEMICALS

A Java desktop application developed using Java, NetBeans, MySQL, JDBC and MVC Architecture.

## Requirements

Install:
- Java JDK
- NetBeans IDE
- MySQL Server
- MySQL Workbench
- MySQL Connector/J (if it is not already included in the project)

## 1. Download the Project

### Download ZIP
1. Open the GitHub repository.
2. Click **Code**.
3. Select **Download ZIP**.
4. Extract the ZIP file.

### Clone with Git
```bash
git clone https://github.com/pasindurjana28-dot/SMChemicals-System.git
```

## 2. Set Up the Database

The `database` folder contains the SQL files for the `smchemicalsdb` database.

1. Open MySQL Workbench.
2. Create the database:
```sql
CREATE DATABASE smchemicalsdb;
```
3. Select the `smchemicalsdb` schema.
4. Open and execute the SQL files from the `database` folder.
5. Check that all tables and data were created successfully.

Tables:
- users
- customers
- products
- inventory
- suppliers
- purchases
- purchase_items
- sales
- sale_items
- invoices
- payments

## 3. Configure the Database Connection

The database configuration file is not included in GitHub because it contains the local MySQL password.

Create `db.properties` inside the project's `src` folder:

```properties
db.url=jdbc:mysql://localhost:3306/smchemicalsdb
db.user=root
db.password=YOUR_MYSQL_PASSWORD
```

Replace `YOUR_MYSQL_PASSWORD` with the MySQL password on that computer.

**Never upload your real MySQL password to GitHub.**

## 4. Open the Project in NetBeans

1. Open NetBeans.
2. Select **File → Open Project**.
3. Select the `SMChemicalsSystem` folder.
4. Open the project.
5. Check that MySQL Connector/J is available.
6. Build the project.

## 5. Run the Application

Run the project from NetBeans.

The application starts with the Administrator Login. Use the Administrator account available in the local `users` table.

## Important Notes

- MySQL Server must be running before starting the application.
- Each person should use their own local MySQL password in `db.properties`.
- Do not commit `db.properties` to GitHub.
- If the application cannot connect to MySQL, check the database name, username, password, port and JDBC driver.

## Main Modules

- Administrator Login
- Dashboard
- Customer Management
- Product Management
- Inventory Management
- Supplier Management
- Purchase Management
- Sales Management
- Billing & Invoice
- Payment Management
- Reports

## Technologies

- Java
- Java Swing
- NetBeans
- MySQL
- JDBC
- MVC Architecture

## Repository

GitHub: https://github.com/pasindurjana28-dot/SMChemicals-System
