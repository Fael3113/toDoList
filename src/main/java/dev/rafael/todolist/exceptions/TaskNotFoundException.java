package dev.rafael.todolist.exceptions;

public class TaskNotFoundException extends RuntimeException  {
	public TaskNotFoundException() {
		super("Tarefa não encontrada/inexistente");
	}

}
