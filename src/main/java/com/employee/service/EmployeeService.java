package com.employee.service;

import com.employee.dto.EmployeeDTO;
import com.employee.entity.EmployeeTable;
import com.employee.exception.ApplicationException;
import com.employee.repository.EmployeeRepository;
import com.employee.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(EmployeeDTO employeeDTO) throws ApplicationException {
        Optional<EmployeeTable> optionalEmployee = employeeRepository.findById(employeeDTO.getAadhar());
        if (optionalEmployee.isPresent()) throw new ApplicationException(Constants.EMPLOYEE_ALREADY_EXISTS_ERROR_MSG);
        EmployeeTable employeeTable = EmployeeTable.builder()
                .name(employeeDTO.getName())
                .aadhar(employeeDTO.getAadhar())
                .age(employeeDTO.getAge())
                .department(employeeDTO.getDepartment())
                .city(employeeDTO.getCity())
                .dob(employeeDTO.getDob())
                .build();
        employeeRepository.save(employeeTable);
    }

    public EmployeeDTO getEmployee(long aadhar) throws ApplicationException {
        Optional<EmployeeTable> optionalEmployee = employeeRepository.findById(aadhar);
        EmployeeTable employee = optionalEmployee.orElseThrow(() ->
                new ApplicationException(Constants.EMPLOYEE_DOESNOT_EXISTS_ERROR_MSG));
        return EmployeeDTO.builder()
                .name(employee.getName())
                .aadhar(employee.getAadhar())
                .age(employee.getAge())
                .department(employee.getDepartment())
                .city(employee.getCity())
                .dob(employee.getDob())
                .build();
    }


    public void updateEmployee(long aadhar, String dept) throws ApplicationException {
        Optional<EmployeeTable> employee = employeeRepository.findById(aadhar);
        EmployeeTable emp = employee.orElseThrow(() ->
                new ApplicationException(Constants.EMPLOYEE_DOESNOT_EXISTS_ERROR_MSG));
        emp.setDepartment(dept);
    }

    public List<EmployeeDTO> getAllEmployees() throws ApplicationException {
        List<EmployeeTable> employeeList = employeeRepository.findAll();
        if (employeeList.isEmpty())
            throw new ApplicationException(Constants.EMPLOYEE_NOT_FOUND_ERROR_MSG);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeList.forEach(employees -> {
            EmployeeDTO employeeDTO = EmployeeDTO.builder()
                    .name(employees.getName())
                    .aadhar(employees.getAadhar())
                    .age(employees.getAge())
                    .department(employees.getDepartment())
                    .city(employees.getCity())
                    .dob(employees.getDob())
                    .build();
            employeeDTOS.add(employeeDTO);
        });
        return employeeDTOS;
    }
}
