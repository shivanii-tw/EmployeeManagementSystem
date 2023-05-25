package com.employee.service;

import com.employee.dto.EmployeeDTO;
import com.employee.exception.ApplicationException;

import java.util.List;

public interface EmployeeService {
    void addEmployee(EmployeeDTO employeeDTO) throws ApplicationException;

    EmployeeDTO getEmployee(long aadhar) throws ApplicationException;

    void updateEmployee(long aadhar, String department) throws ApplicationException;

    List<EmployeeDTO> getAllEmployees() throws ApplicationException;

}
