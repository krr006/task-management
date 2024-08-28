package com.krr006.task_management.controller;


import com.krr006.task_management.dto.AssignTaskRequest;
import com.krr006.task_management.dto.CommentDTO;
import com.krr006.task_management.dto.StatusRequest;
import com.krr006.task_management.dto.TaskDTO;
import com.krr006.task_management.entity.Comment;
import com.krr006.task_management.entity.Task;
import com.krr006.task_management.service.TaskSecurityService;
import com.krr006.task_management.service.TaskService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskSecurityService taskSecurityService;

    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        var createdTask = taskService.createTask(taskDTO);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO updatedtaskDTO) {
        var updatedTask = taskService.updateTask(id, updatedtaskDTO);
        return new ResponseEntity<>(updatedtaskDTO, HttpStatus.OK);
    }


    public ResponseEntity<Task> findTaskById(@PathVariable Long id){
        var foundTask = taskService.findTaskById(id);
        return new ResponseEntity<>(foundTask, HttpStatus.OK);
    }


    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Task> setTaskStatus(@PathVariable Long taskId, @RequestBody StatusRequest request) {
        var updatedTask = taskService.setTaskStatus(taskId, request.getStatus());
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    public ResponseEntity<Task> assignTask(@PathVariable Long taskId, @RequestBody AssignTaskRequest taskRequest) {
        var assignedTask = taskService.assign(taskId, taskRequest.getAssigneeId());
        return new ResponseEntity<>(assignedTask, HttpStatus.OK);
    }

    public ResponseEntity<Comment> addComment(@PathVariable Long taskId, @RequestBody CommentDTO commentDTO) {
        var addedComment = taskService.addComment(taskId, commentDTO);
        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
    }


    public List<Task> findTasksByAuthor(@RequestParam Long authorId) {
        return taskService.findTasksByAuthorId(authorId);
    }

    public List<Task> findTasksByAssignee(@RequestParam Long assigneeId) {
        return taskService.findTasksByAssigneeId(assigneeId);
    }

}
