package com.akazimour.Employeesystem.controller;

import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.service.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

   private EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
       return employeeService.saveEmployee(employee);
    }
}
