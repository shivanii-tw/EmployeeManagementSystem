package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.exception.ApplicationException;
import com.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/employees")
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody EmployeeDTO employee) throws ApplicationException {
        employeeService.addEmployee(employee);

        String successMessage = "INSERT_SUCCESS of aadhar "+ employee.getAadhar();
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @GetMapping(value = "/employees/{aadhar}")
    public ResponseEntity<EmployeeDTO> getEmployeeDetails
            (@PathVariable("aadhar") @Min(value = 1, message = "{aadhar invalid}") @Max(value = 10000,
                    message = "{aadhar invalid}") long aadhar) throws ApplicationException {
        EmployeeDTO employee = employeeService.getEmployee(aadhar);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping(value = "/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeeDetails() throws ApplicationException {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    @PutMapping(value = "/employees")
    public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeDTO employee) throws ApplicationException {
        employeeService.updateEmployee(employee.getAadhar(), employee.getDepartment());
        String successMessage = "UPDATE_SUCCESS of aadhar "+employee.getAadhar();
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

}

