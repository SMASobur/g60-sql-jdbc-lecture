package se.lexicon;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JdbcDemo {
    //Step 1:
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
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
            // Step 2: Execute a SQL SELECT Statement
            String query = "SELECT id, name, class_group, create_date FROM student";
            ResultSet resultSet = statement.executeQuery(query);
            // Step 3: Process the ResultSet
            System.out.println("📌 Student Records: ");

            while (resultSet.next()) { // Moves to the next row in the result set

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String classGroup = resultSet.getString("class_group");

                // Convert to LocalDateTime
                LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();

                // OPTIONAL
                // Format LocalDateTime to String
                String formattedDate = createDate.format(DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy"));

                // Display student data
                System.out.println("ID: " + id + " | Name: " + name + " | Class: " + classGroup + " | Created At: " + createDate);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error connecting to the database: " + e.getMessage());
        }
    }

    public static void ex2() {
        // Step 1: Establish a connection and create a PreparedStatement
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT id, name, class_group, create_date FROM student WHERE class_group LIKE ?"
                );
        ) {
            System.out.println("✅ Database connection established successfully!");

            // Step 2: Set the parameter in PreparedStatement
            String classGroupParam = "G1";
            preparedStatement.setString(1, classGroupParam); // The first "?" is replaced with classGroupParam
            //preparedStatement.setString(1, "%" + classGroupParam + "%"); // Search for any class group that contains "G"

            // Step 3: Execute the SQL SELECT Statement

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Step 4: Process the ResultSet
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
