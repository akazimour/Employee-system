package com.akazimour.Employeesystem.controller;

import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.service.EmployeeService;
import com.akazimour.Employeesystem.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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


}
