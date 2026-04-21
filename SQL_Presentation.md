![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Introduction to Databases and SQL

---

## Table of Contents

1. [Introduction to Databases](#1-introduction-to-databases)
2. [Relational Databases (RDBMS)](#2-relational-databases-rdbms)
3. [Introduction to SQL](#3-introduction-to-sql)
4. [SQL Categories](#4-sql-categories)
5. [Basic SQL Syntax & Examples](#5-basic-sql-syntax--examples)
6. [Modifying Table Structure (ALTER TABLE)](#6-modifying-table-structure-alter-table)
7. [Database Relationships](#7-database-relationships)
8. [Database Constraints](#8-database-constraints)

---

## 1. Introduction to Databases

A **database** is an organized collection of data stored in a computer system, designed to make information easy to
store, access, update, and manage. Databases are used almost everywhere, such as in schools, hospitals, banks, social
media, and online shopping. Whenever an application makes changes—like updating a profile, placing an order, or
recording a payment—those changes are stored in the database so the system stays updated and synchronized. For example,
when you log into an app, your personal details are saved in a database, and when you search for something online, the
database helps retrieve the information quickly. Overall, databases help keep data accurate, well-organized, and easy to
use.

### Why do we need Databases?

- **Persistence**: Data remains saved even after the application is closed or the computer is restarted.
- **Large Volumes**: Databases can store and manage huge amounts of information efficiently.
- **Security**: They allow controlled access, so only authorized users can view or change data.
- **Concurrency**: Multiple users can work with the same data at the same time without conflicts.

### Types of Databases

Databases come in different types depending on how data is stored and used. Each type is designed for specific needs
such as speed, flexibility, scalability, and performance.

- **Relational Databases (SQL Databases)**  
  These store data in the form of **tables** (rows and columns) and use **SQL** to query and manage data.  
  **Example:** MySQL, PostgreSQL, Oracle, SQL Server  
  **Best for:** Banking systems, school records, business applications.

- **NoSQL Databases**  
  These are designed for flexible and fast data storage. NoSQL databases do not always use tables, and they work well
  with large and unstructured data.  
  **Example:** MongoDB, Cassandra  
  **Best for:** Social media, real-time apps, big data systems.

- **Key-Value Databases**  
  These store data as **key–value pairs**, which makes them extremely fast for simple queries.  
  **Example:** Redis, Amazon DynamoDB  
  **Best for:** Caching, session storage, quick lookups.

- **Graph Databases**  
  These store data using **nodes** and **relationships**, making them ideal for connected data.  
  **Example:** Neo4j, Amazon Neptune  
  **Best for:** Social networks, recommendation systems, fraud detection.

- **Time-Series Databases**  
  These are optimized to store and analyze data that changes over time (data with timestamps).  
  **Example:** InfluxDB, TimescaleDB, Prometheus  
  **Best for:** Monitoring systems, IoT sensors, stock market data.

### DBMS (Database Management System)

A **DBMS** is the software that interacts with end users, applications, and the database itself to capture and analyze
data.
Examples: MySQL, PostgreSQL, Oracle, Microsoft SQL Server, MongoDB.

> **Mainly focus on MySQL** as our database system to learn and practice relational database concepts and SQL.

---

## 2. Relational Databases (RDBMS)

A **Relational Database** stores data in the form of **tables** (rows and columns).  
To manage these relational databases, we use an **RDBMS (Relational Database Management System)** such as **MySQL**.

An **RDBMS** is software that allows us to:

- create and manage tables
- store and update data
- connect related tables
- use **SQL** to query the database

### Basic Terms in Relational Databases

- **Table**: A collection of related data (e.g., `students` table).
- **Row (Record)**: One complete entry in a table (one student).
- **Column (Field)**: A specific type of information in the table (e.g., `name`, `age`).

### Example: `students` Table

| student_id | name        | age | department |
|-----------:|-------------|----:|------------|
|        101 | Ahmed Ali   |  20 | CS         |
|        102 | Sara Noor   |  21 | IT         |
|        103 | Omar Hassan |  19 | SE         |

### Relationship Between Programming and Databases

In programming (like **OOP**), we often use **classes** and **objects**.  
In databases, we use **tables** and **rows**. They are related like this:

| Programming (OOP)        | Database (RDBMS)   |
|--------------------------|--------------------|
| **Class**                | **Table**          |
| **Object**               | **Row (Record)**   |
| **Attribute / Property** | **Column (Field)** |

---

## 3. Introduction to SQL

**SQL (Structured Query Language)** is the standard language for dealing with Relational Databases.

SQL is used to:

- Insert, update, and delete records.
- Query (retrieve) data.
- Create and modify the structure of the database (tables, indexes, etc.).

---

## 4. SQL Categories

SQL commands are mainly categorized into:

### DDL (Data Definition Language)

Used to define the database structure or schema.

- `CREATE`: To create objects in the database.
- `ALTER`: Alters the structure of the database.
- `DROP`: Delete objects from the database.
- `TRUNCATE`: Remove all records from a table.

### DML (Data Manipulation Language)

Used for managing data within schema objects.

- `INSERT`: Insert data into a table.
- `UPDATE`: Updates existing data within a table.
- `DELETE`: Delete records from a table.

### DQL (Data Query Language)

Used to fetch data from the database.

- `SELECT`: Retrieve data from the database.

---

## 5. Basic SQL Syntax & Examples

### Creating a Database and Table

```sql
CREATE DATABASE student_db;
USE student_db;

CREATE TABLE student
(
    id          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    class_group VARCHAR(50)  NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Primary Key (PK)

A **Primary Key (PK)** is a column (or a group of columns) that **uniquely identifies each row** in a table.

A Primary Key must be:
- **Unique** (no duplicates)
- **NOT NULL** (cannot be empty)

#### Why is a Primary Key important?
We need a primary key in every table because it:
- **uniquely identifies records** (so we can find one row easily)
- helps with **updating and deleting** the correct row safely
- improves performance when searching or joining data
- is required to create relationships using **Foreign Keys** in other tables

#### Example
In the `student` table, `id` is the primary key:

- Student 1 → `id = 1`
- Student 2 → `id = 2`

Even if two students have the same name, their `id` will always be different.


### Column Data Types (MySQL)

When creating a table, every **column must have a data type**.  
A **data type** defines what kind of values a column can store (numbers, text, dates, etc.).

#### Common MySQL Data Types
- **INT**: Whole numbers (e.g., `1`, `25`, `100`)
- **VARCHAR(n)**: Text with variable length (e.g., names). `n` is the maximum characters.
- **CHAR(n)**: Fixed-length text (useful for short codes)
- **DATE**: Stores only a date (YYYY-MM-DD)
- **DATETIME**: Stores date and time (YYYY-MM-DD HH:MM:SS)
- **TIMESTAMP**: Stores date/time (often used for created/updated time)
- **FLOAT / DOUBLE**: Decimal numbers (e.g., `12.5`)
- **BOOLEAN**: True/False values (often stored as `0` or `1`)
- **ENUM**: A list of allowed values (e.g., `ENUM('Present','Absent')`)

> Choosing the correct data type helps save storage, improve performance, and keep data accurate.

### Inserting Data (DML)

```sql
INSERT INTO student (name, class_group)
             VALUES ('Alice Johnson', 'G1'),
                    ('Bob Smith', 'G1');
```

### Querying Data (DQL)

```sql
-- Select all columns
SELECT * FROM student;

-- Select specific columns with a condition
SELECT name, class_group FROM student WHERE class_group = 'G1';
```

#### Advanced Filtering
- **AND / OR**: Combine multiple conditions.
- **LIKE**: Search for a pattern (use `%` as a wildcard).
- **BETWEEN**: Filter within a range.
- **IN**: Filter against a list of values.

```sql
-- Using AND and LIKE
SELECT * FROM student WHERE class_group = 'G1' AND name LIKE 'A%';

-- Using OR and IN
SELECT * FROM student WHERE class_group = 'G1' OR class_group = 'G2';
SELECT * FROM student WHERE class_group IN ('G1', 'G2');

-- Using BETWEEN
SELECT * FROM student WHERE id BETWEEN 1 AND 10;
```

### Updating and Deleting Data (DML)

```sql
UPDATE student SET class_group = 'G2' WHERE id = 1;

DELETE FROM student WHERE id = 2;
```

---

## 6. Modifying Table Structure (ALTER TABLE)

Sometimes we need to change the structure of a table after it has been created. We use the `ALTER TABLE` command (part of **DDL**).

### Common Alter Operations

#### 1. Adding a New Column
```sql
ALTER TABLE student ADD email VARCHAR(255) UNIQUE;
```

#### 2. Modifying an Existing Column
Change the data type or length of a column.
```sql
ALTER TABLE student MODIFY name VARCHAR(150) NOT NULL;
```

#### 3. Renaming a Column
```sql
ALTER TABLE student CHANGE class_group group_name VARCHAR(50);
```

#### 4. Dropping (Removing) a Column
```sql
ALTER TABLE student DROP COLUMN email;
```

---

## 7. Database Relationships

Before using Foreign Keys, we need to understand how tables relate to each other. These relationships are very similar to relationships between objects in **Object-Oriented Programming (OOP)**.

### 1. One-to-One (1:1)
- **Database**: A row in Table A is linked to exactly one row in Table B.
- **OOP Example**: A `User` object has one `UserProfile` object.
- **Implementation**: The Foreign Key is usually placed in one of the tables and marked as `UNIQUE`.

### 2. One-to-Many (1:M)
- **Database**: A row in Table A can be linked to multiple rows in Table B, but a row in Table B is linked to only one row in Table A.
- **OOP Example**: A `Department` object has a list of `Employee` objects.
- **Implementation**: The "Many" side (e.g., `Employee`) contains a Foreign Key pointing to the "One" side (e.g., `Department`). This is the **most common** relationship.

### 3. Many-to-Many (M:M)
- **Database**: Multiple rows in Table A can be linked to multiple rows in Table B.
- **OOP Example**: A `Student` object can be enrolled in multiple `Course` objects, and a `Course` can have multiple `Student` objects.
- **Implementation**: Requires a **Join Table** (or Associative Table) that contains Foreign Keys for both Table A and Table B.

---

## 8. Database Constraints

Constraints are rules applied to columns to ensure data integrity.

- **NOT NULL**: Ensures that a column cannot have a NULL value.
- **UNIQUE**: Ensures that all values in a column are different.
- **PRIMARY KEY**: A combination of a `NOT NULL` and `UNIQUE`. Uniquely identifies each row in a table.
- **FOREIGN KEY**: Prevents actions that would destroy links between tables.
- **DEFAULT**: Sets a default value for a column if no value is specified.

### Example of Foreign Key (One-to-Many)

This example shows a **One-to-Many** relationship: One `student` can have many `attendance` records.

```sql
CREATE TABLE attendance
(
    id              INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id      INT  NOT NULL,
    attendance_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    status          ENUM('Present', 'Absent') NOT NULL,

    FOREIGN KEY (student_id) REFERENCES student (id),
    UNIQUE (student_id, attendance_date)
);
```

> **Note on Unique Constraints:** The `UNIQUE (student_id, attendance_date)` constraint ensures that a student cannot have more than one attendance record for the same day. When inserting multiple records for a student, different dates must be provided.

---

### Resources
- [W3Schools SQL Tutorial](https://www.w3schools.com/sql/)
