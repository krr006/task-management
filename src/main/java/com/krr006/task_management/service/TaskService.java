package com.krr006.task_management.service;

import com.krr006.task_management.dto.CommentDTO;
import com.krr006.task_management.dto.TaskDTO;
import com.krr006.task_management.entity.*;
import com.krr006.task_management.exception.TaskNotFoundException;
import com.krr006.task_management.exception.UserNotFoundException;
import com.krr006.task_management.repository.CommentRepository;
import com.krr006.task_management.repository.TaskRepository;
import com.krr006.task_management.repository.UserRepository;
import com.krr006.task_management.utils.CustomEnumValidator;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.krr006.task_management.entity.Task;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

        task = taskRepository.save(task);

        if (taskDTO.getComment() != null && !taskDTO.getComment().isEmpty()) {
            var comment = addComment(task.getId(), new CommentDTO(taskDTO.getComment()));
            task.getComments().add(comment);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, TaskDTO updatedtaskDTO) {
        var existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        existingTask.setTitle(updatedtaskDTO.getTitle());
        existingTask.setDescription(updatedtaskDTO.getDescription());
        existingTask.setStatus(CustomEnumValidator.validateStatus(updatedtaskDTO.getStatus()));
        existingTask.setPriority(CustomEnumValidator.validatePriority(updatedtaskDTO.getPriority()));

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
        existingTask.setStatus(CustomEnumValidator.validateStatus(updatedStatus));

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
    public Comment addComment(Long taskId, CommentDTO commentDTO) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        var comment = Comment.builder()
                .text(commentDTO.getText())
                .task(task)
                .build();

        commentRepository.save(comment);
        return comment;
    }

    public List<Task> findTasksByAuthorId(Long authorId) {
        List<Task> tasks = taskRepository.findByAuthorId(authorId);
        tasks.forEach(task -> task.setComments(commentRepository.findByTaskId(task.getId())));

        return tasks;
    }

    public List<Task> findTasksByAssigneeId(Long assigneeId) {
        List<Task> tasks = taskRepository.findByAssigneeId(assigneeId);
        tasks.forEach(task -> task.setComments(commentRepository.findByTaskId(task.getId())));

        return tasks;
    }


    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }


}
