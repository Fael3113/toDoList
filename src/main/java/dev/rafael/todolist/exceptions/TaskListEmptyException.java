package dev.rafael.todolist.exceptions;

public class TaskListEmptyException extends RuntimeException {
	public TaskListEmptyException() {
		super("Não há nenhuma tarefa");
	}
}
