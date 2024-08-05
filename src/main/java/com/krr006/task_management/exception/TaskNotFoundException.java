package com.krr006.task_management.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id){
        super("Task with id=" + id + " not found");
    }
}
