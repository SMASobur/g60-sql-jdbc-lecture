package se.lexicon;

import se.lexicon.attendance_app.dao.StudentDao;
import se.lexicon.attendance_app.dao.StudentDaoImpl;
import se.lexicon.attendance_app.db.DatabaseConnection;
import se.lexicon.attendance_app.model.Student;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class main {
    static void main() {
        ex1();
    }

    static void ex1() {
        // 0. Get datasource
        DataSource dataSource = DatabaseConnection.getMysqlDataSource();

        // 1. Get connection using datasource
        try (Connection connection = dataSource.getConnection()) {


            // 2. Create DAO objects
            StudentDao studentDao = new StudentDaoImpl(connection);

            // 3. Create Student using constructor (id = 0 for new student)
            Student student = new Student(
                    "Ali Hassan",
                    "Java-Group-1"
            );

            Student savedStudent = studentDao.save(student);
            System.out.println("Saved student: " + savedStudent);

            // 4. Fetch student by ID
            // Student foundStudent = studentDao.findById(savedStudent.getId()).orElse(null);
            //System.out.println("Found student: " + foundStudent);

            // 5. Fetch all students
            System.out.println("\nAll students:");
            List<Student> students = studentDao.findAll();
            students.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
