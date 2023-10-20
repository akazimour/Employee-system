package com.akazimour.Employeesystem.service;

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

import java.util.Optional;

import static org.mockito.BDDMockito.given;

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

}