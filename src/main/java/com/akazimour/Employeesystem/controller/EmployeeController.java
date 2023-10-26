package com.akazimour.Employeesystem.controller;

import com.akazimour.Employeesystem.exception.ResourceNotFoundException;
import com.akazimour.Employeesystem.model.Employee;
import com.akazimour.Employeesystem.service.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    @GetMapping
    public List<Employee> retrieveAllEmployees(){
       return employeeService.getAllEmployees();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        return employeeService.getEmployeeById(id).map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> modifyEmployee(@PathVariable long id, @RequestBody Employee employee){
        Optional<Employee> employeeById = employeeService.getEmployeeById(id);
        Employee employee1 = new Employee();
        if (employeeById.isPresent()){
            employee1 = employeeById.get();
            employee1.setFirstName(employee.getFirstName());
            employee1.setLastName(employee.getLastName());
            employee1.setEmail(employee.getEmail());
            employeeService.updateEmployee(employee1);
            return new ResponseEntity<Employee>(employee1,HttpStatus.OK);
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
@DeleteMapping("/{id}")
    public ResponseEntity<String>deleteById(@PathVariable long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<String>("Employee was deleted successfully!",HttpStatus.OK);
    }

}
