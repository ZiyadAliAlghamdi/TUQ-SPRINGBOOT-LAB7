package org.example.lms.Service;

import jakarta.validation.Valid;
import org.example.lms.Model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final ArrayList<Student> students = new ArrayList<>();

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public Student getSingleStudent(String id) {
        for (Student student: students){
            if (student.getId().equals(id)){
                return student;
            }
        }
        return null;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean deleteStudent(String id) {
        for (Student student: students){
            if (student.getId().equals(id)){
                students.remove(student);
                return true;
            }
        }
        return false;
    }


    public boolean updateStudent(String id, @Valid Student student) {
        for (Student studentLoopVar : students){
            if (studentLoopVar.getId().equals(id)){
                students.set(students.indexOf(studentLoopVar), studentLoopVar);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Student> getStudentsByMajor(String major) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getMajor().equalsIgnoreCase(major)) {
                result.add(student);
            }
        }
        return result;
    }

    public ArrayList<Student> getStudentsByAgeRange(int minAge, int maxAge) {
        return students.stream()
                .filter(s -> s.getAge() >= minAge && s.getAge() <= maxAge)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public double calculateAverageGpa() {
        if (students.isEmpty()) {
            return 0;
        }

        double totalGpa = 0;
        for (Student student : students) {
            totalGpa += student.getGpa();
        }

        return totalGpa / students.size();
    }

    public Student highestGpa(){
        return students.stream()
                .max(Comparator.comparing(Student::getGpa))
                .orElse(null);  //getting max using java stream
    }


}
