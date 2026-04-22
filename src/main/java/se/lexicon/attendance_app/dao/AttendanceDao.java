package se.lexicon.attendance_app.dao;

import se.lexicon.attendance_app.model.Attendance;

import java.util.List;
import java.util.Optional;
//ToDo: Implement the AttendanceDao
public interface AttendanceDao {
    Attendance save(Attendance attendance); // Create or Update
    List<Attendance> findAll(); // Read all

    // Optional
    Optional<Attendance> findById(int id); // Read by ID
    void update(Attendance attendance);
    boolean delete(int id);

}
