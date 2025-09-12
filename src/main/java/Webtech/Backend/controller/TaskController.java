package Webtech.Backend.controller;

import Webtech.Backend.entity.Task;
import Webtech.Backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // POST /api/tasks - Create a new task
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task newTask) {
        Task task = new Task();
        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription() != null ? newTask.getDescription() : "");
        task.setDone(newTask.isDone());
        return taskService.createTask(task);
    }

    // DELETE /api/tasks/{id} - Delete a task
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    // PATCH /api/tasks/{id} - Update task completion status
    @PatchMapping("/tasks/{id}")
    public Task updateTaskCompletion(@PathVariable Long id, @RequestBody Task taskUpdate) {
        Task existingTask = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (taskUpdate.getTitle() != null) {
            existingTask.setTitle(taskUpdate.getTitle());
        }
        if (taskUpdate.getDescription() != null) {
            existingTask.setDescription(taskUpdate.getDescription());
        }
        existingTask.setDone(taskUpdate.isDone());

        return taskService.updateTask(id, existingTask);
    }

    // Simple health check endpoint
    @GetMapping("/health")
    public String health() {
        return "Backend is running!";
    }
}