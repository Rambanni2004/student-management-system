package com.sms.dao;

import com.sms.model.Student;
import com.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public boolean addStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students (name, email, course, age) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.setInt(4, s.getAge());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT * FROM students ORDER BY id";
        List<Student> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    public List<Student> searchByName(String keyword) throws SQLException {
        String sql = "SELECT * FROM students WHERE name LIKE ? ORDER BY name";
        List<Student> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public boolean updateStudent(Student s) throws SQLException {
        String sql = "UPDATE students SET name=?, email=?, course=?, age=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.setInt(4, s.getAge());
            ps.setInt(5, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(rs.getInt("id"), rs.getString("name"),
                rs.getString("email"), rs.getString("course"), rs.getInt("age"));
    }
}
