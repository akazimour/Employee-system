package com.akazimour.Employeesystem.service;

import com.akazimour.Employeesystem.exception.ResourceNotFoundException;
import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();
    }
    // JUnit test for employeeService saveEmployee method
    @DisplayName("JUnit test for employeeService saveEmployee method")
    @Test
    public void givenEmployee_WhenSavedViaService_thenReturnEmployee(){
    // given
         given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
         given(employeeRepository.save(employee)).willReturn(employee);
    // when
        Employee employee1 = employeeServiceImpl.saveEmployee(employee);
    // then
        Assertions.assertThat(employee1).isNotNull();
        Assertions.assertThat(employee1.getId()).isGreaterThan(0);
      }

    // JUnit test for employeeService saveEmployee method with exception
    @DisplayName("JUnit test for employeeService saveEmployee method with exception")
    @Test
    public void givenEmployee_WhenSavedViaService_thenReturnException(){
        // given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        // when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()->{employeeServiceImpl.saveEmployee(employee);});
        // then
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    // JUnit test for returnAll employees
    @DisplayName("JUnit test for returnAll employees")
    @Test
    public void givenEmployees_WhenRunQuery_thenReturnAll(){
   // given
       Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tim")
                .lastName("Burton")
                .email("tim@gmail.com")
                .build();
given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));
        // when
        List<Employee> allEmployees = employeeServiceImpl.getAllEmployees();
        // then
        Assertions.assertThat(allEmployees).isNotNull();
        Assertions.assertThat(allEmployees).size().isEqualTo(2);
        Assertions.assertThat(allEmployees).contains(employee,employee1);
      }

    // JUnit test for returnAll employees negative case
    @DisplayName("JUnit test for returnAll employees negative case")
    @Test
    public void givenEmptyEmployeeList_WhenRunQuery_thenReturnEmptyList(){
        // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tim")
                .lastName("Burton")
                .email("tim@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        // when
        List<Employee> allEmployees = employeeServiceImpl.getAllEmployees();
        // then
        Assertions.assertThat(allEmployees).isEmpty();
        Assertions.assertThat(allEmployees).size().isEqualTo(0);

    }
    // JUnit test for findById service method
    @DisplayName("JUnit test for findById service method")
    @Test
        public void givenEmployee_WhenCalledById_thenReturn(){
        //given
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        //when
        Employee employee1 = employeeServiceImpl.getEmployeeById(employee.getId()).get();
        //then
        Assertions.assertThat(employee1).isNotNull();
        Assertions.assertThat(employee1).usingRecursiveComparison().isEqualTo(employee);
      }


}
