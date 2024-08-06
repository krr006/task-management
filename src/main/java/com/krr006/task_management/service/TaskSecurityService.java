package com.krr006.task_management.service;

import com.krr006.task_management.entity.Task;
import com.krr006.task_management.entity.User;
import com.krr006.task_management.exception.TaskNotFoundException;
import com.krr006.task_management.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Slf4j
@RequiredArgsConstructor
@Service
public class TaskSecurityService {

    private final TaskRepository taskRepository;

//    public boolean isTaskAuthor(Long taskId) {
//        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//        var task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
//        boolean isAuthor = task.getAuthor().getUsername().equals(currentUsername);
//
//
//        if (!isAuthor) {
//            throw new AccessDeniedException("Access Denied");
//        }
//
//        return true;
//    }
//
//    public boolean isTaskAssignee(Long taskId) {
//        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//        var task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
//        boolean isAssignee = task.getAssignee().getUsername().equals(currentUsername);
//
//
//        if (!isAssignee) {
//            throw new AccessDeniedException("Access Denied");
//        }
//
//        return true;
//    }




    private boolean checkUserForTask(Long taskId, Function<Task, User> userExtractor) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        User user = userExtractor.apply(task);

        boolean isCurrentUser = user.getUsername().equals(currentUsername);

        return isCurrentUser;
    }

    public boolean isTaskAuthor(Long taskId) {
        return checkUserForTask(taskId, Task::getAuthor);
    }

    public boolean isTaskAssignee(Long taskId) {
        return checkUserForTask(taskId, Task::getAssignee);
    }
}

