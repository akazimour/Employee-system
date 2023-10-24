package com.akazimour.Employeesystem.service;

import com.akazimour.Employeesystem.model.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee saveEmployee(Employee employee);
    public List<Employee> getAllEmployees();

}
