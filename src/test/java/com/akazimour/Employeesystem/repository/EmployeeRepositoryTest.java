package com.akazimour.Employeesystem.repository;

import com.akazimour.Employeesystem.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp(){
        employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();
    }

    //JUnit test for save employee operation testing
    @DisplayName("JUnit test for save employee operation testing")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("johndoe@gmail.com")
//                .build();
        //when
        Employee savedEmployee = employeeRepository.save(employee);

        //then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

  //  JUnit test for find all employees
    @DisplayName("JUnit test for find all employees")
    @Test
    public void givenEmployeeList_WhenSaved_thenReturn(){
        //given
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("johndoe@gmail.com")
//                .build();

        Employee employee1 = Employee.builder()
                .firstName("Johny")
                .lastName("Cage")
                .email("johny@gmail.com")
                .build();

        employeeRepository.deleteAll();
        Employee savedEmployee = employeeRepository.save(employee);
        Employee savedEmployee1 = employeeRepository.save(employee1);
        List<Employee> empList = new ArrayList<>();
        empList.add(employee);
        empList.add(employee1);

        //when
        List<Employee> all = employeeRepository.findAll();

        //then
        assertThat(all).isNotNull();
        assertThat(all).size().isEqualTo(2);
        assertThat(all.subList(0,all.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(empList);
    }

//    JUnit test for get employee by id
    @DisplayName("JUnit test for get employee by id")
    @Test
    public void givenEmployee_WhenFindById_thenReturnWithEmployee(){
        //given

//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("johndoe@gmail.com")
//                .build();
       Employee savedEmployee = employeeRepository.save(employee);

        //when
        Employee employeeWithId = employeeRepository.findById(employee.getId()).get();

        //then
        assertThat(employeeWithId).isNotNull();
        assertThat(employeeWithId)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    //JUnit test for get employee by email
    @DisplayName("JUnit test for get employee by email")
    @Test
    public void givenEmployee_WhenFindByEmail_thenReturn(){
        //given
        Employee employee3 = Employee.builder()
                .firstName("Jack")
                .lastName("Sparrow")
                .email("jack@gmail.com")
                .build();
        Employee savedEmployee = employeeRepository.save(employee3);

        //when
        Employee employee = employeeRepository.findByEmail(employee3.getEmail()).get();

        //then
        assertThat(employee).isNotNull();
        assertThat(savedEmployee)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    // JUnit test for update employee object
    @DisplayName("JUnit test for update employee object")
    @Test
    public void givenEployee_WhenUpdated_thenReturnWithUpdatedEmp(){
    //given
        Employee employee4 = Employee.builder()
                .firstName("Sonja")
                .lastName("Blade")
                .email("sonja@gmail.com")
                .build();
        Employee savedEmployee = employeeRepository.save(employee4);

    // when
        Employee employee = employeeRepository.findById(employee4.getId()).get();
        employee.setFirstName("Tania");
        employee.setEmail("tanja@gmail.com");
        Employee savedEmp = employeeRepository.saveAndFlush(employee);

    // then
        assertThat(savedEmp).isNotNull();
        assertThat(savedEmp.getEmail()).isEqualTo("tanja@gmail.com");
        assertThat(savedEmp.getFirstName()).isEqualTo("Tania");
      }

    // JUnit test for JPQL query
    @DisplayName("JUnit test for JPQL query")
    @Test
    public void givenEmployee_WhenJPQLisUsed_thenReturnEmployee(){
    //given
        Employee employee4 = Employee.builder()
                .firstName("Sonja")
                .lastName("Blade")
                .email("sonja@gmail.com")
                .build();
        Employee savedEmployee = employeeRepository.save(employee4);

        Employee employee3 = Employee.builder()
                .firstName("Jack")
                .lastName("Sparrow")
                .email("jack@gmail.com")
                .build();
        Employee emp = employeeRepository.save(employee3);

    //when
        Employee employee = employeeRepository.findByQuery(savedEmployee.getFirstName(), savedEmployee.getLastName()).get();

    //then
        assertThat(employee)
                .usingRecursiveComparison()
                .isEqualTo(savedEmployee);
      }

   // JUnit test for
//    @Test
//    public void given_When_then(){
        //given

        //when

        //then
  //  }
}
