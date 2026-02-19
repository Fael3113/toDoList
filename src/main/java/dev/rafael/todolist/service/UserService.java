package dev.rafael.todolist.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.rafael.todolist.exceptions.UserAlreadyExistsException;
import dev.rafael.todolist.model.UserModel;
import dev.rafael.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public List<UserModel> listar(){
		return this.userRepository.findAll();
	}
}
