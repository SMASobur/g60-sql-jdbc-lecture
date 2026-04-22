package se.lexicon.attendance_app.dao;

import se.lexicon.attendance_app.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private final Connection connection;

    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student save(Student student) {
        String sql = "INSERT INTO student (name, class_group) VALUES (?, ?)";

        try (
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                // Statement.RETURN_GENERATED_KEYS is used to retrieve auto-generated ID values from INSERT statements
        ) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getClassGroup());

            ps.executeUpdate();

            // Read auto-generated ID
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    student.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error saving student: " + e.getMessage());
            throw new RuntimeException("Error saving student", e);
        }

        return student;
    }

    @Override
    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("class_group"),
                        rs.getTimestamp("create_date").toLocalDateTime()
                ));

                // or extract the map row to student as a helper method
                //students.add(mapRowToStudent(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error retrieving students: " + e.getMessage());
            throw new RuntimeException("Error retrieving students", e);
        }

        return students;

    }
}
