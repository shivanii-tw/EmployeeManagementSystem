package com.employee.service;

import com.employee.dto.EmployeeDTO;
import com.employee.entity.EmployeeTable;
import com.employee.exception.ApplicationException;
import com.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) throws ApplicationException {
        Optional<EmployeeTable> optionalEmployee = employeeRepository.findById(employeeDTO.getAadhar());
        if (optionalEmployee.isPresent()) throw new ApplicationException("Employee already exist");
        EmployeeTable emp = new EmployeeTable();
        emp.setName(employeeDTO.getName());
        emp.setAadhar(employeeDTO.getAadhar());
        emp.setAge(employeeDTO.getAge());
        emp.setDepartment(employeeDTO.getDepartment());
        emp.setCity(employeeDTO.getCity());
        emp.setDob(employeeDTO.getDob());
        employeeRepository.save(emp);
    }

    @Override
    public EmployeeDTO getEmployee(long aadhar) throws ApplicationException {
        Optional<EmployeeTable> optionalEmployee = employeeRepository.findById(aadhar);
        EmployeeTable employee = optionalEmployee.orElseThrow(() -> new ApplicationException("Employee does not exist"));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setAadhar(employee.getAadhar());
        employeeDTO.setAge(employee.getDob());
        employeeDTO.setDepartment(employee.getDepartment());
        employeeDTO.setCity(employee.getCity());
        employeeDTO.setDob(employee.getDob());
        return employeeDTO;
    }

    @Override
    public void updateEmployee(long aadhar, String dept) throws ApplicationException {
        Optional<EmployeeTable> employee = employeeRepository.findById(aadhar);
        EmployeeTable emp = employee.orElseThrow(() -> new ApplicationException("Employee with given aadhar does not exist"));
        emp.setDepartment(dept);
        employeeRepository.save(emp);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() throws ApplicationException {
        Iterable<EmployeeTable> employeeList = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeList.forEach(employees -> {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setName(employees.getName());
            employeeDTO.setAadhar(employees.getAadhar());
            employeeDTO.setAge(employees.getDob());
            employeeDTO.setDepartment(employees.getDepartment());
            employeeDTO.setCity(employees.getCity());
            employeeDTO.setDob(employees.getDob());
            employeeDTOS.add(employeeDTO);
        });
        if (employeeDTOS.isEmpty())
            throw new ApplicationException("Employees Not found");
        return employeeDTOS;
    }
}
