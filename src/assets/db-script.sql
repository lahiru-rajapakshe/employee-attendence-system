DROP DATABASE IF EXISTS easDB;

CREATE DATABASE easDB;

USE easDB;

CREATE TABLE student
(
    id VARCHAR(30) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    picture LONGBLOB NOT NULL,
    guardian_contact VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE user
(
    username VARCHAR(30) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role ENUM ('ADMIN', 'USER') NOT NULL
);

CREATE TABLE attendance
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    status ENUM ('IN', 'OUT') NOT NULL,
    student_id VARCHAR(30) NOT NULL,
    username VARCHAR(30) NOT NULL,
    CONSTRAINT fk_attendance_student FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT fk_attendance_user FOREIGN KEY (username) REFERENCES user(username)
);