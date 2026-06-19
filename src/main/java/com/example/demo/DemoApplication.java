package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This tells Spring to scan both your root packages and the sub-folders
@SpringBootApplication(scanBasePackages = {"com.example.demo", "web", "repository", "model", "service", "config"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}