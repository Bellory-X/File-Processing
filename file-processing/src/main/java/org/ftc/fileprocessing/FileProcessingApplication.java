package org.ftc.fileprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FileProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileProcessingApplication.class, args);
    }

}
