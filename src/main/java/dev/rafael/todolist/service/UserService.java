package dev.rafael.todolist.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.rafael.todolist.exceptions.UserAlreadyExistsException;
import dev.rafael.todolist.model.UserModel;
import dev.rafael.todolist.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserModel createUser(UserModel userModel){
		var user = this.userRepository.findByUsername(userModel.getUsername());

		if (user != null){
			throw new UserAlreadyExistsException();
		}

		var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
		userModel.setPassword(passwordHashred);

		return this.userRepository.save(userModel);
	}

	public UserModel listar(HttpServletRequest request){
		var idUser = (UUID) request.getAttribute("idUser");
		return this.userRepository.findById(idUser).orElse(null);
	}
}
