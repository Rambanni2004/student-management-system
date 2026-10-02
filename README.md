# Student Management System

A console-based CRUD application built with **Java**, **JDBC** and **MySQL**, using the DAO pattern.

## Features
- Add, view, update and delete student records
- Search students by ID or name
- Duplicate email protection (UNIQUE constraint)
- SQL-injection safe queries using `PreparedStatement`
- DB credentials kept out of source code via `db.properties`

## Tech Stack
Java 17+, JDBC, MySQL 8, MySQL Connector/J

## Project Structure
```
src/com/sms/
  Main.java              -> menu-driven UI
  model/Student.java     -> entity class
  dao/StudentDAO.java    -> all database operations
  util/DBConnection.java -> connection handling
sql/schema.sql           -> database + table script
```

## Setup
1. Install Java 17+ and MySQL.
2. Run the script: `mysql -u root -p < sql/schema.sql`
3. Download **mysql-connector-j** jar and put it in the `lib/` folder.
4. Copy `db.properties.example` to `db.properties` and enter your MySQL password.
5. Compile and run:

**Linux / Mac**
```bash
javac -cp "lib/*" -d out $(find src -name "*.java")
java -cp "out:lib/*" com.sms.Main
```

**Windows (PowerShell)**
```powershell
javac -cp "lib/*" -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp "out;lib/*" com.sms.Main
```

## Future Improvements
- Login with admin roles
- Swing / JavaFX GUI or Spring Boot REST API
- Pagination and export to CSV
