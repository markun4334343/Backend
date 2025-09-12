package Webtech.Backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "Task Backend API is running! Available endpoints: " +
                "<br>/api/health - Health check" +
                "<br>/api/tasks - Get all tasks" +
                "<br>POST /api/tasks - Create new task" +
                "<br>DELETE /api/tasks/{id} - Delete task" +
                "<br>PATCH /api/tasks/{id} - Update task";
    }
}