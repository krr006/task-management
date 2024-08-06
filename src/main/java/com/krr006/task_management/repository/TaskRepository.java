package com.krr006.task_management.repository;

import com.krr006.task_management.entity.Comment;
import com.krr006.task_management.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthorId(Long authorId);
    List<Task> findByAssigneeId(Long assigneeId);
}
