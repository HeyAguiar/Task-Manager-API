package com.guilherme.taskmanagerapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilherme.taskmanagerapi.domain.Task;
import com.guilherme.taskmanagerapi.dtos.TaskDTO;
import com.guilherme.taskmanagerapi.repositories.TaskRepository;

@Service
public class TaskService  {

	@Autowired
	TaskRepository repository;
	
	public Task createTask(TaskDTO data) {
		Task newTask = new Task(data);
		this.saveTask(newTask);
		return newTask;
		
	}
	
	public void saveTask(Task task) {
		this.repository.save(task);
	}

	public List<Task> getAllTasks() {
		return this.repository.findAll();
	}

	public Optional<Task> findById(Long id) {
		return this.repository.findById(id);
	}

	public void delete(Task task) {
		this.repository.delete(task);
	}
	
	
}

