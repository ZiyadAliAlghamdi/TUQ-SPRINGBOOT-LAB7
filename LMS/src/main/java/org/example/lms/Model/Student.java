package org.example.lms.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;


@Data
@AllArgsConstructor
public class Student {
    @NotNull(message = "ID cannot be null")
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @NotEmpty(message = "Major cannot be empty")
    private String major;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Student must be at least 18 years old")
    private int age;


    @NotNull(message = "gpa cannot be null")
    private double gpa;
}