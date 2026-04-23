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

            // Retrieve the auto-generated ID
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

    @Override
    public Optional<Attendance> findById(int id) {
        String sql = "SELECT * FROM attendance WHERE id = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // If found, wrap it in Optional
                    return Optional.of(mapRowToAttendance(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error finding attendance by ID: " + e.getMessage());
            throw new RuntimeException("Error finding attendance by ID", e);
        }

        // If nothing is found in the database, return an empty Optional
        return Optional.empty();
    }

    @Override
    public void update(Attendance attendance) {
        String sql = "UPDATE attendance SET student_id = ?, attendance_date = ?, status = ? WHERE id = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, attendance.getStudentId());
            ps.setDate(2, java.sql.Date.valueOf(attendance.getAttendanceDate()));
            ps.setString(3, attendance.getStatus());
            ps.setInt(4, attendance.getId()); // The WHERE clause parameter

            int rowsAffected = ps.executeUpdate();

            // Good practice: check if it actually updated something
            if (rowsAffected == 0) {
                System.out.println("⚠️ Warning: No attendance found with ID " + attendance.getId() + " to update.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error updating attendance: " + e.getMessage());
            throw new RuntimeException("Error updating attendance", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM attendance WHERE id = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            // executeUpdate() returns the number of rows deleted
            int rowsDeleted = ps.executeUpdate();

            // Returns true if 1 or more rows were deleted, false if 0
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting attendance: " + e.getMessage());
            throw new RuntimeException("Error deleting attendance", e);
        }
    }

    // --- HELPER METHOD ---
    // Instead of writing the mapping logic 3 times, we put it in one helper method.
    private Attendance mapRowToAttendance(ResultSet rs) throws SQLException {
        return new Attendance(
                rs.getInt("id"),
                rs.getInt("student_id"),
                rs.getDate("attendance_date").toLocalDate(), // Convert SQL Date back to LocalDate
                rs.getString("status")
        );
    }
}