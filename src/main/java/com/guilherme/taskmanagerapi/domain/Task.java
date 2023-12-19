package com.guilherme.taskmanagerapi.domain;

import java.io.Serializable;

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
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     
     private Long id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskProgress getTaskProgress() {
		return taskProgress;
	}

	public void setTaskProgress(TaskProgress taskProgress) {
		this.taskProgress = taskProgress;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
     
     public Task() {
   
     }
}
