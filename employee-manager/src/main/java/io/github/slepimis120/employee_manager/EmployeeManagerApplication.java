package io.github.slepimis120.employee_manager;

import io.github.slepimis120.employee_manager.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagerApplication implements CommandLineRunner {

	private final FileService fileService;

	public EmployeeManagerApplication(FileService fileService) {
		this.fileService = fileService;
	}

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		fileService.ensureFileExists();
	}
}
