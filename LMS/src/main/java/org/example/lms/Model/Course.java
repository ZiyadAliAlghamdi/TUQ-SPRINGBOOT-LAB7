package org.example.lms.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    @NotNull(message = "ID cannot be empty")
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    //could be null if there is no teached for the course
    @Positive(message = "id cannot be positive")
    private String teacherId;

    @NotNull(message = "Credit hours cannot be null")
    @Positive(message = "Credit hours must be positive")
    private int creditHours;
}