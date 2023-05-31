package com.employee.service

import com.employee.dto.EmployeeDTO
import com.employee.entity.EmployeeTable
import com.employee.exception.ApplicationException
import com.employee.repository.EmployeeRepository
import com.employee.utility.Constants
import spock.lang.Specification

import java.time.LocalDate

class EmployeeServiceSpec extends Specification{

    def employeeRepoMock = Mock(EmployeeRepository)
    def employeeServiceMock = new EmployeeService(employeeRepoMock)

    def "should return employee when valid aadhar is given"(){
        given:
        def expected = EmployeeTable.builder().name("hey").aadhar(1000).age(20)
                .department("sales").city("hyd").dob(LocalDate.now()).build()

        when:
        employeeServiceMock.getEmployee(1000)

        then:
        1 * employeeRepoMock.findById(1000) >> Optional.of(expected)


    }
    def "should throw error when given employee aadhar does not exist"(){
        given:
        employeeRepoMock.findById(100) >> Optional.empty()

        when:
        employeeServiceMock.getEmployee(100)

        then:
        def err = thrown(ApplicationException)
        err.message == Constants.EMPLOYEE_DOESNOT_EXISTS_ERROR_MSG
    }


    def "should update department of employee when valid aadhar is given"() {
        given:
        def employeeBefore = EmployeeTable.builder().name("hey").aadhar(1000).age(20)
                .department("sales").city("hyd").dob(LocalDate.now()).build()

        when:
        employeeServiceMock.updateEmployee(1000,"marketing")

        then:
        employeeBefore.department == "marketing"
        1 * employeeRepoMock.findById(1000) >> Optional.of(employeeBefore)
    }

    def "should throw error when update of employee department done with invalid aadhar"() {
        given:
        employeeRepoMock.findById(100) >> Optional.empty()

        when:
        employeeServiceMock.updateEmployee(100,"marketing")

        then:
        def err = thrown(ApplicationException)
        err.message == Constants.EMPLOYEE_DOESNOT_EXISTS_ERROR_MSG
    }

    def "should return all employees"() {
        given:
        def expectedEmployeeList = new ArrayList()
        expectedEmployeeList.add(EmployeeTable.builder().name("hey").aadhar(1000).age(20)
                .department("sales").city("hyd").dob(LocalDate.now()).build())
        expectedEmployeeList.add(EmployeeTable.builder().name("hi").aadhar(1001).age(20)
                .department("sales").city("hyd").dob(LocalDate.now()).build())

        when:
        employeeServiceMock.getAllEmployees()

        then:
        1 * employeeRepoMock.findAll() >> expectedEmployeeList

    }

    def "should create a new employee with given details"(){
        given:
        def employeeDTO = EmployeeDTO.builder().name("hi").aadhar(1001).age(20)
                .department("sales").city("hyd").dob(LocalDate.now()).build()
        employeeRepoMock.findById(1001) >> Optional.empty()

        when:
        employeeServiceMock.addEmployee(employeeDTO)

        then:
        1 * employeeRepoMock.save(_)

    }

    def "should throw error when trying to add existing employee details"() {
        given:
        def employeeEntity = EmployeeTable.builder().name("hi").aadhar(1001).age(20)
                .department("sales").city("hyd").dob(LocalDate.now()).build()
        def employeeDTO = EmployeeDTO.builder().name("hi").aadhar(1001).age(20)
                .department("sales").city("hyd").dob(LocalDate.now()).build()
        employeeRepoMock.findById(1001) >> Optional.of(employeeEntity)

        when:
        employeeServiceMock.addEmployee(employeeDTO)

        then:
        def err = thrown(ApplicationException)
        err.message == Constants.EMPLOYEE_ALREADY_EXISTS_ERROR_MSG
    }

}