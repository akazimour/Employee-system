package com.akazimour.Employeesystem.integration;

import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class EmployeeController_IT {

    @Container
    public GenericContainer postgreSQLcontainer = new GenericContainer(DockerImageName.parse("postgres:latest"));

@Autowired
MockMvc mockMvc;

@Autowired
EmployeeRepository employeeRepository;

@Autowired
ObjectMapper objectMapper;

@BeforeEach
void setUp(){
    employeeRepository.deleteAll();
}

    @Test
    public void givenEmployee_WhenSaved_return_Employee() throws Exception{

        //given precondition section
        Employee employee4 = Employee.builder()
                .firstName("Sonja")
                .lastName("Blade")
                .email("sonja@gmail.com")
                .build();

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
       employeeRepository.saveAll(employeeList);
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
employeeRepository.save(employee);
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",employee.getId()));
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

        Employee savedEmployee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();

        employeeRepository.save(savedEmployee);


        Employee updatedEmployee = Employee.builder()
                .firstName("Arnold")
                .lastName("Schwarzenegger")
                .email("arnold@gmail.com")
                .build();


        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(updatedEmployee.getEmail())));
    }
    // JUnit test for updateEmployeeV2 REST API
    @Test
    public void givenEmployee_WhenUpdatedV2_thenReturnWithUpdatedEmployee() throws Exception {
        //given

        Employee savedEmployee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();

        employeeRepository.save(savedEmployee);


        Employee updatedEmployee = Employee.builder()
                .firstName("Arnold")
                .lastName("Schwarzenegger")
                .email("arnold@gmail.com")
                .build();


        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/update/{id}", savedEmployee.getId())
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
        Employee savedEmployee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();

        employeeRepository.save(savedEmployee);

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}",savedEmployee.getId()));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }

}

