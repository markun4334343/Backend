package Webtech.Backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow all origins for now
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    // GET /api/tasks - Get all tasks
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return tasks;
    }

    // POST /api/tasks - Create a new task
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task newTask) {
        Task task = new Task(
                counter.incrementAndGet(),
                newTask.getText(),
                newTask.isCompleted()
        );
        tasks.add(task);
        return task;
    }

    // DELETE /api/tasks/{id} - Delete a task
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    // Simple health check endpoint
    @GetMapping("/health")
    public String health() {
        return "Backend is running!";
    }

    // Task model class (can be in separate file later)
    public static class Task {
        private Long id;
        private String text;
        private boolean completed;

        public Task() {}

        public Task(Long id, String text, boolean completed) {
            this.id = id;
            this.text = text;
            this.completed = completed;
        }

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }
    }
}