package com.akazimour.Employeesystem.service;

import com.akazimour.Employeesystem.exception.ResourceNotFoundException;
import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Override
    public List<Employee> getAllEmployees() {
       return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
       return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(long id, Employee employee) {
        Employee employee1 = employeeRepository.findById(id).get();
        BeanUtils.copyProperties(employee,employee1);
       return employeeRepository.save(employee1);

    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

}
