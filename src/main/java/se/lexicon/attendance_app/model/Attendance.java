package se.lexicon.attendance_app.model;

import java.time.LocalDate;

public class Attendance {
    private int id;
    private Student student;
    private LocalDate attendanceDate;
    private AttendanceStatus status;

    // Constructor with parameters
    public Attendance(int id, Student student, LocalDate attendanceDate, AttendanceStatus status) {
        this.id = id;
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public Attendance(Student student, LocalDate attendanceDate, AttendanceStatus status) {
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public Attendance(Student student, AttendanceStatus status) {
        this.student = student;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    // toString() Method
    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", student=" + student +
                ", attendanceDate=" + attendanceDate +
                ", status=" + status +
                '}';
    }
}
