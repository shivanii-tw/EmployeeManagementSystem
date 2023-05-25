package com.employee.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "employee")
public class EmployeeTable {
    private String name;
    @Id
    private long aadhar;
    private int age;
    private String department;
    private String city;

    private LocalDate dob;

    public EmployeeTable() {

    }

    public EmployeeTable(String name, long aadhar, int age, String department, String city, LocalDate dob) {
        this.name = name;
        this.aadhar = aadhar;
        this.age = age;
        this.department = department;
        this.city = city;
        this.dob = dob;
    }

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

    public void setAge(int age) {
        this.age = age;
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
}
