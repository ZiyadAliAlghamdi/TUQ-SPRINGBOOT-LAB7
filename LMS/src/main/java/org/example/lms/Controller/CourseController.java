package org.example.lms.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.Api.ApiResponse;
import org.example.lms.Model.Course;
import org.example.lms.Service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody @Valid Course course, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        courseService.addCourse(course);
        return ResponseEntity.ok(new ApiResponse("Course added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable String id, @RequestBody @Valid Course course, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (courseService.updateCourse(id, course)) {
            return ResponseEntity.ok(new ApiResponse("Course updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Course with ID " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        if (courseService.deleteCourse(id)) {
            return ResponseEntity.ok(new ApiResponse("Course deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Course with ID " + id + " not found"));
    }

    //END OF CRUD


    @GetMapping("/search/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable String id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return ResponseEntity.status(404).body(new ApiResponse("Course with ID " + id + " not found"));
        }
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{courseId}/assign/{teacherId}")
    public ResponseEntity<ApiResponse> assignTeacherToCourse(@PathVariable String courseId, @PathVariable String teacherId) {
        if (courseService.assignTeacherToCourse(courseId, teacherId)) {
            return ResponseEntity.ok(new ApiResponse("Teacher assigned to course successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Course with ID " + courseId + " not found"));
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<Course>> getUnassignedCourses() {
        return ResponseEntity.ok(courseService.getUnassignedCourses());
    }

    @GetMapping("/credits/{creditHours}")
    public ResponseEntity<?> getCoursesByCreditHours(@PathVariable int creditHours) {
        List<Course> courses = courseService.getCoursesByCreditHours(creditHours);
        if (courses.isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("No courses found with " + creditHours + " credit hours"));
        }
        return ResponseEntity.ok(courses);
    }
}