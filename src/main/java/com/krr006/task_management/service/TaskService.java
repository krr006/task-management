package com.krr006.task_management.service;

import com.krr006.task_management.dto.TaskDTO;
import com.krr006.task_management.entity.*;
import com.krr006.task_management.exception.TaskNotFoundException;
import com.krr006.task_management.exception.UserNotFoundException;
import com.krr006.task_management.repository.CommentRepository;
import com.krr006.task_management.repository.TaskRepository;
import com.krr006.task_management.repository.UserRepository;
import com.krr006.task_management.utils.CustomEnumValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.krr006.task_management.entity.Task;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final Validator validator;

    @Transactional
    public Task createTask(TaskDTO taskDTO) {
        var task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .status(CustomEnumValidator.validateStatus(taskDTO.getStatus()))
                .priority(CustomEnumValidator.validatePriority(taskDTO.getPriority()))
                .build();


        Long authorId = taskDTO.getAuthorId();
        var author = userRepository.findById(authorId)
                .orElseThrow(() -> new TaskNotFoundException(authorId));
        task.setAuthor(author);


        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        var existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setAssignee(updatedTask.getAssignee());

        return taskRepository.save(existingTask);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }


    @Transactional
    public void deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)){
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);

    }

    public Task setTaskStatus(Long id, String updatedStatus){
        var existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        Status status = Status.valueOf(updatedStatus.toUpperCase());
        existingTask.setStatus(status);

        return taskRepository.save(existingTask);
    }

    @Transactional
    public Task assign(Long id, Long assigneeId) {
        var existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        var assignee = userRepository.findById(assigneeId).orElseThrow(() -> new UserNotFoundException(assigneeId));
        existingTask.setAssignee(assignee);
        return taskRepository.save(existingTask);
    }

    @Transactional
    public Comment addComment(Long taskId, Comment comment) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        comment.setTask(task);
        commentRepository.save(comment);
        return comment;
    }

    public Page<Task> findTasksByAuthorId(Long authorId, Pageable pageable) {
        return taskRepository.findByAuthorId(authorId, pageable);
    }

    public Page<Task> findTasksByAssignee(Long assigneeId, Pageable pageable) {
        return taskRepository.findByAssigneeId(assigneeId, pageable);
    }

    public List<Comment> findCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }


}
