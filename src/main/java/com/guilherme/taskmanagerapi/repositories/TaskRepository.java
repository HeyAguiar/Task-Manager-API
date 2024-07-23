package com.guilherme.taskmanagerapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.taskmanagerapi.domain.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

	Optional<Task> findByTitle(String title);
}
