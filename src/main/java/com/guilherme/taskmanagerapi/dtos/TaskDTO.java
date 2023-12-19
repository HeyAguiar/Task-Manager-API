package com.guilherme.taskmanagerapi.dtos;

import com.guilherme.taskmanagerapi.domain.TaskProgress;

public record TaskDTO(String title, String description, TaskProgress taskProgress, String term) {

}
