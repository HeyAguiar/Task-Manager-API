package com.guilherme.taskmanagerapi.repositories;

import com.guilherme.taskmanagerapi.domain.Task;
import com.guilherme.taskmanagerapi.domain.TaskProgress;
import com.guilherme.taskmanagerapi.dtos.TaskDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should find a task successfully by title from DB")
    void findATaskSuccess() {
        String title = "Sweep the house";

       TaskDTO data = new TaskDTO(title, "Sweep the corners of the sofa", TaskProgress.TO_DO, "16/07");
       this.createTask(data);

        Optional<Task> result = taskRepository.findByTitle(title);
       assertThat(result).isPresent();
    }

    @Test
    @DisplayName("Should not find a task in DB when the task doesn’t exist")
    void findATaskError() {
        String title = "Sweep the house";

        Optional<Task> result = taskRepository.findByTitle(title);
        assertThat(result).isEmpty();
    }

    private Task createTask(TaskDTO data) {
        Task newTask = new Task(data);
        this.entityManager.persist(newTask);
        return newTask;
    }
}