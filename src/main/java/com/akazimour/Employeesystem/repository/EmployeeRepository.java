package com.akazimour.Employeesystem.repository;

import com.akazimour.Employeesystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    @Query("SELECT e FROM Employee e WHERE e.firstName =:fName AND e.lastName =:lName")
    Optional<Employee> findByQuery(String fName, String lName);
}
