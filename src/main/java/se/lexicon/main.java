package se.lexicon;

import se.lexicon.attendance_app.dao.AttendanceDao;
import se.lexicon.attendance_app.dao.AttendanceDaoImpl;
import se.lexicon.attendance_app.dao.StudentDao;
import se.lexicon.attendance_app.dao.StudentDaoImpl;
import se.lexicon.attendance_app.db.DatabaseConnection;
import se.lexicon.attendance_app.model.Attendance;
import se.lexicon.attendance_app.model.Student;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class main {

    // FIX: Add String[] args for proper main method
    public static void main(String[] args) {
       // ex1();
        System.out.println("\n--------------------------------------------------\n");
        ex2();
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
                    "Sikdar",
                    "G5"
            );

            Student savedStudent = studentDao.save(student);
            System.out.println("Saved student: " + savedStudent);

            // 4. Fetch student by ID
            // Student foundStudent = studentDao.findById(savedStudent.getId()).orElse(null);
            // System.out.println("Found student: " + foundStudent);

            // 5. Fetch all students
            System.out.println("\nAll students:");
            List<Student> students = studentDao.findAll();
            students.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    static void ex2() {
        DataSource dataSource = DatabaseConnection.getMysqlDataSource();

        try (Connection connection = dataSource.getConnection()) {

            // 0. Setup: We need a Student ID first to link to the Attendance
            StudentDao studentDao = new StudentDaoImpl(connection);
            int latestStudentId = 0;
            String getMaxIdSql = "SELECT MAX(id) FROM student";
            try (PreparedStatement ps = connection.prepareStatement(getMaxIdSql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    latestStudentId = rs.getInt(1); // Get the result of MAX(id)
                }
            }

            if (latestStudentId == 0) {
                System.out.println("⚠️ No students found in the database!");
                return; // Stop the method here
            }

            System.out.println("✅ Using latest student ID: " + latestStudentId);

            // 1. Initialize AttendanceDao
            AttendanceDao attendanceDao = new AttendanceDaoImpl(connection);

            // 2.  SAVE
            System.out.println("--- SAVE ---");
            Attendance newAttendance = new Attendance(
                    latestStudentId,
                    LocalDate.now(),
                    "Present"
            );
            Attendance savedAttendance = attendanceDao.save(newAttendance);
            System.out.println("✅ Saved Attendance: " + savedAttendance);

            // 3.  FIND ALL
            System.out.println("\n--- FIND ALL ---");
            List<Attendance> allAttendances = attendanceDao.findAll();
            allAttendances.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}