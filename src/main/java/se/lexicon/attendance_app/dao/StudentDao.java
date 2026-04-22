package se.lexicon.attendance_app.dao;

import se.lexicon.attendance_app.model.Student;

import java.util.List;

public interface StudentDao {
    Student save(Student student); // Create or Update
    List<Student> findAll(); // Read all
}
