
package com.kiet.student.controller;

import com.kiet.student.model.student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final List<student> students = new ArrayList<>();

    // Initialize 5 students
    public StudentController() {
        students.add(new student(101, "Rahul Sharma",
                "rahul@kiet.edu", "CSE(AI & ML)", 2, 8.65));

        students.add(new student(102, "Priya Singh",
                "priya@kiet.edu", "CSE", 2, 9.20));

        students.add(new student(103, "Aman Verma",
                "aman@kiet.edu", "IT", 3, 8.40));

        students.add(new student(104, "Neha Gupta",
                "neha@kiet.edu", "CSE", 2, 9.10));

        students.add(new student(105, "Rohit Kumar",
                "rohit@kiet.edu", "ECE", 1, 7.90));
    }

    // Q3(A): Get all students
    @GetMapping
    public List<student> getAllStudents() {
        return students;
    }

    // Q3(B): Get student by ID
    @GetMapping("/{studentId}")
    public Object getStudentById(
            @PathVariable int studentId) {

        for (student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }

        return Map.of("message", "Student not found");
    }

    // Q4: Add student
    @PostMapping
    public student addStudent(
            @RequestBody student student) {

        students.add(student);
        return student;
    }

    // Q5(A): Update student
    @PutMapping("/{studentId}")
    public Object updateStudent(
            @PathVariable int studentId,
            @RequestBody student updatedStudent) {

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId() == studentId) {
                updatedStudent.setStudentId(studentId);
                students.set(i, updatedStudent);
                return updatedStudent;
            }
        }

        return Map.of("message", "Student not found");
    }

    // Q5(B): Delete student
    @DeleteMapping("/{studentId}")
    public Map<String, String> deleteStudent(
            @PathVariable int studentId) {

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId() == studentId) {
                students.remove(i);

                return Map.of(
                        "message", "Student deleted successfully"
                );
            }
        }

        return Map.of("message", "Student not found");
    }

    // Q6(A): Search by branch and year
    @GetMapping("/search")
    public List<student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {

        return students.stream()
                .filter(s -> s.getBranch()
                        .toLowerCase()
                        .contains(branch.toLowerCase()))
                .filter(s -> s.getYear() == year)
                .collect(Collectors.toList());
    }

    // Q6(B): Find students by minimum CGPA
    @GetMapping("/topper")
    public List<student> getToppers(
            @RequestParam double cgpa) {

        return students.stream()
                .filter(s -> s.getCgpa() >= cgpa)
                .collect(Collectors.toList());
    }
}
