package com.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.Period;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @NotNull(message = "name should not be null")
    @Pattern(regexp = "[A-Za-z]+( [A-Za-z]+)*", message = "Name should be alphabets only")
    private String name;

    @Column(unique = true)
    private long aadhar;

    private int age;

    @NotNull(message = "department should not be null")
    private String department;

    @NotNull(message = "city should not be null")
    private String city;

    @PastOrPresent(message = "Date should not be of future")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate dob;

    public int getAge() {
        Period p = Period.between(dob, LocalDate.now());
        this.age = p.getYears();
        return this.age;
    }

}
