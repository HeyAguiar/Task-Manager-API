package com.guilherme.taskmanagerapi.dtos;

import com.guilherme.taskmanagerapi.domain.TaskProgress;

public record TaskUpdateDTO(TaskProgress taskProgress, String term) {
}
