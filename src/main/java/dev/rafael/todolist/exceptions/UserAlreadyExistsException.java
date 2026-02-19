package dev.rafael.todolist.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException() {
		super("Usuário já existe");
	}
}
