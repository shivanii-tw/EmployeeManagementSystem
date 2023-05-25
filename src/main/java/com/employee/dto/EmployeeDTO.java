package com.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAadhar() {
        return aadhar;
    }

    public void setAadhar(long aadhar) {
        this.aadhar = aadhar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(LocalDate dob) {
        LocalDate today = LocalDate.now();
        LocalDate birthday = dob;

        Period p = Period.between(birthday, today);
        this.age = p.getYears();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "name='" + name + '\'' +
                ", aadhar='" + aadhar + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", city='" + city + '\'' +
                ", dob=" + dob +
                '}';
    }
}
