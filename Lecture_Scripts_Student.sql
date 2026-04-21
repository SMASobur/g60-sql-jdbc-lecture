-- ==========================================================
-- This SQL script builds a small school database with:
-- 1) A student table (who the students are + their group)
-- 2) An attendance table (daily presence/absence for each student)
-- Then it runs some example SELECT queries to practice searching data.
-- Think of it like:
-- - student = "list of students"
-- - attendance = "daily attendance log" connected to students by student_id
-- ==========================================================


-- 1) CREATE DATABASE
-- Creates a new database named student_db.
CREATE DATABASE student_db;

-- Creates the database ONLY if it doesn't already exist.
-- Also sets text encoding so names (like Swedish letters åäö) work well.
CREATE DATABASE IF NOT EXISTS student_db
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Selects the database so the tables are created inside student_db.
USE student_db;


-- 2) CREATE TABLE: student
-- Creates a table called student.
-- Each row = one student.
CREATE TABLE IF NOT EXISTS student (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,   -- unique id for each student (1,2,3...) made automatically
    name VARCHAR(100) NOT NULL,                   -- student name (text up to 100 characters)
    class_group VARCHAR(50) NOT NULL,             -- group like G1, G2, G3
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- automatically stores when the student row was created
    );


-- 3) INSERT STUDENTS
-- Adds student data into the student table.
INSERT INTO student (name, class_group) VALUES
                    ('Erik Andersson', 'G1'),
                    ('Anna Johansson', 'G1'),
                    ('Lars Karlsson', 'G2'),
                    ('Maria Nilsson', 'G2'),
                    ('Gustav Eriksson', 'G1'),
                    ('Ingrid Larsson', 'G3'),
                    ('Olof Olsson', 'G3'),
                    ('Karin Persson', 'G2'),
                    ('Anders Svensson', 'G1'),
                    ('Maja Gustafsson', 'G3'),
                    ('Nils Pettersson', 'G2'),
                    ('Astrid Jonsson', 'G3'),
                    ('Per Hansson', 'G1'),
                    ('Linnea Bengtsson', 'G2'),
                    ('Johan Lindberg', 'G3');


-- 4) SELECT QUERIES (READ / VIEW DATA)
-- SELECT shows data from a table.

-- Shows all columns and all students.
SELECT * FROM student;

-- Shows only name and class_group columns.
SELECT name, class_group FROM student;

-- Shows only students in group G1.
SELECT * FROM student WHERE class_group = 'G1';

-- Shows students in group G1 whose name starts with "E".
-- LIKE 'E%' means: E + anything after it.
SELECT * FROM student WHERE class_group = 'G1' AND name LIKE 'E%';

-- IN (...) means "match any of these values"
-- This returns students in G1 OR G2.
SELECT * FROM student WHERE class_group IN ('G1','G2');

-- ORDER BY sorts the results alphabetically by name (ASC = A to Z).
SELECT * FROM student ORDER BY name ASC;


-- 5) UPDATE AND DELETE (CHANGE DATA)
-- UPDATE changes existing rows.
-- This changes the student with id = 1 to be in group G2.
UPDATE student SET class_group = 'G2' WHERE id = 1;

-- DELETE removes a row.
-- This deletes the student with id = 4.
DELETE FROM student WHERE id = 4;


-- 6) CREATE TABLE: attendance
-- This table stores attendance records.
-- Each row = one student on one date (present/absent).
CREATE TABLE IF NOT EXISTS attendance (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,      -- unique id for each attendance record
  student_id INT NOT NULL,                         -- which student this belongs to (connects to student.id)
  attendance_date DATE NOT NULL DEFAULT (CURRENT_DATE), -- date of attendance (defaults to today if not given)
  status ENUM('Present', 'Absent') NOT NULL,        -- only allowed values: Present or Absent

  -- FOREIGN KEY makes sure student_id must exist in the student table.
  FOREIGN KEY (student_id) REFERENCES student(id),

  -- UNIQUE prevents duplicates like:
  -- same student + same date entered twice.
  UNIQUE (student_id, attendance_date)
  );


-- 7) INSERT ATTENDANCE DATA
-- Adds many attendance rows for different students and dates.
-- Format: (student_id, date, status)
INSERT INTO attendance (student_id, attendance_date, status) VALUES
                     (1, '2024-01-01', 'Present'),
                     (2, '2024-01-01', 'Absent'),
                     (3, '2024-01-01', 'Present'),
                     (4, '2024-01-01', 'Present'),
                     (5, '2024-01-01', 'Absent'),
                     (6, '2024-01-01', 'Present'),
                     (7, '2024-01-01', 'Present'),
                     (8, '2024-01-01', 'Absent'),
                     (9, '2024-01-01', 'Present'),
                     (10, '2024-01-01', 'Present'),
                     (11, '2024-01-01', 'Absent'),
                     (12, '2024-01-01', 'Present'),
                     (13, '2024-01-01', 'Present'),
                     (14, '2024-01-01', 'Absent'),
                     (15, '2024-01-01', 'Present'),

                     (1, '2024-01-02', 'Absent'),
                     (2, '2024-01-02', 'Present'),
                     (3, '2024-01-02', 'Absent'),
                     (4, '2024-01-02', 'Present'),
                     (5, '2024-01-02', 'Present'),
                     (6, '2024-01-02', 'Absent'),
                     (7, '2024-01-02', 'Present'),
                     (8, '2024-01-02', 'Present'),
                     (9, '2024-01-02', 'Absent'),
                     (10, '2024-01-02', 'Present'),
                     (11, '2024-01-02', 'Present'),
                     (12, '2024-01-02', 'Absent'),
                     (13, '2024-01-02', 'Present'),
                     (14, '2024-01-02', 'Present'),
                     (15, '2024-01-02', 'Absent'),

                     (1, '2024-01-03', 'Present'),
                     (2, '2024-01-03', 'Present'),
                     (3, '2024-01-03', 'Present'),
                     (4, '2024-01-03', 'Absent'),
                     (5, '2024-01-03', 'Present'),
                     (6, '2024-01-03', 'Present'),
                     (7, '2024-01-03', 'Absent'),
                     (8, '2024-01-03', 'Present'),
                     (9, '2024-01-03', 'Present'),
                     (10, '2024-01-03', 'Absent'),
                     (11, '2024-01-03', 'Present'),
                     (12, '2024-01-03', 'Present'),
                     (13, '2024-01-03', 'Absent'),
                     (14, '2024-01-03', 'Present'),
                     (15, '2024-01-03', 'Present'),

                     (1, '2024-01-04', 'Present'),
                     (2, '2024-01-04', 'Absent'),
                     (3, '2024-01-04', 'Present'),
                     (4, '2024-01-04', 'Present'),
                     (5, '2024-01-04', 'Present'),
                     (6, '2024-01-04', 'Absent'),
                     (7, '2024-01-04', 'Present'),
                     (8, '2024-01-04', 'Present'),
                     (9, '2024-01-04', 'Present'),
                     (10, '2024-01-04', 'Absent'),
                     (11, '2024-01-04', 'Present'),
                     (12, '2024-01-04', 'Present'),
                     (13, '2024-01-04', 'Present'),
                     (14, '2024-01-04', 'Absent'),
                     (15, '2024-01-04', 'Present'),

                     (1, '2024-01-05', 'Absent'),
                     (2, '2024-01-05', 'Present'),
                     (3, '2024-01-05', 'Present'),
                     (4, '2024-01-05', 'Absent'),
                     (5, '2024-01-05', 'Present'),
                     (6, '2024-01-05', 'Present'),
                     (7, '2024-01-05', 'Present'),
                     (8, '2024-01-05', 'Absent'),
                     (9, '2024-01-05', 'Present'),
                     (10, '2024-01-05', 'Present'),
                     (11, '2024-01-05', 'Present'),
                     (12, '2024-01-05', 'Absent'),
                     (13, '2024-01-05', 'Present'),
                     (14, '2024-01-05', 'Present'),
                     (15, '2024-01-05', 'Absent'),

                     (1, '2024-01-06', 'Present'),
                     (2, '2024-01-06', 'Present'),
                     (3, '2024-01-06', 'Absent'),
                     (4, '2024-01-06', 'Present'),
                     (5, '2024-01-06', 'Absent'),
                     (6, '2024-01-06', 'Present'),
                     (7, '2024-01-06', 'Present'),
                     (8, '2024-01-06', 'Present'),
                     (9, '2024-01-06', 'Absent'),
                     (10, '2024-01-06', 'Present'),
                     (11, '2024-01-06', 'Absent'),
                     (12, '2024-01-06', 'Present'),
                     (13, '2024-01-06', 'Present'),
                     (14, '2024-01-06', 'Present'),
                     (15, '2024-01-06', 'Present'),

                     (1, '2024-01-07', 'Present'),
                     (2, '2024-01-07', 'Absent'),
                     (3, '2024-01-07', 'Present'),
                     (4, '2024-01-07', 'Present');


-- 8) JOIN (COMBINE TWO TABLES)
-- INNER JOIN connects student rows with their attendance rows.
-- It matches: student.id = attendance.student_id
-- Result: you see the student info + their attendance on the same row.
SELECT * FROM student s INNER JOIN attendance a ON s.id = a.student_id;


SELECT
    s.name,
    a.attendance_date,
    a.status
FROM student s
INNER JOIN attendance a ON s.id = a.student_id;