package com.akazimour.Employeesystem.service;

import com.akazimour.Employeesystem.exception.ResourceNotFoundException;
import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Employee updateEmployee(Employee employee) {
       return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeV2(Long id,Employee employee ) {
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()){
            Employee employee1 = byId.get();
            employee1.setFirstName(employee.getFirstName());
            employee1.setLastName(employee.getLastName());
            employee1.setEmail(employee.getEmail());
           return employeeRepository.save(employee1);
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee does not exist with id: "+id);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

}
