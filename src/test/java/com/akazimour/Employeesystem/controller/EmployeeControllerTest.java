package com.akazimour.Employeesystem.controller;

import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.service.EmployeeService;
import com.akazimour.Employeesystem.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControllerTest {

@Autowired
MockMvc mockMvc;

@MockBean
private EmployeeServiceImpl employeeService;

@Autowired
ObjectMapper objectMapper;

@Test
public void givenEmployee_WhenSaved_return_Employee() throws Exception{

    //given precondition section
    Employee employee4 = Employee.builder()
            .firstName("Sonja")
            .lastName("Blade")
            .email("sonja@gmail.com")
            .build();
    BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)-> invocation.getArgument(0));

    //when action behaviour that we want to test
    ResultActions response = mockMvc
            .perform(MockMvcRequestBuilders.post("/api/employees/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee4)));
    //then verify result with assertions
    response.andDo(MockMvcResultHandlers.print());
    response.andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee4.getFirstName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee4.getLastName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee4.getEmail())));
      }

    // JUnit test for get all employees Controller method
    @Test
    public void givenEmployeeList_WhenQuery_thenReturnWithTheList()throws Exception{
    // given
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder()
                .firstName("Dan")
                .lastName("Ulric")
                .email("john@gmail.com")
                .build());
        employeeList.add(Employee.builder()
                .firstName("James")
                .lastName("Brown")
                .email("james@gmail.com")
                .build());
        BDDMockito.given(employeeService.getAllEmployees()).willReturn(employeeList);
    //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

    //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(employeeList.size())));
      }

    // JUnit test for EmployeeController find by id REST API positive
    @Test
    public void givenEmployee_WhenCallById_thenReturnWitEmployee() throws Exception {
    //given
        long empId = 1L;
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();
    BDDMockito.given(employeeService.getEmployeeById(empId)).willReturn(Optional.of(employee));
    //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",empId));
    //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));
      }
    // JUnit test for EmployeeController find by id REST API negative
    @Test
    public void givenEmployee_WhenCallById_thenReturnWitEmpty() throws Exception {
        //given
        long empId = 1L;
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(empId)).willReturn(Optional.empty());
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",empId));
        //then
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
    // JUnit test for updateEmployee REST API
    @Test
    public void givenEmployee_WhenUpdated_thenReturnWithUpdatedEmployee() throws Exception {
    //given
        long empId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Arnold")
                .lastName("Schwarzenegger")
                .email("arnold@gmail.com")
                .build();
BDDMockito.given(employeeService.getEmployeeById(empId)).willReturn(Optional.of(savedEmployee));
BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->invocation.getArgument(0));
    //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", empId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(updatedEmployee.getEmail())));
      }
    // JUnit test for updateEmployee REST API negative case
    @Test
    public void givenEmployee_WhenUpdated_thenReturnWithEmptyObject() throws Exception {
        //given
        long empId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Arnold")
                .lastName("Schwarzenegger")
                .email("arnold@gmail.com")
                .build();

       // BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class),ArgumentMatchers.any(Long.class))).willReturn(updatedEmployee)
        BDDMockito.given(employeeService.getEmployeeById(empId)).willReturn(Optional.empty());
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->invocation.getArgument(0));
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", empId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void givenEmployee_WhenUpdatedV2_thenReturnWithUpdatedEmployee() throws Exception {
        //given
        long empId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstName("Arnold")
                .lastName("Schwarzenegger")
                .email("arnold@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(empId)).willReturn(Optional.of(savedEmployee));
        BDDMockito.given(employeeService.updateEmployeeV2(ArgumentMatchers.any(Long.class),ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->invocation.getArgument(0));
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", empId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(updatedEmployee.getEmail())));
    }

    // JUnit test for EmployeeController delete employee REST API
    @Test
   public void givenEmployee_WhenDeleted_thenBeingRemovedFromDb() throws Exception {
    //given
        long empId = 1L;
        BDDMockito.willDoNothing().given(employeeService).deleteEmployee(empId);
    //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}",empId));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

      }


}
