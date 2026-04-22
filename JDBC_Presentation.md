![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# JDBC (Java Database Connectivity)

---

## Table of Contents

1. Introduction to JDBC
2. JDBC Drivers
3. JDBC URL
4. JDBC Interfaces
    - Connection Interface
    - Statement Interface
    - PreparedStatement Interface
    - ResultSet Interface
5. DataSource

---

## 1. Introduction to JDBC

**JDBC (Java Database Connectivity)** is a Java API that enables Java applications to communicate with relational
databases.

JDBC is a **specification**, meaning it defines a set of standard Java interfaces that allow Java programs to access and
manipulate data stored in a database.

JDBC acts as a **bridge** between a Java application and a database because Java programs and databases use **different
languages**.

- Java applications use **Java (object-oriented language)**
- Databases understand **SQL (Structured Query Language)**

JDBC converts Java method calls into SQL commands that the database can understand and converts database results back
into Java-readable form.

---

### How JDBC Works

1. The **Java application** calls JDBC methods such as `executeQuery()`
2. The **JDBC API** provides standard interfaces like `Connection`, `Statement`, and `PreparedStatement`
3. The **JDBC Driver** translates JDBC calls into database-specific SQL commands
4. The **Database** executes the SQL query
5. The result is returned to the Java application as a **ResultSet**

```
Java Application
      ↓
  JDBC API
      ↓
 JDBC Driver
      ↓
   Database
      ↑
  ResultSet
```

JDBC works like a **translator**:

- Java speaks Java
- Database speaks SQL
- JDBC translates between them

---

### Why JDBC Is Important

- Database-independent Java code
- Same Java program can work with different databases
- Secure and standardized communication
- Only the JDBC driver changes, not the Java code

---

## 2. JDBC Drivers

A **JDBC Driver** is a software component that enables a Java application to communicate with a database.

- Java programs use **JDBC API methods**
- Databases understand **database-specific protocols**
- The JDBC driver acts as a **translator**

Without a JDBC driver, Java applications **cannot connect** to a database.

---

### Role of JDBC Driver

The JDBC Driver:

- Converts JDBC method calls into database-specific commands
- Sends SQL queries to the database
- Receives results from the database
- Returns results to the Java application as a `ResultSet`

---

### Types of JDBC Drivers

JDBC defines **four types of drivers**, based on how they communicate with the database:

1. Type 1 – JDBC-ODBC Bridge Driver
2. Type 2 – Native API Driver
3. Type 3 – Network Protocol Driver
4. Type 4 – Thin Driver

---

### Which JDBC Driver Do We Use?

**Type 4 – Thin Driver**: A JDBC driver that communicates directly with the database.

- Written completely in Java
- Platform independent
- Best performance
- Used in real-world applications

**Examples:**

- MySQL → `mysql-connector-j`
- Oracle → `ojdbc`
- PostgreSQL → `postgresql`


---

### Steps to Use JDBC (Core Workflow)

1. Load JDBC Driver
2. Establish Database Connection
3. Create Statement / PreparedStatement
4. Execute SQL Query
5. Process ResultSet
6. Close JDBC Resources

---

## 3. JDBC URL

A **JDBC URL** is a **connection string** used to identify the database and provide the required connection details.

It tells Java:
- Which database to connect to
- Where the database is located
- Which JDBC driver to use

---

### JDBC URL Format

The general format of a JDBC URL is: `jdbc:<database_type>://<host>:<port>/<database_name>`

- `jdbc` → Protocol (always starts with `jdbc`)
- `<database_type>` → Database vendor (mysql, oracle, postgresql, etc.)
- `<host>` → Database server address (localhost or IP)
- `<port>` → Database port number
- `<database_name>` → Name of the database

Examples:

- **MySQL**: `jdbc:mysql://localhost:3306/my_database`
   - **localhost**: Database server address (can be an IP address or domain)
   - **3306**: Default MySQL port number
   - **my_database**: Name of the specific database to connect to

- **PostgreSQL**: `jdbc:postgresql://localhost:5432/studentdb`
   - **localhost**: Database server address (can be an IP address or domain)
   - **5432**: Default PostgreSQL port number
   - **studentdb**: Name of the specific database to connect to

---

### Adding Database Properties to JDBC URL

Database properties (like connection timeout, SSL settings, character encoding) can be added to the JDBC URL as **query
parameters**.

Format: `jdbc:<database_type>://<host>:<port>/<database_name>?property1=value1&property2=value2`

Examples with properties:

- **MySQL**: `jdbc:mysql://localhost:3306/my_database?useSSL=false&serverTimezone=UTC`
   - `useSSL=false`: Disables SSL connection
   - `serverTimezone=UTC`: Sets the server timezone

- **PostgreSQL**: `jdbc:postgresql://localhost:5432/studentdb?ssl=true&connectTimeout=10`
   - `ssl=true`: Enables SSL connection
   - `connectTimeout=10`: Sets connection timeout to 10 seconds



## 4. JDBC Interfaces
JDBC provides a set of **core interfaces** in the `java.sql` package that define how Java applications interact with databases.

- Establish a connection with the database
- Execute SQL queries
- Retrieve and process data

---
### 4.1 Connection Interface

The **Connection** interface represents an active **connection between a Java application and a database**.  
It is responsible for managing communication, transactions, and creating SQL execution objects.

---

#### Common Methods

- `createStatement()`  
  → Creates a `Statement` object used to execute **static SQL queries** (without parameters).

- `prepareStatement(String sql)`  
  → Creates a `PreparedStatement` object used to execute **parameterized SQL queries** with better performance and security.

- `setAutoCommit(boolean autoCommit)`  
  → Controls transaction behavior:
   - `true` (default): each SQL statement is committed automatically.
   - `false`: changes must be saved using `commit()` or undone using `rollback()`.

- `commit()`  
  → Saves all changes made during the current transaction.

- `rollback()`  
  → Cancels all changes made during the current transaction.

- `close()`  
  → Closes the connection and releases all database resources.

---

#### Example

```java
Connection connection = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/my_database",
    "DB_USER",
    "DB_PASSWORD"
);

// With additional connection properties
Connection connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/my_database?useSSL=false&serverTimezone=UTC",
        "DB_USER",
        "DB_PASSWORD"
);
```

---

### 4.2 Statement Interface

The **Statement** interface is used to execute **static SQL queries**, where the SQL statement is written directly in the code.

---

#### Common Methods

- `executeQuery(String sql)`  
  → Executes a `SELECT` query and returns a `ResultSet`.

- `executeUpdate(String sql)`  
  → Executes `INSERT`, `UPDATE`, or `DELETE` queries.

- `execute(String sql)`  
  → Executes any SQL statement.

- `executeBatch()`  
  → Executes a batch of SQL statements.

- `close()`  
  → Closes the statement and releases resources.

---

#### Example

```java
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery("SELECT * FROM student");
```

---

### 4.3 PreparedStatement Interface

The **PreparedStatement** interface is used to execute **parameterized SQL queries**.  
The query is precompiled, making it **faster and more secure**.

---

#### Advantages

- Prevents SQL Injection
- Better performance
- Cleaner code

---

#### Common Methods

- `setInt(int index, int value)`
- `setString(int index, String value)`
- `executeQuery()`
- `executeUpdate()`
- `close()`

---

#### Example

```java
String sql = "INSERT INTO student (id, name) VALUES (?, ?)";
PreparedStatement ps = connection.prepareStatement(sql);

ps.setInt(1, 101);
ps.setString(2, "Rahul");

ps.executeUpdate();
```

### 4.4 ResultSet Interface

The **ResultSet** interface represents the **data returned from a database after executing a `SELECT` query**.

It acts like a table where:
- Each row represents a record
- Each column represents a field in the database table

The cursor in a `ResultSet` initially points **before the first row**.

---

#### Common Methods

- `next()`  
  → Moves the cursor to the next row in the result set.  
  → Returns `true` if a row exists, otherwise `false`.

- `getInt(int columnIndex)` / `getInt(String columnName)`  
  → Retrieves integer data from the specified column.

- `getString(int columnIndex)` / `getString(String columnName)`  
  → Retrieves string data from the specified column.

- `getDouble(int columnIndex)` / `getDouble(String columnName)`  
  → Retrieves double data from the specified column.

- `getDate(int columnIndex)`  
  → Retrieves date values from the specified column.

- `close()`  
  → Releases ResultSet resources.

---

#### Example

```java
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery("SELECT * FROM student");

while (resultSet.next()) {
    int id = resultSet.getInt("id");
    String name = resultSet.getString("name");

    System.out.println(id + " " + name);
}
```

---

#### Retrieving Generated Keys

In many databases, primary keys are **auto-generated** (for example, AUTO_INCREMENT in MySQL).

JDBC allows us to retrieve these generated keys after executing an `INSERT` statement.

---

##### Steps to Get Generated Keys

1. Create `PreparedStatement` using `Statement.RETURN_GENERATED_KEYS`
2. Execute the `INSERT` query
3. Call `getGeneratedKeys()`
4. Read the generated key from `ResultSet`

---

##### Example

```java
String sql = "INSERT INTO student (name, age) VALUES (?, ?)";
PreparedStatement ps = connection.prepareStatement(
    sql,
    Statement.RETURN_GENERATED_KEYS
);

ps.setString(1, "Rahul");
ps.setInt(2, 22);

ps.executeUpdate();

ResultSet rs = ps.getGeneratedKeys();
if (rs.next()) {
    int generatedId = rs.getInt(1);
    System.out.println("Generated ID: " + generatedId);
}
```

## 5. DataSource

**DataSource** is a JDBC interface used to obtain database connections in a **more efficient and scalable way** than using `DriverManager`.

It is defined in the package:

```
javax.sql.DataSource
```

### Why DataSource is Used

- Avoids creating a new connection every time
- Supports **connection pooling**
- Improves performance
- Used in real-world and enterprise applications


### How to Use DataSource

Using `DataSource` involves **getting a database connection from a DataSource object instead of using DriverManager**.

The basic steps are:

---

#### Steps to Use DataSource

1. Configure the `DataSource` (URL, username, password, pool size)
2. Obtain a `Connection` from the `DataSource`
3. Use JDBC interfaces (`Statement`, `PreparedStatement`)
4. Close the connection (it returns to the pool, not closed permanently)

---

#### Example Using DataSource

```java
import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

MysqlDataSource dataSource = new MysqlDataSource();
dataSource.setURL("jdbc:mysql://localhost:3306/my_database");
dataSource.setUser("DB_USER");
dataSource.setPassword("DB_PASSWORD");

Connection connection = dataSource.getConnection();

PreparedStatement ps = connection.prepareStatement(
    "SELECT * FROM student"
);
ResultSet rs = ps.executeQuery();

while (rs.next()) {
    System.out.println(rs.getInt("id") + " " + rs.getString("name"));
}

connection.close(); // returned to pool
```

---

#### DataSource vs DriverManager

- `getConnection()` comes from `DataSource`, not `DriverManager`
- Closing the connection returns it to the pool
- Recommended for web and enterprise applications
- Commonly used with Spring, Tomcat, and application servers

---

Both `DriverManager` and `DataSource` are used to obtain a `Connection` object.
The difference is **how the connection is created and managed**, not the `Connection` itself.

- `DriverManager.getConnection()`  
  → Creates a **new database connection every time**

- `DataSource.getConnection()`  
  → Provides a **managed connection**, usually from a **connection pool**

The `Connection` interface remains the same in both cases.

> We use `DriverManager` for learning and simple programs,  
> and `DataSource` in real-world and enterprise applications.
