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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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
    // JUnit test for updateEmployee service method
    @DisplayName("JUnit test for updateEmployee service method")
    @Test
    public void givenEmployee_WhenUpdated_thenReturnWithUpdatedEmployee(){
   // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tim")
                .lastName("Burton")
                .email("tim@gmail.com")
                .build();
      //  given(employeeRepository.findById(employee1.getId())).willReturn(Optional.of(employee1));
        given(employeeRepository.save(employee1)).willReturn(employee1);
        employee1.setEmail("burton@gmail.com");
        employee1.setFirstName("Tom");

   // when
        Employee updated = employeeServiceImpl.updateEmployee(employee1);
    // then
        Assertions.assertThat(updated.getEmail()).isEqualTo("burton@gmail.com");
        Assertions.assertThat(updated.getFirstName()).isEqualTo("Tom");
        Assertions.assertThat(updated.getId()).isEqualTo(2L);
      }

    @DisplayName("JUnit test for updateEmployeeV2 service method")
    @Test
    public void givenEmployee_WhenUpdatedV2_thenReturnWithUpdatedEmployee(){
        // given
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Tim")
                .lastName("Burton")
                .email("tim@gmail.com")
                .build();
        given(employeeRepository.findById(employee1.getId())).willReturn(Optional.of(employee1));
        given(employeeRepository.save(employee1)).willReturn(employee1);
        employee1.setEmail("burton@gmail.com");
        employee1.setFirstName("Tom");

        // when
        Employee updated = employeeServiceImpl.updateEmployeeV2(employee.getId(),employee1);
        // then
        Assertions.assertThat(updated.getEmail()).isEqualTo("burton@gmail.com");
        Assertions.assertThat(updated.getFirstName()).isEqualTo("Tom");
        Assertions.assertThat(updated.getId()).isEqualTo(1L);
    }

    // JUnit test for delete byId service method
    @DisplayName("JUnit test for delete byId service method")
    @Test
    public void givenEmployee_WhenDelete_thenEmployeeDeleted(){
   // given
        long employeeId=2L;
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tim")
                .lastName("Burton")
                .email("tim@gmail.com")
                .build();
        willDoNothing().given(employeeRepository).deleteById(employeeId);
   // when
        employeeServiceImpl.deleteEmployee(employeeId);
   // then
        verify(employeeRepository,times(1)).deleteById(employeeId);

      }



}
