package com.guilherme.taskmanagerapi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.guilherme.taskmanagerapi.dtos.TaskUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilherme.taskmanagerapi.domain.Task;
import com.guilherme.taskmanagerapi.dtos.TaskDTO;
import com.guilherme.taskmanagerapi.repositories.TaskRepository;

@Service
public class TaskService  {

	private final TaskRepository repository;

	@Autowired
	public TaskService(TaskRepository repository) {
		this.repository = repository;
	}
	
	public Task createTask(TaskDTO data) {
		UUID.randomUUID();
		Task newTask = new Task(data);
		this.saveTask(newTask);
		return newTask;
		
	}
	
	public void saveTask(Task task) {
		this.repository.save(task);
	}

	public void updateTaskById(String taskId, TaskUpdateDTO taskUpdateDTO) {
		var id = UUID.fromString(taskId);

		var taskEntity = repository.findById(id);

		if (taskEntity.isPresent()) {
			var task = taskEntity.get();

			if (taskUpdateDTO.taskProgress() != null) {
				task.setTaskProgress(taskUpdateDTO.taskProgress());
			}

			if (taskUpdateDTO.term() != null) {
				task.setTerm(taskUpdateDTO.term());
			}

			repository.save(task);
		}
	}

	public List<Task> getAllTasks() {
		return this.repository.findAll();
	}

	public Optional<Task> findById(String taskId) {
		return this.repository.findById(UUID.fromString(taskId));
	}

	public void deleteById(String taskId) {

		var id = UUID.fromString(taskId);

		var taskExists = repository.existsById(id);

		if (taskExists) {
			this.repository.deleteById(id);
		}
	}
}
