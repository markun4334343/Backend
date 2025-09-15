package Webtech.Backend;

import Webtech.Backend.config.CorsConfig;
import Webtech.Backend.controller.RootController;
import Webtech.Backend.controller.TaskController;
import Webtech.Backend.entity.Task;
import Webtech.Backend.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

// Einfacherer Test ohne Spring Boot Context
class WebsiteApplicationSimpleTests {

	@Test
	void testCorsConfiguration() {
		CorsConfig corsConfig = new CorsConfig();
		CorsConfigurationSource source = corsConfig.corsConfigurationSource();

		assertNotNull(source);
		assertTrue(source instanceof UrlBasedCorsConfigurationSource);
	}

	@Test
	void testRootController() {
		RootController controller = new RootController();
		String result = controller.home();

		assertNotNull(result);
		assertTrue(result.contains("Task Backend API is running"));
	}

	@Test
	void testTaskEntity() {
		Task task = new Task("Test Task", "Test Description");
		task.setId(1L);
		task.setDone(true);

		assertEquals("Test Task", task.getTitle());
		assertEquals("Test Task", task.getText());
		assertTrue(task.isDone());
		assertTrue(task.isCompleted());
	}
}

// Separater Test mit Mockito Extension
@ExtendWith(MockitoExtension.class)
class TaskControllerTests {

	@Mock
	private TaskService taskService;

	@InjectMocks
	private TaskController taskController;

	private Task testTask;

	@BeforeEach
	void setUp() {
		testTask = new Task("Test Task", "");
		testTask.setId(1L);
	}

	@Test
	void testCreateTask() {
		when(taskService.createTask(any(Task.class))).thenReturn(testTask);

		Map<String, Object> taskData = new HashMap<>();
		taskData.put("text", "Test Task");
		taskData.put("completed", false);

		Task result = taskController.createTask(taskData);

		assertNotNull(result);
		assertEquals("Test Task", result.getTitle());
		verify(taskService, times(1)).createTask(any(Task.class));
	}

	@Test
	void testHealthEndpoint() {
		String result = taskController.health();
		assertEquals("Backend is running!", result);
	}
}