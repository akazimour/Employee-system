package com.akazimour.Employeesystem.service;

import com.akazimour.Employeesystem.exception.ResourceNotFoundException;
import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> employee1 = employeeRepository.findByEmail(employee.getEmail());
        if (employee1.isPresent()){
    throw new ResourceNotFoundException("Employee already exist with this email: "+employee.getEmail());
}
        return employeeRepository.save(employee);
    }
}
