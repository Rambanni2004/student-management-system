CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    course VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_students_name ON students(name);

INSERT INTO students (name, email, course, age) VALUES
('Ravi Kumar', 'ravi@example.com', 'B.Tech CSE', 20),
('Sneha Reddy', 'sneha@example.com', 'B.Tech ECE', 21);
