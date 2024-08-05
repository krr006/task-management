package com.krr006.task_management.utils;

import com.krr006.task_management.entity.Priority;
import com.krr006.task_management.entity.Status;

public class CustomEnumValidator {

    public static Status validateStatus(String status) {
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status");
        }
    }

    public static Priority validatePriority(String priority) {
        try {
            return Priority.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority");
        }
    }
}
