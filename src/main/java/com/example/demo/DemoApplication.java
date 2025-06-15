package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.EnvLoader;

@SpringBootApplication
@RestController
public class DemoApplication {

    @GetMapping("/")
    public String home() {
        return "Spring is here!";
    }

    public static void main(String[] args) {
        // .env laden
        String dbUrl = EnvLoader.getEnv("DB_URL");

        SpringApplication.run(DemoApplication.class, args);
    }
}
