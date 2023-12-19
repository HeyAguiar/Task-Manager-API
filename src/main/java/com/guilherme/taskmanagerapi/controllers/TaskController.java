package com.guilherme.taskmanagerapi.controllers;

import java.util.List;
import java.util.Optional;

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

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody TaskDTO task) {
		Task newTask = taskService.createTask(task);
		return new ResponseEntity<>(newTask, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = this.taskService.getAllTasks();
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTaskById(@PathVariable(value = "id") Long id,
    		                                      @Valid TaskDTO taskDTO) {
    	Optional<Task> taskOptional = taskService.findById(id);
    	if (!taskOptional.isPresent()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The taks was not found.");
    	}
    	return ResponseEntity.status(HttpStatus.OK).body(taskOptional.get());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") Long id, @Valid TaskDTO taskDTO) {
    	Optional<Task> taskOptional = taskService.findById(id);
    	if (!taskOptional.isPresent()) {
    		ResponseEntity.status(HttpStatus.NOT_FOUND).body("The task was not found");
    	}
    	taskService.delete(taskOptional.get());
    	return ResponseEntity.status(HttpStatus.OK).body("The task was deleted sucessfuly");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") Long id, @RequestBody @Valid TaskDTO taskDTO) {
    	Optional<Task> taskOptional = taskService.findById(id);
    	if (!taskOptional.isPresent()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The task was not found");
    	}
    	Task task = taskOptional.get();
    	BeanUtils.copyProperties(taskDTO, task);
    	taskService.saveTask(task);
    	return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
