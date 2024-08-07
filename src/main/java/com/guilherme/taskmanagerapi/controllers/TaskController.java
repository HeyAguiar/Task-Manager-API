package com.guilherme.taskmanagerapi.controllers;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guilherme.taskmanagerapi.domain.Task;
import com.guilherme.taskmanagerapi.dtos.TaskDTO;
import com.guilherme.taskmanagerapi.services.TaskService;
import jakarta.validation.Valid;

import javax.sql.DataSource;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService service;
	private final DataSource dataSource;

	@Autowired
	public TaskController(TaskService service, DataSource dataSource) {
		this.service = service;
		this.dataSource = dataSource;
	}

	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody TaskDTO task) {
		Task newTask = service.createTask(task);
        URI location = URI.create("/tasks/" + newTask.getId());
		return ResponseEntity.created(location).body(newTask);
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = this.service.getAllTasks();
		return ResponseEntity.ok(tasks);
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTaskById(@PathVariable(value = "id") UUID id) {
    	Optional<Task> taskOptional = service.findById(String.valueOf(id));
    	if (taskOptional.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The task was not found.");
    	}
    	return ResponseEntity.ok(taskOptional.get());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(value = "id") UUID id) {
    	Optional<Task> taskOptional = service.findById(String.valueOf(id));
    	if (taskOptional.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	service.deleteById(String.valueOf(id));
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") UUID id, @RequestBody @Valid TaskDTO taskDTO) {
    	Optional<Task> taskOptional = service.findById(String.valueOf(id));
    	if (taskOptional.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	Task task = taskOptional.get();
    	BeanUtils.copyProperties(taskDTO, task);
    	service.saveTask(task);
    	return ResponseEntity.ok(task);
    }

	@GetMapping("/healthcheck")
		public ResponseEntity<String> checkDataBaseConnection() {
			try (Connection connection = dataSource.getConnection()) {
				return ResponseEntity.ok("Conexão estabelecida com sucesso!");
			} catch (SQLException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha na conexão com o banco de dados");
			}
		}
	}