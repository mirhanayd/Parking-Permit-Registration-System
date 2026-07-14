# Parking Permit Registration System

A Java Swing desktop application for managing student parking permits. The app supports user registration, login, and permit management backed by a MySQL database.

## Features

- User registration with student email, passport number, name, surname, and password
- Login with student ID and password
- Parking permit creation for the logged-in user
- Permit listing in a table view
- Permit deletion by plate number
- MySQL-backed persistence

## Tech Stack

- Java
- Swing / JDialog
- MySQL
- JDBC
- IntelliJ IDEA GUI Designer forms

## Project Structure

- `src/loginf.java` - login screen
- `src/registirationf.java` - registration screen
- `src/parkpermitf.java` - parking permit management screen
- `lib/mysql-connector-j-9.1.0.jar` - MySQL JDBC driver

## Requirements

- Java 8 or newer
- MySQL Server
- IntelliJ IDEA recommended for the `.form` UI files

## Database Setup

The application expects a MySQL database named `parking_permit` running on `127.0.0.1:3306`.

Default credentials used in the code:

- Username: `root`
- Password: `986532`

Create the database and tables with something like this:

```sql
CREATE DATABASE parking_permit;
USE parking_permit;

CREATE TABLE users (
    id VARCHAR(255) PRIMARY KEY,
    p_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    passw VARCHAR(255) NOT NULL
);

CREATE TABLE parkpermit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    s_id VARCHAR(255) NOT NULL,
    plateno VARCHAR(255) NOT NULL,
    areas VARCHAR(255) NOT NULL,
    semester VARCHAR(255) NOT NULL,
    FOREIGN KEY (s_id) REFERENCES users(id)
);
```

If your database user, password, host, or port is different, update the connection string in the Java files.

## How to Run

### In IntelliJ IDEA

1. Open the project folder in IntelliJ IDEA.
2. Make sure `lib/mysql-connector-j-9.1.0.jar` is added to the project classpath.
3. Ensure the MySQL database is running and the schema above is created.
4. Run `src/loginf.java`.

### From the Command Line

1. Compile the sources with the MySQL driver on the classpath.
2. Run the `loginf` class.

Example on Windows:

```powershell
javac -cp ".;lib/mysql-connector-j-9.1.0.jar" src\*.java
java -cp ".;lib/mysql-connector-j-9.1.0.jar;src" loginf
```

## Notes

- The registration form inserts new users into the `users` table.
- After login, the permit screen loads the current user's info and permits.
- The current code stores the database password directly in the source files, so consider moving it to a config file for safer usage.
