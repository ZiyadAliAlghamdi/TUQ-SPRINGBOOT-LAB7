package org.example.lms.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Teacher {
    @NotNull(message = "ID cannot be null")
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Salary cannot be null")
    @Positive(message = "Salary must be a positive number")
    private Double salary;

    @NotEmpty(message = "Specialization cannot be empty")
    private String specialization;

    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "^\\b(active|retired)\\b+$", message = "Status must be one of: active, retired")
    private String status;
}