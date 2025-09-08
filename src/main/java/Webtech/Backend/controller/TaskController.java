package Webtech.Backend.controller;

import Webtech.Backend.entity.Task;
import Webtech.Backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks - Get all tasks
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // POST /api/tasks - Create a new task
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task newTask) {
        // Create a new task with title (use title as text for your frontend)
        Task task = new Task();
        task.setTitle(newTask.getTitle()); // This will be the "text" from frontend
        task.setDescription(""); // Empty description for now
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

        existingTask.setDone(taskUpdate.isDone());
        return taskService.updateTask(id, existingTask);
    }

    // Simple health check endpoint
    @GetMapping("/health")
    public String health() {
        return "Backend is running!";
    }
}