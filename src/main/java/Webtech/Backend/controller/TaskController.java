package Webtech.Backend.controller;

import Webtech.Backend.entity.Task;
import Webtech.Backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")  // CHANGED FROM "*" TO EMPTY STRING
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
        System.out.println("=== DEBUG: Received task data ===");
        System.out.println("Raw data: " + taskData.toString());

        // Extract data from frontend format
        String text = (String) taskData.get("text");
        Boolean completed = (Boolean) taskData.get("completed");

        System.out.println("Extracted text: " + text);
        System.out.println("Extracted completed: " + completed);

        Task task = new Task();
        task.setTitle(text != null ? text : "");
        task.setDescription("");
        task.setDone(completed != null ? completed : false);

        System.out.println("Task object before save: " + task.getTitle() + ", done: " + task.isDone());

        Task savedTask = taskService.createTask(task);
        System.out.println("Task saved with ID: " + savedTask.getId() + ", Title: " + savedTask.getTitle());

        return savedTask;
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

    @GetMapping("/tasks/debug")
    public String debugAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        StringBuilder result = new StringBuilder();
        result.append("Total tasks: ").append(tasks.size()).append("\n\n");

        for (Task task : tasks) {
            result.append("ID: ").append(task.getId())
                    .append(", Title: '").append(task.getTitle())
                    .append("', Done: ").append(task.isDone())
                    .append(", Description: '").append(task.getDescription())
                    .append("'\n");
        }

        return result.toString();
    }
}