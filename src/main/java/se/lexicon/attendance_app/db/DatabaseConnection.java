package se.lexicon.attendance_app.db;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DatabaseConnection {

    // PostgreSQL URL format: jdbc:postgresql://host:port/database
    private static final String URL = "jdbc:postgresql://localhost:5432/student_db";
    private static final String USER = "smasobur";      // Default PostgreSQL user
    private static final String PASSWORD = "";     // Your PostgreSQL password

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }

    private static DataSource pgDataSource;

    // This method provides a DataSource for PostgreSQL
    public static DataSource getMysqlDataSource() {  // Keeping method name for compatibility
        if (pgDataSource == null) {

            // PGSimpleDataSource is PostgreSQL's non-pooled DataSource
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setURL(URL);
            ds.setUser(USER);
            ds.setPassword(PASSWORD);

            pgDataSource = ds;
        }
        return pgDataSource;
    }

    // Optional: Better named method (you can use this instead)
    public static DataSource getDataSource() {
        return getMysqlDataSource();
    }
}