package Webtech.Backend.controller;

import Webtech.Backend.entity.Task;
import Webtech.Backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "")  // CHANGED FROM "*" TO EMPTY STRING
@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // In your TaskController class:
    @GetMapping("")  // EMPTY STRING instead of "/"
    public String apiHome() {
        return "API Endpoints: " +
                "<br>GET /api/tasks - Get all tasks" +
                "<br>POST /api/tasks - Create task" +
                "<br>DELETE /api/tasks/{id} - Delete task" +
                "<br>PATCH /api/tasks/{id} - Update task" +
                "<br>GET /api/health - Health check";
    }

    // GET /api/tasks - Get all tasks
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Map<String, Object> taskData) {
        // Extract data from frontend format
        String text = (String) taskData.get("text");
        Boolean completed = (Boolean) taskData.get("completed");

        Task task = new Task();
        task.setTitle(text != null ? text : "");
        task.setDescription("");
        task.setDone(completed != null ? completed : false);

        return taskService.createTask(task);
    }

    // DELETE /api/tasks/{id} - Delete a task
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/tasks/{id}")
    public Task updateTaskCompletion(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Task existingTask = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (updates.containsKey("text")) {
            existingTask.setTitle((String) updates.get("text"));
        }
        if (updates.containsKey("completed")) {
            existingTask.setDone((Boolean) updates.get("completed"));
        }

        return taskService.updateTask(id, existingTask);
    }

    // Simple health check endpoint
    @GetMapping("/health")
    public String health() {
        return "Backend is running!";
    }
}