package com.krr006.task_management.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class TaskDTO {
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long authorId;

}
