package se.lexicon.attendance_app.db;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "elnaz";

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }

    private static DataSource mysqlDataSource;

    // This method provides a DataSource, which is a connection provider.
    // Instead of creating a new connection every time, it allows connections to be managed and reused.
    public static DataSource getMysqlDataSource() {
        if (mysqlDataSource == null) {

            // We used MysqlDataSource which is non-pooled and no need to configure connection pooling.
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(URL);
            ds.setUser(USER);
            ds.setPassword(PASSWORD);
            mysqlDataSource = ds;
        }
        return mysqlDataSource;
    }
}
