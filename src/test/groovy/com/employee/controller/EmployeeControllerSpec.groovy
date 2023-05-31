package com.employee.controller

import com.employee.dto.EmployeeDTO
import com.employee.exception.ApplicationException
import com.employee.service.EmployeeService
import com.employee.utility.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.time.LocalDate

import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerSpec extends Specification{

    @Autowired
    private MockMvc mvc

    @MockBean
    private EmployeeService employeeService;

    def "should return employee by aadhar with status 200"() {
        given:
        def employeeDTO= EmployeeDTO.builder().name("hey").aadhar(100).age(0).department("sales").city("hyd").dob(LocalDate.now()).build()
        when(employeeService.getEmployee(100L)).thenReturn(employeeDTO)

        expect:
        mvc.perform(MockMvcRequestBuilders.get("/employees/100"))
                .andExpect(status().isOk())
                .andExpect(content().json('{\"name\":\"hey\",\"aadhar\":100,\"age\":0,\"department\":"sales",\"city\":\"hyd\",\"dob\":\"31-05-2023\"}'))
    }

    def "should return status 500 when employee with given aadhar is not found"() {
        given:
        when(employeeService.getEmployee(100L)).thenThrow(new ApplicationException(Constants.EMPLOYEE_DOESNOT_EXISTS_ERROR_MSG))

        expect:
        mvc.perform(MockMvcRequestBuilders.get("/employees/100"))
                .andExpect(status().is5xxServerError())
//                .andExpect(content().json('{\"errorMessage\":\"Employee with given aadhar does not exists!\",\"errorCode\":500,\"timestamp\":"'+LocalDateTime.now()+'"}'))
    }

    def "should update employee department with status 200 when valid aadhar is given"(){
        given:
        def employeeJSON = '{\"name\":\"hey\",\"aadhar\":100,\"age\":0,\"department\":"marketing",\"city\":\"hyd\",\"dob\":\"30-05-2023\"}'

        when:
        def result = mvc.perform(MockMvcRequestBuilders.put("/employees").content(employeeJSON).contentType(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())

    }

    def "should throw error with status 500 when invalid aadhar is given to update "(){
        given:
        def employeeJSON = '{\"name\":\"hey\",\"aadhar\":101,\"age\":0,\"department\":"sales",\"city\":\"hyd\",\"dob\":\"30-05-2023\"}'
        employeeService.getEmployee(101) >> Optional.empty()
        when(employeeService.updateEmployee(101,"sales")).thenThrow(new ApplicationException(Constants.EMPLOYEE_DOESNOT_EXISTS_ERROR_MSG))

        expect:
        mvc.perform(MockMvcRequestBuilders.put("/employees").content(employeeJSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())


    }

    def "should add employee details with status 201"(){
        given:
        def employeeJSON = '{\"name\":\"hey\",\"aadhar\":100,\"age\":0,\"department\":"marketing",\"city\":\"hyd\",\"dob\":\"30-05-2023\"}'

        when:
        def result =  mvc.perform(MockMvcRequestBuilders.post("/employees").content(employeeJSON).contentType(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isCreated())
    }

    def "should throw error with status 500 when employee already exists"(){
        given:
        def employeeDTO= EmployeeDTO.builder().name("hey").aadhar(100).age(0).department("sales").city("hyd").dob(LocalDate.now()).build()
        def employeeJSON = '{\"name\":\"hey\",\"aadhar\":100,\"age\":0,\"department\":"sales",\"city\":\"hyd\",\"dob\":\"31-05-2023\"}'
        employeeService.getEmployee(100) >> Optional.of(employeeJSON)
        when(employeeService.addEmployee(employeeDTO)).thenThrow(new ApplicationException(Constants.EMPLOYEE_ALREADY_EXISTS_ERROR_MSG))

        expect:
        mvc.perform(MockMvcRequestBuilders.post("/employees").content(employeeJSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
    }


}
