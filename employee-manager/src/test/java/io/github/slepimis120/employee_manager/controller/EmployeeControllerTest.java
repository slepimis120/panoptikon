package io.github.slepimis120.employee_manager.controller;

import io.github.slepimis120.employee_manager.model.Employee;
import io.github.slepimis120.employee_manager.service.EmployeeService;
import io.github.slepimis120.employee_manager.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import({EmployeeService.class, FileService.class})
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    public void testInsertEmployee() throws Exception {
        when(employeeService.insert(any(Employee.class))).thenReturn(ResponseEntity.ok("Employee added successfully"));

        mockMvc.perform(post("/employee/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"surname\":\"Doe\",\"birthYear\":\"1990\",\"email\":\"john.doe@example\",\"gender\":\"Male\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee added successfully"));
    }

    @Test
    public void testGetEmployee() throws Exception {
        when(employeeService.get("John")).thenReturn(ResponseEntity.ok(new Employee("John", "Doe", "1990", "john.doe@example", "Male")));

        mockMvc.perform(get("/employee/get")
                        .param("name", "John")  // Use query parameter instead of request body
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"John\",\"surname\":\"Doe\",\"birthYear\":\"1990\",\"email\":\"john.doe@example\",\"gender\":\"Male\"}"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<Employee> employees = List.of(
                new Employee("John", "Doe", "1990", "john.doe@example", "Male"),
                new Employee("Jane", "Smith", "1985", "jane.smith@example", "Female")
        );

        when(employeeService.getAll()).thenReturn(ResponseEntity.ok(employees));

        mockMvc.perform(get("/employee/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"John\",\"surname\":\"Doe\",\"birthYear\":\"1990\",\"email\":\"john.doe@example\",\"gender\":\"Male\"}," +
                        "{\"name\":\"Jane\",\"surname\":\"Smith\",\"birthYear\":\"1985\",\"email\":\"jane.smith@example\",\"gender\":\"Female\"}]"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee updatedEmployee = new Employee("John", "Doe", "1990", "john.doe@example", "Male");
        when(employeeService.update(any(Employee.class))).thenReturn(ResponseEntity.ok("Employee updated successfully"));

        mockMvc.perform(put("/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"surname\":\"Doe\",\"birthYear\":\"1990\",\"email\":\"john.doe@example\",\"gender\":\"Male\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee updated successfully"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        when(employeeService.delete("John")).thenReturn(ResponseEntity.ok("Employee deleted successfully"));

        mockMvc.perform(delete("/employee/delete")
                        .param("name", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }


}
