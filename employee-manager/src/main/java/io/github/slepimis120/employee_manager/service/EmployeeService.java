package io.github.slepimis120.employee_manager.service;

import io.github.slepimis120.employee_manager.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private static final String FILE_PATH = "src/main/resources/employee-data.txt";

    public ResponseEntity<String> insert(Employee employee) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[3].equals(employee.getEmail())) {
                    return ResponseEntity.badRequest().body("Employee with email " + employee.getEmail() + " already exists");
                }
            }

            String userData = String.format("%s,%s,%s,%s,%s",
                    employee.getName(),
                    employee.getSurname(),
                    employee.getBirthYear(),
                    employee.getEmail(),
                    employee.getGender());

            writer.write(userData);
            writer.newLine();
            writer.flush();
            return ResponseEntity.ok("Employee added successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error occurred while adding employee");
        }

    }

    public ResponseEntity<Employee> get(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(name)) {
                    Employee employee = new Employee(
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            data[4]
                    );
                    return ResponseEntity.ok(employee);
                }
            }
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public ResponseEntity<List<Employee>> getAll() {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<Employee> employees = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Employee employee = new Employee(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );
                employees.add(employee);
            }
            return ResponseEntity.ok(employees);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public ResponseEntity<String> update(Employee employee) {
        File tempFile = new File(FILE_PATH + ".tmp");
        File originalFile = new File(FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[3].equals(employee.getEmail())) {
                    String updatedData = String.format("%s,%s,%s,%s,%s",
                            employee.getName(),
                            employee.getSurname(),
                            employee.getBirthYear(),
                            employee.getEmail(),
                            employee.getGender());
                    writer.write(updatedData);
                    updated = true;
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            if (!updated) {
                return ResponseEntity.badRequest().body("Employee with email " + employee.getEmail() + " not found.");
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error occurred while updating employee");
        }
        if (originalFile.delete()) {
            if (tempFile.renameTo(originalFile)) {
                return ResponseEntity.ok("Employee updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Error occurred while renaming the updated file.");
            }
        } else {
            return ResponseEntity.badRequest().body("Error occurred while deleting the original file. File path: " + originalFile.getAbsolutePath());
        }
    }

    public ResponseEntity<String> delete(String name) {
        File tempFile = new File(FILE_PATH + ".tmp");
        File originalFile = new File(FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean deleted = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(name)) {
                    deleted = true;
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }

            if (!deleted) {
                return ResponseEntity.badRequest().body("Employee with name " + name + " not found.");
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error occurred while deleting employee");
        }
        if (originalFile.delete()) {
            if (tempFile.renameTo(originalFile)) {
                return ResponseEntity.ok("Employee deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Error occurred while renaming the updated file.");
            }
        } else {
            return ResponseEntity.badRequest().body("Error occurred while deleting the original file. File path: " + originalFile.getAbsolutePath());
        }
    }
}
