package org.example.lms.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.Api.ApiResponse;
import org.example.lms.Model.Teacher;
import org.example.lms.Service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeacher(@RequestBody @Valid Teacher teacher, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        teacherService.addTeacher(teacher);
        return ResponseEntity.ok(new ApiResponse("Teacher added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable String id, @RequestBody @Valid Teacher teacher, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = teacherService.updateTeacher(id, teacher);
        if (isUpdated) {
            return ResponseEntity.ok(new ApiResponse("Teacher updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Teacher with ID " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable String id) {
        if (teacherService.deleteTeacher(id)) {
            return ResponseEntity.ok(new ApiResponse("Teacher deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Teacher with ID " + id + " not found"));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> getSingleTeacher(@PathVariable String id) {
        Teacher teacher = teacherService.getSingleTeacher(id);
        if (teacher == null) {
            return ResponseEntity.status(404).body(new ApiResponse("Teacher with ID " + id + " not found"));
        }
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/specialization/{spec}")
    public ResponseEntity<?> getTeachersBySpecialization(@PathVariable String spec) {
        List<Teacher> teachers = teacherService.getTeachersBySpecialization(spec);
        if (teachers.isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("No teachers found with specialization: " + spec));
        }
        return ResponseEntity.ok(teachers);
    }

    @PutMapping("/{teacherId}/raise-salary")
    public ResponseEntity<?> giveSalaryRaise(@PathVariable String teacherId, @RequestParam Double amount) {
        if (amount == null || amount <= 0) {
            return ResponseEntity.status(400).body(new ApiResponse("Raise amount must be a positive number."));
        }

        if (teacherService.giveSalaryRaise(teacherId, amount)) {
            return ResponseEntity.ok(new ApiResponse("Salary updated successfully."));
        }

        return ResponseEntity.status(404).body(new ApiResponse("Teacher not found."));
    }

    @GetMapping("/highest-salary")
    public ResponseEntity<?> getHighestPaidTeacher() {
        Teacher teacher = teacherService.getHighestPaidTeacher();
        if (teacher == null) {
            return ResponseEntity.status(404).body(new ApiResponse("No teachers found in the system."));
        }
        return ResponseEntity.ok(teacher);
    }

    @PutMapping("/reassign")
    public ResponseEntity<ApiResponse> reassignCourses(@RequestParam String fromTeacherId, @RequestParam String toTeacherId) {
        boolean isReassigned = teacherService.reassignTeacherCourses(fromTeacherId, toTeacherId);
        if (isReassigned) {
            return ResponseEntity.ok(new ApiResponse("Courses reassigned successfully."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Reassignment failed. Check if teachers exist and the receiving teacher is active."));
    }
}