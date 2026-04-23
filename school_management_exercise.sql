-- ==========================================================
-- SQL Workshop: School Management System
-- Database: PostgreSQL
-- Author: MD Abdus Sobur Sikdar
-- Date: 2026-04-22
-- ==========================================================

-- 1. DDL: Data Definition Language
-- Exercise 1.1: Create Database (run from terminal: createdb school_management)
-- Exercise 1.2: Create Tables

CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         course_name VARCHAR(100) NOT NULL,
                         credits INT NOT NULL
);

CREATE TABLE student (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         class_group VARCHAR(50) NOT NULL,
                         create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE attendance (
                            id SERIAL PRIMARY KEY,
                            student_id INT NOT NULL,
                            attendance_date DATE NOT NULL DEFAULT CURRENT_DATE,
                            status VARCHAR(10) NOT NULL CHECK (status IN ('Present', 'Absent')),
                            FOREIGN KEY (student_id) REFERENCES student(id),
                            UNIQUE (student_id, attendance_date)
);

-- 2. DML: Data Manipulation Language
-- Exercise 2.1: Insert Data
INSERT INTO courses (course_name, credits) VALUES
                                               ('Java Programming', 5),
                                               ('SQL Basics', 3),
                                               ('Web Development', 4);

INSERT INTO student (name, class_group) VALUES
                                            ('Johan Lindberg', 'G3'),
                                            ('Jakob Svensson', 'G1'),
                                            ('Maria Nilsson', 'G2'),
                                            ('Erik Andersson', 'G1'),
                                            ('Anna Johansson', 'G1');

INSERT INTO attendance (student_id, attendance_date, status) VALUES
                                                                 (1, '2024-01-01', 'Present'),
                                                                 (1, '2024-01-02', 'Present'),
                                                                 (2, '2024-01-01', 'Absent'),
                                                                 (3, '2024-01-01', 'Present'),
                                                                 (4, '2024-01-01', 'Absent');

-- Exercise 2.2: Update Data
UPDATE courses SET credits = 6 WHERE course_name = 'Java Programming';

-- Exercise 2.3: Delete Data
DELETE FROM courses WHERE id = 3;

-- 3. DQL: Data Query Language
-- Exercise 3.1: Basic Select
SELECT * FROM student;

-- Exercise 3.2: Filtering
SELECT * FROM student WHERE class_group = 'G1';

-- Exercise 3.3: Pattern Matching
SELECT * FROM student WHERE name LIKE 'J%';

-- 4. Joins and Relationships
-- Exercise 4.1: Inner Join
SELECT s.name, a.attendance_date, a.status
FROM student s
         INNER JOIN attendance a ON s.id = a.student_id;

-- Exercise 4.2: Left Join
SELECT s.name, a.attendance_date, a.status
FROM student s
         LEFT JOIN attendance a ON s.id = a.student_id;

-- 5. Aggregation and Grouping
-- Exercise 5.1: Count
SELECT COUNT(*) AS total_students FROM student;

-- Exercise 5.2: Group By
SELECT status, COUNT(*) AS total_records
FROM attendance
GROUP BY status;

-- Exercise 5.3: Group By with Join
SELECT s.name, COUNT(a.id) AS present_days
FROM student s
         LEFT JOIN attendance a ON s.id = a.student_id AND a.status = 'Present'
GROUP BY s.id, s.name;