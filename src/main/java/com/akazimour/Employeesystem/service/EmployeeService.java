package com.akazimour.Employeesystem.service;

import com.akazimour.Employeesystem.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    public Employee saveEmployee(Employee employee);
    public List<Employee> getAllEmployees();
    public Optional<Employee>getEmployeeById(long id);
    public Employee updateEmployee(long id, Employee employee);
    public void deleteEmployee(long id);

}
