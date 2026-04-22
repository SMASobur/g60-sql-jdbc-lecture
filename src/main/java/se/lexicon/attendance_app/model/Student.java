package se.lexicon.attendance_app.model;

import java.time.LocalDateTime;

public class Student {
    private int id;
    private String name;
    private String classGroup;
    private LocalDateTime createDate;

    // Constructor with parameters
    public Student(int id, String name, String classGroup, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.classGroup = classGroup;
        this.createDate = createDate;
    }

    public Student(String name, String classGroup) {
        this.name = name;
        this.classGroup = classGroup;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    // toString() Method
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classGroup='" + classGroup + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
