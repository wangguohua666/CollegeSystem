package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan(basePackages = {"com.example.filter"})
public class HrSsbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrSsbApplication.class, args);
    }

}
