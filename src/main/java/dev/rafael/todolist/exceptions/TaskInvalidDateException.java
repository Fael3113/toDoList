package dev.rafael.todolist.exceptions;

public class TaskInvalidDateException extends RuntimeException {
	public TaskInvalidDateException(String message) {
		super(message);
	}
}
