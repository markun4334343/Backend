package Webtech.Backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Spring Boot is running! Use /api/tasks";
    }

    @GetMapping("/health")
    public String health() {
        return "Backend is healthy!";
    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working!";
    }

    @GetMapping("/api/ping")
    public String ping() {
        return "API is working!";
    }
}