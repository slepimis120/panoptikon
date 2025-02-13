package io.github.slepimis120.employee_manager.controller;

import io.github.slepimis120.employee_manager.model.Employee;
import io.github.slepimis120.employee_manager.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody Employee employee) {
        return employeeService.insert(employee);
    }

    @GetMapping("/get")
    public ResponseEntity<Employee> get(@RequestParam String name) {
        return employeeService.get(name);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Employee>> getAll() {
        return employeeService.getAll();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Employee employee) {
        return employeeService.update(employee);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String name) {
        return employeeService.delete(name);
    }
}
