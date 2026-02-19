package dev.rafael.todolist.exceptions;

public class UnauthorizedUserException extends RuntimeException {
	public UnauthorizedUserException() {
		super("Usuário não tem permissão para alterar a tarefa");
	}
}
