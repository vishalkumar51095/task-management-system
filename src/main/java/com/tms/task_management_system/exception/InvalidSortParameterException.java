package com.tms.task_management_system.exception;


public class InvalidSortParameterException extends RuntimeException {
    public InvalidSortParameterException(String sortKey) {
        super("Invalid sortBy parameter: " + sortKey);
    }
}
