package io.github.slepimis120.employee_manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private static final String FILE_PATH = "src/main/resources/employee-data.txt";

    public void ensureFileExists() {
        File file = new File(FILE_PATH);

        if (file.exists()) {
            logger.info("File already exists: {}", file.getName());
        } else {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    logger.info("File created: {}", file.getName());
                } else {
                    logger.warn("File already exists: {}", file.getName());
                }
            } catch (IOException e) {
                logger.error("An error occurred while creating the file: {}", file.getName(), e);
            }
        }
    }
}
