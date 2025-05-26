package com.cloudxplorer.springboot_todo.exception;

public class TasksExceptions {
    
    public static class TaskNotFoundException extends RuntimeException {
        public TaskNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class UnableToCreateTaskException extends RuntimeException {
        public UnableToCreateTaskException(String message) {
            super(message);
        }
    }

    public static class TaskAlreadyExistsException extends RuntimeException {
        public TaskAlreadyExistsException(String message) {
            super(message);
        }
    }

    public class UnsupportedMethodException extends RuntimeException {
        public UnsupportedMethodException(String message) {
            super(message);
        }
    }

}