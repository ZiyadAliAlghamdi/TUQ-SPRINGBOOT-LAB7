package org.example.lms.Service;

import lombok.AllArgsConstructor;
import org.example.lms.Model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {


    private final ArrayList<Course> courses = new ArrayList<>();



    public ArrayList<Course> getAllCourses() {
        return courses;
    }


    public void addCourse(Course course) {
        courses.add(course);
    }


    public boolean deleteCourse(String id) {
        //i will use removeIf which is a method for arraylists
        return courses.removeIf(c -> c.getId().equals(id));
    }

    public boolean updateCourse(String id, Course course) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equals(id)) {
                courses.set(i, course);
                return true;
            }
        }
        return false;
    }

    public Course getCourseById(String id) {
        //i will use stream this time
        return courses.stream().filter(course -> course.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<Course> getCoursesByTeacherId(String teacherId) {
        return courses.stream()
                .filter(c -> teacherId.equals(c.getTeacherId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean assignTeacherToCourse(String courseId, String teacherId) {
        Course course = getCourseById(courseId);
        if (course != null) {
            course.setTeacherId(teacherId);
            return true;
        }
        return false;
    }

    public ArrayList<Course> getUnassignedCourses() {
        return courses.stream()
                .filter(c -> c.getTeacherId() == null)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Course> getCoursesByCreditHours(int creditHours) {
        return courses.stream()
                .filter(c -> c.getCreditHours() == creditHours)
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
