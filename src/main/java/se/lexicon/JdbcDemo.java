package se.lexicon;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JdbcDemo {

    // PostgreSQL connection details
    private static final String URL = "jdbc:postgresql://localhost:5432/student_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "elnaz";

    static void main() {
        ex2();
    }

    public static void ex1() {
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            System.out.println("✅ Database connection established successfully!");

            // PostgreSQL uses same SELECT syntax
            String query = "SELECT id, name, class_group, create_date FROM student";
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("📌 Student Records: ");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String classGroup = resultSet.getString("class_group");

                // PostgreSQL returns timestamp the same way
                LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();

                String formattedDate = createDate.format(DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy"));

                System.out.println("ID: " + id + " | Name: " + name + " | Class: " + classGroup + " | Created At: " + createDate);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error connecting to the database: " + e.getMessage());
        }
    }

    public static void ex2() {
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT id, name, class_group, create_date FROM student WHERE class_group LIKE ?"
                );
        ) {
            System.out.println("✅ Database connection established successfully!");

            String classGroupParam = "G1";
            preparedStatement.setString(1, classGroupParam);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("📌 Student Records in Class Group: " + classGroupParam);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String classGroup = resultSet.getString("class_group");
                    LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();

                    System.out.println("ID: " + id + " | Name: " + name + " | Class: " + classGroup + " | Created At: " + createDate);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error connecting to the database: " + e.getMessage());
        }
    }
}