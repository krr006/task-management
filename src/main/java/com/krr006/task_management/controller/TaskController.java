package com.krr006.task_management.controller;


import com.krr006.task_management.dto.TaskDTO;
import com.krr006.task_management.entity.Task;
import com.krr006.task_management.service.TaskService;
import com.krr006.task_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")

public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        var createdTask = taskService.createTask(taskDTO);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }
}
