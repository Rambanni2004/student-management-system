package com.sms;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final StudentDAO dao = new StudentDAO();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by ID");
            System.out.println("4. Search by Name");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("0. Exit");
            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> printList(dao.getAllStudents());
                    case 3 -> searchById();
                    case 4 -> printList(dao.searchByName(readLine("Enter name keyword: ")));
                    case 5 -> updateStudent();
                    case 6 -> deleteStudent();
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    System.out.println("Error: A student with this email already exists.");
                } else {
                    System.out.println("Database error: " + e.getMessage());
                }
            }
        }
    }

    private static void addStudent() throws SQLException {
        Student s = new Student(
                readLine("Name: "), readLine("Email: "),
                readLine("Course: "), readInt("Age: "));
        System.out.println(dao.addStudent(s) ? "Student added successfully." : "Failed to add student.");
    }

    private static void searchById() throws SQLException {
        Student s = dao.getStudentById(readInt("Enter ID: "));
        if (s == null) System.out.println("Student not found.");
        else { printHeader(); System.out.println(s); }
    }

    private static void updateStudent() throws SQLException {
        int id = readInt("Enter ID to update: ");
        Student s = dao.getStudentById(id);
        if (s == null) { System.out.println("Student not found."); return; }
        System.out.println("Leave blank to keep current value.");
        String name = readLine("Name [" + s.getName() + "]: ");
        String email = readLine("Email [" + s.getEmail() + "]: ");
        String course = readLine("Course [" + s.getCourse() + "]: ");
        String age = readLine("Age [" + s.getAge() + "]: ");
        if (!name.isBlank()) s.setName(name);
        if (!email.isBlank()) s.setEmail(email);
        if (!course.isBlank()) s.setCourse(course);
        if (!age.isBlank()) {
            try { s.setAge(Integer.parseInt(age.trim())); }
            catch (NumberFormatException e) { System.out.println("Invalid age, keeping old value."); }
        }
        System.out.println(dao.updateStudent(s) ? "Student updated." : "Update failed.");
    }

    private static void deleteStudent() throws SQLException {
        int id = readInt("Enter ID to delete: ");
        System.out.println(dao.deleteStudent(id) ? "Student deleted." : "Student not found.");
    }

    private static void printList(List<Student> list) {
        if (list.isEmpty()) { System.out.println("No records found."); return; }
        printHeader();
        list.forEach(System.out::println);
    }

    private static void printHeader() {
        System.out.println(String.format("%-5s %-20s %-28s %-15s %-3s", "ID", "NAME", "EMAIL", "COURSE", "AGE"));
        System.out.println("-".repeat(75));
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try { return Integer.parseInt(readLine(prompt)); }
            catch (NumberFormatException e) { System.out.println("Please enter a valid number."); }
        }
    }
}
