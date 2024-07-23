package com.guilherme.taskmanagerapi.services;

import com.guilherme.taskmanagerapi.domain.Task;
import com.guilherme.taskmanagerapi.domain.TaskProgress;
import com.guilherme.taskmanagerapi.dtos.TaskDTO;
import com.guilherme.taskmanagerapi.repositories.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    TaskService service;

    @Mock
    TaskRepository repository;

    @Captor
    private ArgumentCaptor<Task> taskArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createTask {

        @Test
        @DisplayName("Should create a task successfully in DB")
        void shouldCreateATask() {
            TaskDTO data = new TaskDTO("Lavar louça", "Com detergente", TaskProgress.TO_DO, "16/07");
            Task newTask = new Task(data);

            when(repository.save(taskArgumentCaptor.capture())).thenReturn(newTask);

            Task createdTask = service.createTask(data);

            assertNotNull(createdTask);

            var taskCaptured = taskArgumentCaptor.getValue();

            assertEquals(newTask.getTitle(), taskCaptured.getTitle());
            assertEquals(newTask.getDescription(), taskCaptured.getDescription());
            assertEquals(newTask.getTaskProgress(), taskCaptured.getTaskProgress());
            assertEquals(newTask.getTerm(), taskCaptured.getTerm());

            verify(repository,times(1)).save(any(Task.class));
        }

        @Test
        @DisplayName("Should throw exception when a error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            TaskDTO data = new TaskDTO("Sweep the house", "Sweep the corners of the sofa", TaskProgress.TO_DO, "16/07");
            when(repository.save(any())).thenThrow(new RuntimeException());

            assertThrows(RuntimeException.class, () -> service.createTask(data));

        }
    }


    @Nested
    class updateTask {

        @Test
        @DisplayName("Should update a task successfully")
        void shouldUpdateATaskSuccess() {
            TaskDTO data = new TaskDTO("Lavar louça", "Com detergente", TaskProgress.TO_DO, "16/07");
            var newTask = new Task(data);

            when(repository.save(taskArgumentCaptor.capture())).thenReturn(newTask);

            var createdTask = service.createTask(data);

            assertNotNull(createdTask);

            var taskCaptured = taskArgumentCaptor.getValue();

            assertEquals(newTask.getTitle(), taskCaptured.getTitle());
            assertEquals(newTask.getDescription(), taskCaptured.getDescription());
            assertEquals(newTask.getTaskProgress(), taskCaptured.getTaskProgress());
            assertEquals(newTask.getTerm(), taskCaptured.getTerm());

            verify(repository,times(1)).save(any(Task.class));
        }
    }

    @Nested
    class getAllTasks {

        @Test
        @DisplayName("Should get all tasks successfully")
        void shouldGetAllTaskSuccess() {
            TaskDTO data = new TaskDTO("Sweep the house", "Sweep the corners of the sofa", TaskProgress.TO_DO, "16/07");
            var newTask = new Task(data);
            var taskList = List.of(newTask);

            when(repository.findAll()).thenReturn(taskList);

            var result = service.getAllTasks();

            assertNotNull(result);
            assertEquals(taskList.size(), result.size());
        }
    }

    @Nested
    class getATask {

        @Test
        @DisplayName("Should get a task by id successfully")
        void shouldGetATaskById() {
            TaskDTO data = new TaskDTO("Sweep the house", "Sweep the corners of the sofa", TaskProgress.TO_DO, "16/07");
            var newTask = new Task(data);
            doReturn(Optional.of(newTask))
                    .when(repository)
                    .findById(uuidArgumentCaptor.capture());

            var taskId = UUID.randomUUID();
            Optional<Task> result = service.findById(taskId.toString());
            assertThat(result).isPresent();
            assertEquals(taskId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class deleteATask {

        @Test
        @DisplayName("Should delete a task successfully")
        void shouldDeleteATaskSuccessfully() {
            doReturn(true).when(repository).existsById(uuidArgumentCaptor.capture());

            doNothing().when(repository).deleteById(uuidArgumentCaptor.capture());

            var taskId = UUID.randomUUID();

            service.deleteById(taskId.toString());

            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(taskId, idList.get(0));
            assertEquals(taskId, idList.get(1));
            verify(repository, times(1)).existsById(idList.get(0));
            verify(repository,times(1)).existsById(idList.get((1)));
        }
    }

}