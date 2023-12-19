package com.guilherme.taskmanagerapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.taskmanagerapi.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	boolean existsByTitle(String title);
}
