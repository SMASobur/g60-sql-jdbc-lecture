package se.lexicon.attendance_app.dao;

import se.lexicon.attendance_app.model.Attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceDaoImpl implements AttendanceDao {

    private final Connection connection;

    public AttendanceDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Attendance save(Attendance attendance) {
        String sql = "INSERT INTO attendance (student_id, attendance_date, status) VALUES (?, ?, ?)";

        try (
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, attendance.getStudentId());

            // Convert LocalDate to java.sql.Date for PostgreSQL
            ps.setDate(2, java.sql.Date.valueOf(attendance.getAttendanceDate()));

            ps.setString(3, attendance.getStatus());

            ps.executeUpdate();

            // read auto-generated ID
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    attendance.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error saving attendance: " + e.getMessage());
            throw new RuntimeException("Error saving attendance", e);
        }

        return attendance;
    }

    @Override
    public List<Attendance> findAll() {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT * FROM attendance";

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                attendances.add(mapRowToAttendance(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error retrieving attendances: " + e.getMessage());
            throw new RuntimeException("Error retrieving attendances", e);
        }

        return attendances;
    }


    //  HELPER METHOD
    private Attendance mapRowToAttendance(ResultSet rs) throws SQLException {
        return new Attendance(
                rs.getInt("id"),
                rs.getInt("student_id"),
                rs.getDate("attendance_date").toLocalDate(),
                rs.getString("status")
        );
    }
}