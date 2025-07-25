package org.example.lms.Service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lms.Model.Course;
import org.example.lms.Model.Teacher;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherService {
    
    private final ArrayList<Teacher> teachers = new ArrayList<>();

    private final CourseService courseService;
    

    public ArrayList<Teacher> getAllTeachers() {
        return teachers; 
    }

    public Teacher getSingleTeacher(String id) {
        for (Teacher Teacher: teachers){
            if (Teacher.getId().equals(id)){
                return Teacher;
            }
        }
        return null;
    }
    
    
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher); 
    }
    public boolean deleteTeacher(String id) {
        for (Teacher teacher: teachers){
            if (teacher.getId().equals(id)){
                teachers.remove(teacher);
                return true;
            }
        }
        return false;
    }

    public boolean updateTeacher(String id, @Valid Teacher Teacher) {
        for (Teacher TeacherLoopVar : teachers){
            if (TeacherLoopVar.getId().equals(id)){
                teachers.set(teachers.indexOf(TeacherLoopVar), TeacherLoopVar);
                return true;
            }
        }
        return false;
    }

    

    public ArrayList<Teacher> getTeachersBySpecialization(String specialization) {
        return teachers.stream()
                .filter(t -> t.getSpecialization().equalsIgnoreCase(specialization))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean giveSalaryRaise(String teacherId, double raiseAmount) {
        Teacher teacher = getSingleTeacher(teacherId);
        if (teacher != null && raiseAmount > 0) {
            teacher.setSalary(teacher.getSalary() + raiseAmount);
            return true;
        }
        return false;
    }

    public Teacher getHighestPaidTeacher() {
        return teachers.stream()
                .max(Comparator.comparing(Teacher::getSalary))
                .orElse(null);
    }

    public boolean reassignTeacherCourses(String fromTeacherId, String toTeacherId) {
        Teacher receivingTeacher = getSingleTeacher(toTeacherId);


        if (receivingTeacher == null || !receivingTeacher.getStatus().equalsIgnoreCase("active")) {
            return false;
        }

        ArrayList<Course> coursesToReassign = courseService.getCoursesByTeacherId(fromTeacherId);

        if (coursesToReassign.isEmpty()) {
            return true;
        }

        coursesToReassign.forEach(course -> course.setTeacherId(toTeacherId));
        return true;
    }
}