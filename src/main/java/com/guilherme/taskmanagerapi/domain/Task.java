package com.guilherme.taskmanagerapi.domain;

import java.io.Serializable;
import java.util.UUID;

import com.guilherme.taskmanagerapi.dtos.TaskDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="tasks")
@Table(name="TB_TASKS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Task implements Serializable {
    
	 private static final long serialVersionUID = 1L;

	 @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     
     private UUID id;
     @Column(nullable = false, unique=true)
     private String title;
     private String description;
     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private TaskProgress taskProgress;
     private String term;
     
     public Task(TaskDTO data) {
    	 this.title = data.title();
    	 this.description = data.description();
    	 this.taskProgress = data.taskProgress();
    	 this.term = data.term();
     }

}
