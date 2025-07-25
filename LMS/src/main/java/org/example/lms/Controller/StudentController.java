package org.example.lms.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lms.Api.ApiResponse;
import org.example.lms.Model.Student;
import org.example.lms.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.status(200).body(studentService.getAllStudents());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody @Valid Student student, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        studentService.addStudent(student);
        return ResponseEntity.status(200).body(new ApiResponse("Student added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody @Valid Student student, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = studentService.updateStudent(id, student);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Student updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Student with ID " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable String id) {
        boolean isDeleted = studentService.deleteStudent(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Student deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Student with ID " + id + " not found"));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> getSingleStudent(@PathVariable String id) {
        Student student = studentService.getSingleStudent(id);
        if (student == null) {
            return ResponseEntity.status(404).body(new ApiResponse("Student with ID " + id + " not found"));
        }
        return ResponseEntity.status(200).body(student);
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<?> getStudentsByMajor(@PathVariable String major) {
        List<Student> students = studentService.getStudentsByMajor(major);
        if (students.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No students found with major: " + major));
        }
        return ResponseEntity.status(200).body(students);
    }

    @GetMapping("/age")
    public ResponseEntity<?> getStudentsByAgeRange(@RequestParam int min, @RequestParam int max) {
        List<Student> students = studentService.getStudentsByAgeRange(min, max);
        if (students.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No students found in age range " + min + "-" + max));
        }
        return ResponseEntity.status(200).body(students);
    }

    @GetMapping("/average-gpa")
    public ResponseEntity<Map<String, Double>> getAverageGpa() {
        double average = studentService.calculateAverageGpa();
        return ResponseEntity.status(200).body(Collections.singletonMap("average_gpa", average));
    }

    @GetMapping("/highest-gpa")
    public ResponseEntity<?> getHighestGpaStudent() {
        Student student = studentService.highestGpa();
        if (student == null) {
            return ResponseEntity.status(404).body(new ApiResponse("No students found to determine highest GPA"));
        }
        return ResponseEntity.status(200).body(student);
    }
}